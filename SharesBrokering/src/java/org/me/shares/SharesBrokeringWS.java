/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.shares;

import Shares.CurrentShares;
import Shares.Share;
import Shares.SharePrice;
import UserShares.ShareInfo;
import UserShares.UserShare;
import UserShares.UserShares;
import Users.User;
import Users.Users;
import docwebservices.CurrencyConversionWSService;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceRef;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author tyler
 */
@WebService(serviceName = "SharesBrokeringWS")
@Stateless()
public class SharesBrokeringWS {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/CurrencyConvertor/CurrencyConversionWSService.wsdl")
    private CurrencyConversionWSService service;


    /**
     * Web service operation
     * @param symbol
     * @param name
     * @param qty
     * @param currency
     * @param value
     * @return 
     */
    @WebMethod(operationName = "AddShare")
    public Boolean AddShare(String symbol, String name, int qty, String currency, Double value) {
        // get the current shares from the xml file
        CurrentShares cs = GetStoredShares();
        List<Share> shares = cs.getShares();
        
        Share tempShare;
        // iterate through each share
        Iterator itr = shares.iterator();
        while(itr.hasNext())
        {
            tempShare = (Share) itr.next();
            // if the company symbol already exists, don't add the share and return false
            if (tempShare.getCompanySymbol().equals(symbol))
            {
                return false;
            }
        }
        Share s = new Share();
        s.setCompanySymbol(symbol);
        s.setCompanyName(name);
        s.setQtyAvailable(qty);
        
        SharePrice sp = new SharePrice();
        sp.setCurrency(currency);
        sp.setValue(value);
        s.setPrice(sp);
        try {
            XMLGregorianCalendar now = DatatypeFactory.newInstance().
                    newXMLGregorianCalendar(new GregorianCalendar());
            s.setUpdateDate(now);
        } catch (DatatypeConfigurationException ex) {
            return false;
        }
        shares.add(s);
        
        try {            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(cs.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            marshaller.marshal(cs, new File("C:\\Users\\tyler\\OneDrive\\Documents\\Cloud\\CurrentShares.xml"));
        } catch (javax.xml.bind.JAXBException ex) {
            return false;
        }
        return true;
    }

    /**
     * Web service operation
     * @param symbol
     * @param currency
     * @return 
     */
    @WebMethod(operationName = "getShare")
    public Share getShare(String symbol, String currency) {
        // get the current shares from the xml file
        CurrentShares cs = GetStoredShares();
        List<Share> shares = cs.getShares();
        
        // filter shares to get desired share
        Share s = shares.stream().filter(x -> x.getCompanySymbol().equals(symbol)).findFirst().orElse(null);
        
        // if the user's currency is different to the share's
        if (!s.getPrice().getCurrency().equals(currency))
        {
            // convert the price
            List<String> fromCur = new ArrayList();
            SharePrice tempSharePrice = s.getPrice();
            fromCur.add(tempSharePrice.getCurrency());
            List<Double> convertedRates = getConversionRates(fromCur, currency);
            if (convertedRates.isEmpty())
            {
                return null;
            }
            tempSharePrice.setValue(tempSharePrice.getValue()*convertedRates.get(0));
        }
        
        return s;
    }
        
    /**
     * Web service operation
     * @param symbol
     * @param name
     * @param minQty
     * @param maxQty
     * @param minPrice
     * @param maxPrice
     * @param cur
     * @return 
     */
    @WebMethod(operationName = "GetFilteredShares")
    public List<Share> GetFilteredShares(String symbol, String name, int minQty, int maxQty, Double minPrice, Double maxPrice, String cur) {
        // get all updated shares from xml file
        CurrentShares cs = updateStockValues();
        List<Share> shares = cs.getShares();
                
        
        // if any parameters aren't null, filters need to be applied
        shares = shares.stream()
                    .filter(s -> (symbol == null || s.getCompanySymbol().toLowerCase().contains(symbol.toLowerCase()))
                            && (name == null || s.getCompanyName().toLowerCase().contains(name.toLowerCase()))
                            && (minQty < 0 || s.getQtyAvailable()>=minQty)
                            && (maxQty < 0 || s.getQtyAvailable()<=maxQty)
                            && (minPrice < 0 || s.getPrice().getValue()>=minPrice)
                            && (maxPrice < 0 || s.getPrice().getValue()<=maxPrice))
                    .collect(Collectors.toList());
        
        List<String> fromCurs = new ArrayList();
        Iterator itr = shares.iterator();
        
        // add each share's currency to a list
        while(itr.hasNext())
        {
            fromCurs.add(((Share) itr.next()).getPrice().getCurrency());
        }
        // get the conversion rate for each currency
        List<Double> convertedRates = getConversionRates(fromCurs, cur);
        
        int i = 0;
        SharePrice tempSharePrice;
        for (Iterator it = shares.iterator(); it.hasNext(); i++) {
            tempSharePrice = ((Share) it.next()).getPrice();
            
            // if the currency needs to be converted
            if(!tempSharePrice.getCurrency().equals(cur))
            {
                // if the currency converter is down, set the value to -1
                if(convertedRates.isEmpty())
                {
                    tempSharePrice.setValue(-1);
                }
                else
                {
                    // convert the price
                    tempSharePrice.setValue(tempSharePrice.getValue()*convertedRates.get(i));
                }
            }
        }
        return shares;
    }
    
    
    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "GetShares")
    public List<Share> GetShares() {
        String symbol = "";
        String name = "";
        int minQty =-1;
        int maxQty =-1;
        double minPrice = -1;
        double maxPrice = -1;
        String cur = "GBP";
        // get all updated shares from xml file
        CurrentShares cs = updateStockValues();
        List<Share> shares = cs.getShares();
                
        
        // if any parameters aren't null, filters need to be applied
        shares = shares.stream()
                    .filter(s -> (symbol == null || s.getCompanySymbol().toLowerCase().contains(symbol.toLowerCase()))
                            && (name == null || s.getCompanyName().toLowerCase().contains(name.toLowerCase()))
                            && (minQty < 0 || s.getQtyAvailable()>=minQty)
                            && (maxQty < 0 || s.getQtyAvailable()<=maxQty)
                            && (minPrice < 0 || s.getPrice().getValue()>=minPrice)
                            && (maxPrice < 0 || s.getPrice().getValue()<=maxPrice))
                    .collect(Collectors.toList());
        
        List<String> fromCurs = new ArrayList();
        Iterator itr = shares.iterator();
        
        // add each share's currency to a list
        while(itr.hasNext())
        {
            fromCurs.add(((Share) itr.next()).getPrice().getCurrency());
        }
//        // get the conversion rate for each currency
//        List<Double> convertedRates = getConversionRates(fromCurs, cur);
//        
//        int i = 0;
//        SharePrice tempSharePrice;
//        for (Iterator it = shares.iterator(); it.hasNext(); i++) {
//            tempSharePrice = ((Share) it.next()).getPrice();
//            
//            // if the currency needs to be converted
//            if(!tempSharePrice.getCurrency().equals(cur))
//            {
//                // if the currency converter is down, set the value to -1
//                if(convertedRates.isEmpty())
//                {
//                    tempSharePrice.setValue(-1);
//                }
//                else
//                {
//                    // convert the price
//                    tempSharePrice.setValue(tempSharePrice.getValue()*convertedRates.get(i));
//                }
//            }
//        }
        return shares;
    }
  
    
    private CurrentShares GetStoredShares()
    {
        // get all the shares from the xml file
        CurrentShares cs = new CurrentShares();
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(cs.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            cs = (CurrentShares) unmarshaller.unmarshal(new File("C:\\Users\\tyler\\OneDrive\\Documents\\Cloud\\CurrentShares.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            return cs;
        }
        return cs;
    }

    /**
     * Web service operation
     * @param username
     * @param password
     * @param currency
     * @param wallet
     * @return 
     */
    @WebMethod(operationName = "AddNewUser")
    public Boolean AddNewUser(String username, String password, String currency, double wallet) {
        // get all users from xml file
        Users us = GetStoredUsers();
        if (us==null)
        {
            return false;
        }
        List<User> users = us.getUser();
        Iterator itr = users.iterator();
        
        // check if username entered already exists, return false if it does
        if (users.stream().filter(x -> x.getUsername().equals(username)).findFirst().orElse(null)!=null)
        {
            return false;
        }
        
        
        // if username doesn't exist
        // create a new user and add it to the users list
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setCurrency(currency);
        u.setWallet(wallet);
        users.add(u);
            
        try {            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(us.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            marshaller.marshal(us, new File("C:\\Users\\tyler\\OneDrive\\Documents\\Cloud\\Users.xml"));
            } catch (javax.xml.bind.JAXBException ex) {
                return false;
            }
        
        return true;
    }

    
    private Users GetStoredUsers() {
        // get all the users from the xml file
        Users u = new Users();
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(u.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            u = (Users) unmarshaller.unmarshal(new File("C:\\Users\\tyler\\OneDrive\\Documents\\Cloud\\Users.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            return u;
        }
        return u;
    }
    
    /**
     * Web service operation
     * @param username
     * @param password
     * @return 
     */
    @WebMethod(operationName = "Login")
    public boolean Login(String username, String password) {
        // get the user
        User u = getUser(username);
        if (u == null)
        {
            return false;
        }
        // if the password matches, return true else false
        return u.getPassword().equals(password);
    }
    
    /**
     * Web service operation
     * @param username
     * @return 
     */
    @WebMethod(operationName = "getUser")
    public User getUser(String username)
    {
        // get all users from xml file
        Users us = GetStoredUsers();
        List<User> users = us.getUser();
        // filter on username to get the correct user then return it
        return users.stream().filter(x -> x.getUsername().equals(username)).findFirst().orElse(null);
    }
    
    
    /**
     * Web service operation
     * @param username
     * @param symbol
     * @param minQty
     * @param maxQty
     * @return 
     */
    @WebMethod(operationName = "GetUserShares")
    public List<ShareInfo> GetUserShares(String username, String symbol, int minQty, int maxQty) {
        // get the user shares from the xml file
        UserShares us = GetStoredUserShares();
        
        List<UserShare> userShares = us.getUserShare();
        
        // filter user shares to get only shares for the username specified
         UserShare userShare = userShares.stream()
                 .filter(s -> s.getUsername().equals(username)).findFirst().orElse(null);
         
         // if the user has no shares, return empty
         if(userShare==null)
         {
             return Collections.emptyList();
         }
         
         List<ShareInfo> si = userShare.getShareInfo();
         
        
        // filter user shares to just contain symbol specfied and within the mina and max qty specified
        return si.stream()
                .filter(s -> (s.getCompanySymbol().toLowerCase().contains(symbol.toLowerCase()))
                        && (minQty < 0 || s.getQuantity()>=minQty)
                            && (maxQty < 0 || s.getQuantity()<=maxQty))
                .collect(Collectors.toList());
        
    }
    
    private UserShares GetStoredUserShares() {
        // get all the user shares from the xml file
        UserShares us = new UserShares();
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(us.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            us = (UserShares) unmarshaller.unmarshal(new File("C:\\Users\\tyler\\OneDrive\\Documents\\Cloud\\UserShares.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            return us;
        }
        return us;
    }
    
    private Boolean UpdateShareQuantity(String symbol, int qty, Boolean add)
    {
        // get the shares from the xml file
        CurrentShares cs = GetStoredShares();
        List<Share> shares = cs.getShares();
        
        Share tempShare;
        // iterate through each share
        Iterator itr = shares.iterator();
        Boolean foundShare = false;
        while(itr.hasNext())
        {
            tempShare = (Share) itr.next();
            // if the share exists, update its quantity
            if (tempShare.getCompanySymbol().equals(symbol))
            {
                if (add)
                {
                    tempShare.setQtyAvailable(tempShare.getQtyAvailable()+qty);
                }
                else
                {
                    tempShare.setQtyAvailable(tempShare.getQtyAvailable()-qty);
                }
                foundShare = true;
                break;
            }
        }
        // if the share doesn't exist, return false
        if (!foundShare)
        {
            return false;
        }
        
        try {            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(cs.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            marshaller.marshal(cs, new File("C:\\Users\\tyler\\OneDrive\\Documents\\Cloud\\CurrentShares.xml"));
        } catch (javax.xml.bind.JAXBException ex) {
            return false;
        }
        return true;
    }
    
    
    /**
     * Web service operation
     * @param username
     * @param symbol
     * @param qty
     * @param value
     * @return 
     */
    @WebMethod(operationName = "AddUserShare")
    public Boolean AddUserShare(String username, String symbol, int qty, double value) {
      
        // remove the value from the user's wallet and update the share quantity
        if((!adjustWallet(username, value, false))||!UpdateShareQuantity(symbol, qty, false))
        {
            return false;
        }
        
        // get the user shares from the xml file
        UserShares us = GetStoredUserShares();
        //if (us==null)
        //{
//            return false;
//        }
        List<UserShare> shares = us.getUserShare();

        
        // create new share info
        ShareInfo s = new ShareInfo();
        s.setCompanySymbol(symbol);
        s.setQuantity(qty);
        
        
        UserShare tempUserShare;
        ShareInfo tempShare;
        
        // iterate through usernames
        Iterator itr = shares.iterator();
        Boolean userExists = false;
        Boolean shareExists = false;
        while(itr.hasNext())
        {
            tempUserShare = (UserShare) itr.next();
            // if the current usermame is the username to add the share for
            if (tempUserShare.getUsername().equals(username))
            {
                userExists = true;
                List<ShareInfo> tempShareInfo = tempUserShare.getShareInfo();
                // iterate through that username's shares
                Iterator itr2 = tempShareInfo.iterator();
                while(itr2.hasNext())
                {
                    tempShare = (ShareInfo) itr2.next();
                    // if the user already has shares for that company, add the quantity to the existing
                    if (tempShare.getCompanySymbol().equals(symbol))
                    {
                        tempShare.setQuantity(tempShare.getQuantity() + qty);
                        shareExists = true;
                        break;
                    }
                    
                }
                // if the user doesn't already have a share in the company, add the share
                if (!shareExists)
                {
                    tempShareInfo.add(s);
                }
                break;
            }
        }
        // if the user has no shares, add the user and the share
        if (!userExists)
        {
            // create the new user share
            UserShare u = new UserShare();
            u.setUsername(username);
            List<ShareInfo> userShareInfo = u.getShareInfo();
            userShareInfo.add(s);
            
            // add to the shares list
            shares.add(u);
        }
        
        
        try {            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(us.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            marshaller.marshal(us, new File("C:\\Users\\tyler\\OneDrive\\Documents\\Cloud\\UserShares.xml"));
        } catch (javax.xml.bind.JAXBException ex) {
            return false;
        }
        return true;
    }

    /**
     * Web service operation
     * @param username
     * @param symbol
     * @param qty
     * @param value
     * @return 
     */    
@WebMethod(operationName = "SellShare")
    public Boolean SellShare(String username, String symbol, int qty, double value) {
        // get the user shares from the xml file
        UserShares us = GetStoredUserShares();
        if (us==null)
        {
            return false;
        }
        List<UserShare> shares = us.getUserShare();
        
        UserShare tempUserShare;
        ShareInfo tempShare;
        
        // iterate through usernames
        Iterator itr = shares.iterator();
        Boolean shareExists = false;
        while(itr.hasNext())
        {
            tempUserShare = (UserShare) itr.next();
            // if the current usermame is the username to sell the share for
            if (tempUserShare.getUsername().equals(username))
            {
                List<ShareInfo> tempShareInfo = tempUserShare.getShareInfo();
                // iterate through that username's shares
                Iterator itr2 = tempShareInfo.iterator();
                while(itr2.hasNext())
                {
                    tempShare = (ShareInfo) itr2.next();
                    // if the user has shares for that company
                    if (tempShare.getCompanySymbol().equals(symbol))
                    {
                        shareExists = true;
                        int tempQty = tempShare.getQuantity();
                        // if the user has less shares than they are trying to sell, return false
                        if (tempQty < qty)
                        {
                            return false;
                        }
                        // if the user has more shares than they are trying to sell, update the quantity
                        else if (tempQty > qty)
                        {
                            tempShare.setQuantity(tempQty - qty);
                        }
                        // if the user has the same amount of shares as they are trying to sell, remove the share
                        else
                        {
                            itr2.remove();
                        }
                        break;
                    }
                    
                }
                // if the user doesn't have a share in the company, return false
                if (!shareExists)
                {
                    return false;
                }
                break;
            }
        }
        // update the shares xml file to add the new shares sold back to the broker
        if (!adjustWallet(username, value, true)||!UpdateShareQuantity(symbol, qty, true))
        {
            return false;
        }
        
        
        try {            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(us.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            marshaller.marshal(us, new File("C:\\Users\\tyler\\OneDrive\\Documents\\Cloud\\UserShares.xml"));
        } catch (javax.xml.bind.JAXBException ex) {
            return false;
        }
        return true;
    }    

   
     /**
     * Web service operation
     * @return 
     */ 
    @WebMethod(operationName = "GetCurrencyList")
    public List<String> GetCurrencyList() {
        return getCurrencyCodes();
    }

    
    @WebMethod(operationName = "adjustWallet")
    public Boolean adjustWallet(String username, double amount, boolean add) {
        // get the user
        User u = getUser(username);
        if (u==null)
        {
            return false;
        }
        
        // get all the users from the xml file
        Users us = GetStoredUsers();
        if (us==null)
        {
            return false;
        }
        List<User> users = us.getUser();
        
        Iterator itr = users.iterator();
        User tempUser;
        // iterate through the users
        while(itr.hasNext())
        {
            tempUser = (User) itr.next();
            // if it's the correct user
            if(tempUser.getUsername().equals(username))
            {
                // if adding to wallet
                if (add)
                {
                    // add the amount
                    tempUser.setWallet(tempUser.getWallet()+amount);
                }
                // if subtracting from wallet
                else
                {
                    // if the amount is more than in the wallet, return false
                    if(amount>tempUser.getWallet())
                    {
                        return false;
                    }
                    // subtract the amount
                    tempUser.setWallet(tempUser.getWallet()-amount);
                }
                break;
            }
        }
        
        try {            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(us.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            marshaller.marshal(us, new File("C:\\Users\\tyler\\OneDrive\\Documents\\Cloud\\Users.xml"));
            } catch (javax.xml.bind.JAXBException ex) {
                return false;
            }
        return true;
    }
    
    
    /**
     * Web service operation
     * @param username
     * @param toCur
     * @return 
     */ 
    @WebMethod(operationName = "ExchangeWallet")
    public Boolean ExchangeWallet(String username, String toCur) {
        // get the user
        User u = getUser(username);
        if (u==null)
        {
            return false;
        }
        
        List<String> fromCur = new ArrayList();
        
        // add the user's current currency to the from currency list
        fromCur.add(u.getCurrency());
        // get the conversion rate to convert ro the new currency
        List<Double> rates = getConversionRates(fromCur, toCur);
        if (rates.isEmpty())
        {
            return false;
        }
        Double rate = rates.get(0);
        
        
        // get all the users from the xml file
        Users us = GetStoredUsers();
        if (us==null)
        {
            return false;
        }
        List<User> users = us.getUser();
        
        Iterator itr = users.iterator();
        User tempUser;
        // iterate through the users
        while(itr.hasNext())
        {
            tempUser = (User) itr.next();
            // if it's the correct user
            if(tempUser.getUsername().equals(username))
            {
                // set the user's currency to the new currency
                tempUser.setCurrency(toCur);
                // convert the user's wallet amount to the new rate
                tempUser.setWallet(tempUser.getWallet()*rate);
                break;
            }
        }
        
        try {            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(us.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            marshaller.marshal(us, new File("C:\\Users\\tyler\\OneDrive\\Documents\\Cloud\\Users.xml"));
            } catch (javax.xml.bind.JAXBException ex) {
                return false;
            }
        return true;
    }
    
    private CurrentShares updateStockValues()
    {
        // get the shares from the xml file
        CurrentShares cs = GetStoredShares();
        List<Share> shares = cs.getShares();
        try {
            String symbols = "";
            
            // iterate through each share
            Iterator itr = shares.iterator();
            while(itr.hasNext())
            {
                // add each share's symbol to the symbols list
                symbols += ((Share) itr.next()).getCompanySymbol()+",";
            }
            
            // get the latest value for each symbol in the symbols list
            String values = getLatestValues(symbols);
            
            // if the latest values couldn't be retrieved, return the current shares
            if (values.equals(""))
            {
                return cs;
            }
            
            // parse the latest values json
            JSONParser parse = new JSONParser();
            JSONObject LatestValuesJSON = (JSONObject) parse.parse(values);
            
            // get the current date
            XMLGregorianCalendar currentDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
            
            itr = shares.iterator();
            Share tempShare;
            SharePrice tempSharePrice;
            JSONObject tempJSON;
            // iterate through the shares
            while(itr.hasNext())
            {
                tempShare = (Share) itr.next();
                // if the latest value of the current symbol was returned
                if (LatestValuesJSON.containsKey(tempShare.getCompanySymbol()))
                {
                    tempSharePrice = tempShare.getPrice();
                    // set the value to the latest value
                    tempSharePrice.setValue((Double) ((JSONObject) ((JSONObject) LatestValuesJSON.get(tempShare.getCompanySymbol())).get("quote")).get("latestPrice"));
                    // as the api returns currency in usd, set the currency to usd
                    tempSharePrice.setCurrency("USD");
                    // set the update date to now
                    tempShare.setUpdateDate(currentDate);
                }
            }
            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(cs.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            marshaller.marshal(cs, new File("C:\\Users\\tyler\\OneDrive\\Documents\\Cloud\\CurrentShares.xml"));
            
        } catch (ParseException | DatatypeConfigurationException | JAXBException ex) {
            // if there are any exceptions, return the shares as they are
            return cs;
        }
        
        return cs;
    }
    
    private String getLatestValues(String symbols) {
        String values = "";

        try {
            // connect to the stockrest restful service choosing to get the stock quote for the symbols specified
            URL url = new URL("http://localhost:8080/StockRest/webresources/stockrest?type=quote&symbols="+symbols);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() != 200) {
                return "";
            }

            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                values += sc.nextLine();
            }
            sc.close();
            
        // return a blank string if there are any exceptions
        } catch (MalformedURLException ex) {
            return "";
        } catch (IOException ex) {
            return "";
        }
        
        return values;
    }
    
    
    @WebMethod(operationName = "getNews")
    public String getNews(String symbol) {
        String news = "";
        try {
            String latestNews = getLatestNews(symbol);
            
            // if the latest news cannot be retrieved, return a blank string
            if(latestNews.equals(""))
            {
                return "";
            }
            
            JSONParser parse = new JSONParser();
            JSONObject newsJSON = (JSONObject) parse.parse(latestNews);
            
            JSONArray newsArray = (JSONArray) ((JSONObject) newsJSON.get(symbol)).get("news");
            JSONObject tempJSON;
            
            for (int i=0; i<3; i++)
            {
                tempJSON = (JSONObject) newsArray.get(i);
                news += ((String) tempJSON.get("headline"))+"###"+
                        ((String) tempJSON.get("url"))+"###"+
                        ((String) tempJSON.get("summary"))+"###";
            }
        // return a blank string if there are any exceptions
        } catch (ParseException ex) {
            return "";
        }
        return news;
    }
    
    
    private String getLatestNews(String symbol) {
        String news = "";

        try {
            URL url = new URL("http://localhost:8080/StockRest/webresources/stockrest?type=news&symbols="+symbol);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() != 200) {
                return "";
            }

            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                news += sc.nextLine();
            }
            sc.close();
            
            news = news.replace("Ã¢â‚¬ï¿½", "^//").replace("Ã¢â‚¬Å","").replace("Ã¢â‚¬â„¢", "^/").replace("Ã¢â‚¬Â¦","...");
            
        // return a blank string if there are any exceptions
        } catch (MalformedURLException ex) {
            return "";
        } catch (IOException ex) {
            return "";
        }
        
        return news;
    }
    
    
    
    private List<String> getCurrencyCodes() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        docwebservices.CurrencyConversionWS port = service.getCurrencyConversionWSPort();
        return port.getCurrencyCodes();
    }

    private List<Double> getConversionRates(List<String> fromCurs, String toCur) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        docwebservices.CurrencyConversionWS port = service.getCurrencyConversionWSPort();
        return port.getConversionRates(fromCurs, toCur);
    }

    
    
}

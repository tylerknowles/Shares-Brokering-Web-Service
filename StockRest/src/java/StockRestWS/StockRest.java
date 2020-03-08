/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StockRestWS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author tyler
 */
@Path("stockrest")
public class StockRest {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of StockRest
     */
    public StockRest() {
    }

    
     @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getStockData(@QueryParam("type") String type, @QueryParam("symbols") String symbols) {
        
        switch(type){
            case "quote":
                return getStockValues(symbols);
            case "news":
                return getStockNews(symbols);
            default:
                return null;
        }
    }
    
    
    private String getStockValues(String symbols) {
        String values = "";
        try {
            URL url = new URL("https://cloud.iexapis.com/stable/stock/market/batch?symbols="+symbols+"&types=quote&token=pk_c5c80692f5f644539ad40fd95e32ce86");
        
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() != 200) {
                return getValuesFromFile();
            }
            
            Scanner sc = new Scanner(url.openStream());       
            while(sc.hasNext())
            {
                values+=sc.nextLine();
            }
            sc.close();
            
            if(values.contains("Error"))
            {
                return getValuesFromFile();
            }
            
            FileWriter fileWriter = new FileWriter("C:\\Users\\tyler\\OneDrive\\Documents\\Cloud\\StockValues.txt");
            fileWriter.write(values);
            fileWriter.close();
        
            return values;
            
        } catch (MalformedURLException ex) {
            return getValuesFromFile();
        } catch (IOException ex) {
            return getValuesFromFile();
        }
    }
    
    
    private String getValuesFromFile() {
        String values = "";
        try {
            Scanner sc = new Scanner(new File("C:\\Users\\tyler\\OneDrive\\Documents\\Cloud\\StockValues.txt"));
            while (sc.hasNextLine()) 
            {
                values+=sc.nextLine();
            }
        } catch (FileNotFoundException ex) {
            return "";
        }
        
    return values;
    }
    
    private String getStockNews(String symbol) {
        
        String news = "";
        try {
            URL url = new URL("https://cloud.iexapis.com/stable/stock/market/batch?symbols="+symbol+"&types=news&last=3&token=pk_c5c80692f5f644539ad40fd95e32ce86");
        
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() != 200) {
                return "";
            }
            
            Scanner sc = new Scanner(url.openStream());       
            while(sc.hasNext())
            {
                news+=sc.nextLine();
            }
            sc.close();
            
        } catch (MalformedURLException ex) {
            return "";
        } catch (IOException ex) {
            return "";
        }
        return news;
    }
   
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DOCwebServices;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author taha-m
 */
@WebService()
public class CurrencyConversionWS {

    public enum ExRate {
        AED("UAE Dirham"),
        AFN("Afghan Afghani"),
        ALL("Albanian Lek"),
        AMD("Armenian Dram"),
        ANG("Netherlands Antillean Guilder"),
        AOA("Angolan Kwanza"),
        ARS("Argentine Peso"),
        AUD("Australian Dollar"),
        AWG("Aruban Florin"),
        AZN("Azerbaijani Manat"),
        BAM("Bosnia-Herzegovina Convertible Mark"),
        BBD("Bajan Dollar"),
        BDT("Bangladeshi Taka"),
        BGN("Bulgarian Lev"),
        BHD("Bahraini Dinar"),
        BIF("Burundian Franc"),
        BMD("Bermudan Dollar"),
        BND("Brunei Dollar"),
        BOB("Bolivian boliviano"),
        BRL("Brazilian Real"),
        BSD("Bahamian Dollar"),
        BTC("Bitcoin"),
        BTN("Bhutanese Ngultrum"),
        BWP("Botswana Pula"),
        BYN("Belarusian Ruble"),
        BYR("Old Belarusian Ruble"),
        BZD("Belize Dollar"),
        CAD("Canadian Dollar"),
        CDF("Congolese Franc"),
        CHF("Swiss Franc"),
        CLF("Chilean Unit of Account"),
        CLP("Chilean Peso"),
        CNY("Chinese Yuan"),
        COP("Colombian Peso"),
        CRC("Costa Rican Colón"),
        CUC("Cuban Convertible Peso"),
        CUP("Cuban Peso"),
        CVE("Cape Verdean Escudo"),
        CZK("Czech Koruna"),
        DJF("Djiboutian Franc"),
        DKK("Danish Krone"),
        DOP("Dominican Peso"),
        DZD("Algerian Dinar"),
        EGP("Egypt Pounds"),
        ERN("Eritrean Nakfa"),
        ETB("Ethiopian Birr"),
        EUR("Euro"),
        FJD("Fijian Dollar"),
        FKP("Falkland Islands Pound"),
        GBP("British Pound Sterling"),
        GEL("Georgian Lari"),
        GGP("Guernsey Pound"),
        GHS("Ghanaian Cedi"),
        GIP("Gibraltar Pound"),
        GMD("Gambian Dalasi"),
        GNF("Guinean Franc"),
        GTQ("Guatemalan Quetzal"),
        GYD("Guyanaese Dollar"),
        HKD("Hong Kong Dollar"),
        HNL("Honduran Lempira"),
        HRK("Croatian Kuna"),
        HTG("Haitian Gourde"),
        HUF("Hungarian Forint"),
        IDR("Indonesian Rupiah"),
        ILS("Israeli New Shekel"),
        IMP("Isle of Man Pound"),
        INR("Indian Rupee"),
        IQD("Iraqi Dinar"),
        IRR("Iranian Rial"),
        ISK("Iceland Krona"),
        JEP("Jersey Pound"),
        JMD("Jamaican Dollar"),
        JOD("Jordanian Dinar"),
        JPY("Japanese Yen"),
        KES("Kenyan Shilling"),
        KGS("Kyrgystani Som"),
        KHR("Cambodian Riel"),
        KMF("Comorian Franc"),
        KPW("North Korean Won"),
        KRW("South Korean Won"),
        KWD("Kuwaiti Dinar"),
        KYD("Cayman Islands Dollar"),
        KZT("Kazakhstani Tenge"),
        LAK("Laotian Kip"),
        LBP("Lebanese pound"),
        LKR("Sri Lanka Rupee"),
        LRD("Liberian Dollar"),
        LSL("Lesotho Loti"),
        LTL("Lithuanian Litas"),
        LVL("Latvian Lat"),
        LYD("Libyan Dinar"),
        MAD("Moroccan Dirham"),
        MDL("Moldovan Leu"),
        MGA("Malagasy Ariary"),
        MKD("Macedonian Denar"),
        MMK("Myanmar Kyat"),
        MNT("Mongolian Tughrik"),
        MOP("Macanese Pataca"),
        MRO("Mauritanian Ouguiya"),
        MUR("Mauritian Rupee"),
        MVR("Maldivian Rufiyaa"),
        MWK("Malawian Kwacha"),
        MXN("Mexican Peso"),
        MYR("Malaysian Ringgit"),
        MZN("Mozambican Metical"),
        NAD("Namibian Dollar"),
        NGN("Nigerian Naira"),
        NIO("Nicaraguan Córdoba"),
        NOK("Norwegian Kroner"),
        NPR("Nepalese Rupee"),
        NZD("New Zealand Dollar"),
        OMR("Omani Rial"),
        PAB("Panamanian Balboa"),
        PEN("Sol"),
        PGK("Papua New Guinean kina"),
        PHP("Philippine Peso"),
        PKR("Pakistan Rupee"),
        PLN("Polish Złoty"),
        PYG("Paraguayan Guarani"),
        QAR("Qatari Rial"),
        RON("Romanian Leu"),
        RSD("Serbian Dinar"),
        RUB("Russian Ruble"),
        RWF("Rwandan Franc"),
        SAR("Saudi Riyal"),
        SBD("Solomon Islands Dollar"),
        SCR("Seychellois Rupee"),
        SDG("Sudanese Pound"),
        SEK("Swedish Krona"),
        SGD("Singapore Dollar"),
        SHP("Saint Helena Pound"),
        SLL("Sierra Leonean Leone"),
        SOS("Somali Shilling"),
        SRD("Surinamese Dollar"),
        STD("São Tomé and Príncipe Dobra"),
        SVC("Salvadoran Colón"),
        SYP("Syrian Pound"),
        SZL("Swazi Lilangeni"),
        THB("Thai Baht"),
        TJS("Tajikistani Somoni"),
        TMT("Turkmenistan Manat"),
        TND("Tunisian Dinar"),
        TOP("Tongan Paʻanga"),
        TRY("Turkish Lira"),
        TTD("Trinidad & Tobago Dollar"),
        TWD("Taiwan Dollar"),
        TZS("Tanzanian Shilling"),
        UAH("Ukrainian Hryvnia"),
        UGX("Ugandan Shilling"),
        USD("United States Dollar"),
        UYU("Uruguayan Peso"),
        UZS("Uzbekistani Soʻm"),
        VEF("Venezuelan Bolivar"),
        VND("Vietnamese Dong"),
        VUV("Vanuatu Vatu"),
        WST("Samoan Tālā"),
        XAF("Central African CFA Franc"),
        XAG("Silver Ounce"),
        XAU("Gold Ounce"),
        XCD("Eastern Caribbean Dollar"),
        XDR("Special Drawing Rights"),
        XOF("West African CFA Franc"),
        XPF("CFP Franc"),
        YER("Yemeni Rial"),
        ZAR("South African Rand"),
        ZMK("Old Zambian Kwacha"),
        ZMW("Zambian Kwacha"),
        ZWL("Zimbabwean Dollar");

        private final String curName;

        ExRate(String curName) {
            this.curName = curName;
        }

        String curName() {
            return curName;
        }
    }

    public List<Double> GetConversionRates(List<String> fromCurs, String toCur) {
        
        String LatestRatesString;
        LatestRatesString = getLatestRates();
        if (LatestRatesString.equals(""))
        {
            return Collections.emptyList();
        }
        JSONParser parse = new JSONParser();
        String rate = "";

        
        List<Double> Rates = new ArrayList();
             
        try {
            JSONObject LatestRatesJSON = (JSONObject) ((JSONObject) parse.parse(LatestRatesString)).get("rates");
        
            Double toRate = ((Number) LatestRatesJSON.get(toCur)).doubleValue();
            Iterator itr = fromCurs.iterator();
            String tempCur;
            while(itr.hasNext())
            {
                tempCur = (String) itr.next();
                Rates.add(toRate/((Number) LatestRatesJSON.get(tempCur)).doubleValue());
            }
        } catch (ParseException ex) {
            return Collections.emptyList();
        }
            
        return Rates;
        }
 
        

    public List<String> GetCurrencyCodes() {
        List<String> codes = new ArrayList();
        for (ExRate exr : ExRate.values()) {
            codes.add(exr.name() + " - " + exr.curName);
        }
        return codes;
    }

    
    private String getLatestRates() {
        String rates = "";

        try {
            URL url = new URL("http://localhost:8080/ExchangeRest/webresources/exchangerest");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() != 200) {
                return "";
            }

            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                rates += sc.nextLine();
            }
            sc.close();
            
        } catch (MalformedURLException ex) {
            return "";
        } catch (IOException ex) {
            return "";
        }
        
        
        return rates;
    }
   
        
}

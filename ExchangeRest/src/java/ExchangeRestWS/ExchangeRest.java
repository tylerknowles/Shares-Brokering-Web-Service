/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExchangeRestWS;

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
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author tyler
 */
@Path("exchangerest")
public class ExchangeRest {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ExchangeRest
     */
    public ExchangeRest() {
    }

    /**
     * Retrieves representation of an instance of ExchangeRestWS.ExchangeRest
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getRates() {
        
        String inline = "";
        
        try {
            URL url = new URL("http://data.fixer.io/api/latest?access_key=5edefdfaa8b9a9e8570f7373888ac690");
        
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() != 200) {
                return getRatesFromFile();
            }
            
            Scanner sc = new Scanner(url.openStream());       
            while(sc.hasNext())
            {
                inline+=sc.nextLine();
            }
            System.out.println(inline);
            sc.close();
            
            FileWriter fileWriter = new FileWriter("C:\\Users\\tyler\\OneDrive\\Documents\\Cloud\\Rates.txt");
            fileWriter.write(inline);
            fileWriter.close();
        
            return inline;
            
        } catch (MalformedURLException ex) {
            return getRatesFromFile();
        } catch (IOException ex) {
            return getRatesFromFile();
        }
        
    }

    
    private String getRatesFromFile() {
        String rates = "";
        try {
            Scanner sc = new Scanner(new File("C:\\Users\\tyler\\OneDrive\\Documents\\Cloud\\Rates.txt"));
            while (sc.hasNextLine()) 
            {
                rates+=sc.nextLine();
            }
        } catch (FileNotFoundException ex) {
            return "";
        }
        
    return rates;
}
    
    
    
}

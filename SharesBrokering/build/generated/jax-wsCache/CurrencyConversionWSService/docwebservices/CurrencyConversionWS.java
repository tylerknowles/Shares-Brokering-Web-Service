
package docwebservices;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.11-b150120.1832
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CurrencyConversionWS", targetNamespace = "http://DOCwebServices/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CurrencyConversionWS {


    /**
     * 
     * @return
     *     returns java.util.List<java.lang.String>
     */
    @WebMethod(operationName = "GetCurrencyCodes")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "GetCurrencyCodes", targetNamespace = "http://DOCwebServices/", className = "docwebservices.GetCurrencyCodes")
    @ResponseWrapper(localName = "GetCurrencyCodesResponse", targetNamespace = "http://DOCwebServices/", className = "docwebservices.GetCurrencyCodesResponse")
    @Action(input = "http://DOCwebServices/CurrencyConversionWS/GetCurrencyCodesRequest", output = "http://DOCwebServices/CurrencyConversionWS/GetCurrencyCodesResponse")
    public List<String> getCurrencyCodes();

    /**
     * 
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "Temp")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "Temp", targetNamespace = "http://DOCwebServices/", className = "docwebservices.Temp")
    @ResponseWrapper(localName = "TempResponse", targetNamespace = "http://DOCwebServices/", className = "docwebservices.TempResponse")
    @Action(input = "http://DOCwebServices/CurrencyConversionWS/TempRequest", output = "http://DOCwebServices/CurrencyConversionWS/TempResponse")
    public String temp();

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.util.List<java.lang.Double>
     */
    @WebMethod(operationName = "GetConversionRates")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "GetConversionRates", targetNamespace = "http://DOCwebServices/", className = "docwebservices.GetConversionRates")
    @ResponseWrapper(localName = "GetConversionRatesResponse", targetNamespace = "http://DOCwebServices/", className = "docwebservices.GetConversionRatesResponse")
    @Action(input = "http://DOCwebServices/CurrencyConversionWS/GetConversionRatesRequest", output = "http://DOCwebServices/CurrencyConversionWS/GetConversionRatesResponse")
    public List<Double> getConversionRates(
        @WebParam(name = "arg0", targetNamespace = "")
        List<String> arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

}

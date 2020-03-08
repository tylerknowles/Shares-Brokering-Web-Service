
package docwebservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the docwebservices package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetConversionRates_QNAME = new QName("http://DOCwebServices/", "GetConversionRates");
    private final static QName _GetConversionRatesResponse_QNAME = new QName("http://DOCwebServices/", "GetConversionRatesResponse");
    private final static QName _GetCurrencyCodes_QNAME = new QName("http://DOCwebServices/", "GetCurrencyCodes");
    private final static QName _GetCurrencyCodesResponse_QNAME = new QName("http://DOCwebServices/", "GetCurrencyCodesResponse");
    private final static QName _Temp_QNAME = new QName("http://DOCwebServices/", "Temp");
    private final static QName _TempResponse_QNAME = new QName("http://DOCwebServices/", "TempResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: docwebservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetConversionRates }
     * 
     */
    public GetConversionRates createGetConversionRates() {
        return new GetConversionRates();
    }

    /**
     * Create an instance of {@link GetConversionRatesResponse }
     * 
     */
    public GetConversionRatesResponse createGetConversionRatesResponse() {
        return new GetConversionRatesResponse();
    }

    /**
     * Create an instance of {@link GetCurrencyCodes }
     * 
     */
    public GetCurrencyCodes createGetCurrencyCodes() {
        return new GetCurrencyCodes();
    }

    /**
     * Create an instance of {@link GetCurrencyCodesResponse }
     * 
     */
    public GetCurrencyCodesResponse createGetCurrencyCodesResponse() {
        return new GetCurrencyCodesResponse();
    }

    /**
     * Create an instance of {@link Temp }
     * 
     */
    public Temp createTemp() {
        return new Temp();
    }

    /**
     * Create an instance of {@link TempResponse }
     * 
     */
    public TempResponse createTempResponse() {
        return new TempResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConversionRates }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://DOCwebServices/", name = "GetConversionRates")
    public JAXBElement<GetConversionRates> createGetConversionRates(GetConversionRates value) {
        return new JAXBElement<GetConversionRates>(_GetConversionRates_QNAME, GetConversionRates.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConversionRatesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://DOCwebServices/", name = "GetConversionRatesResponse")
    public JAXBElement<GetConversionRatesResponse> createGetConversionRatesResponse(GetConversionRatesResponse value) {
        return new JAXBElement<GetConversionRatesResponse>(_GetConversionRatesResponse_QNAME, GetConversionRatesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrencyCodes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://DOCwebServices/", name = "GetCurrencyCodes")
    public JAXBElement<GetCurrencyCodes> createGetCurrencyCodes(GetCurrencyCodes value) {
        return new JAXBElement<GetCurrencyCodes>(_GetCurrencyCodes_QNAME, GetCurrencyCodes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrencyCodesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://DOCwebServices/", name = "GetCurrencyCodesResponse")
    public JAXBElement<GetCurrencyCodesResponse> createGetCurrencyCodesResponse(GetCurrencyCodesResponse value) {
        return new JAXBElement<GetCurrencyCodesResponse>(_GetCurrencyCodesResponse_QNAME, GetCurrencyCodesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Temp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://DOCwebServices/", name = "Temp")
    public JAXBElement<Temp> createTemp(Temp value) {
        return new JAXBElement<Temp>(_Temp_QNAME, Temp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TempResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://DOCwebServices/", name = "TempResponse")
    public JAXBElement<TempResponse> createTempResponse(TempResponse value) {
        return new JAXBElement<TempResponse>(_TempResponse_QNAME, TempResponse.class, null, value);
    }

}

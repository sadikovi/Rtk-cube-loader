
package oracle.obiee.admin.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the oracle.obiee.admin.ws package. 
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

    private final static QName _StartExtenderResponse_QNAME = new QName("http://ws.admin.obiee.oracle/", "startExtenderResponse");
    private final static QName _CallProcedureResponse_QNAME = new QName("http://ws.admin.obiee.oracle/", "callProcedureResponse");
    private final static QName _CallProcedureWithResultsResponse_QNAME = new QName("http://ws.admin.obiee.oracle/", "callProcedureWithResultsResponse");
    private final static QName _ValidationException_QNAME = new QName("http://ws.admin.obiee.oracle/", "ValidationException");
    private final static QName _StartExtender_QNAME = new QName("http://ws.admin.obiee.oracle/", "startExtender");
    private final static QName _CallProcedure_QNAME = new QName("http://ws.admin.obiee.oracle/", "callProcedure");
    private final static QName _ServerException_QNAME = new QName("http://ws.admin.obiee.oracle/", "ServerException");
    private final static QName _CallProcedureWithResults_QNAME = new QName("http://ws.admin.obiee.oracle/", "callProcedureWithResults");
    private final static QName _UnsupportedTypeException_QNAME = new QName("http://ws.admin.obiee.oracle/", "UnsupportedTypeException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: oracle.obiee.admin.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StartExtender }
     * 
     */
    public StartExtender createStartExtender() {
        return new StartExtender();
    }

    /**
     * Create an instance of {@link ServerException }
     * 
     */
    public ServerException createServerException() {
        return new ServerException();
    }

    /**
     * Create an instance of {@link CallProcedure }
     * 
     */
    public CallProcedure createCallProcedure() {
        return new CallProcedure();
    }

    /**
     * Create an instance of {@link UnsupportedTypeException }
     * 
     */
    public UnsupportedTypeException createUnsupportedTypeException() {
        return new UnsupportedTypeException();
    }

    /**
     * Create an instance of {@link CallProcedureWithResults }
     * 
     */
    public CallProcedureWithResults createCallProcedureWithResults() {
        return new CallProcedureWithResults();
    }

    /**
     * Create an instance of {@link ValidationException }
     * 
     */
    public ValidationException createValidationException() {
        return new ValidationException();
    }

    /**
     * Create an instance of {@link CallProcedureWithResultsResponse }
     * 
     */
    public CallProcedureWithResultsResponse createCallProcedureWithResultsResponse() {
        return new CallProcedureWithResultsResponse();
    }

    /**
     * Create an instance of {@link CallProcedureResponse }
     * 
     */
    public CallProcedureResponse createCallProcedureResponse() {
        return new CallProcedureResponse();
    }

    /**
     * Create an instance of {@link StartExtenderResponse }
     * 
     */
    public StartExtenderResponse createStartExtenderResponse() {
        return new StartExtenderResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartExtenderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.admin.obiee.oracle/", name = "startExtenderResponse")
    public JAXBElement<StartExtenderResponse> createStartExtenderResponse(StartExtenderResponse value) {
        return new JAXBElement<StartExtenderResponse>(_StartExtenderResponse_QNAME, StartExtenderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CallProcedureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.admin.obiee.oracle/", name = "callProcedureResponse")
    public JAXBElement<CallProcedureResponse> createCallProcedureResponse(CallProcedureResponse value) {
        return new JAXBElement<CallProcedureResponse>(_CallProcedureResponse_QNAME, CallProcedureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CallProcedureWithResultsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.admin.obiee.oracle/", name = "callProcedureWithResultsResponse")
    public JAXBElement<CallProcedureWithResultsResponse> createCallProcedureWithResultsResponse(CallProcedureWithResultsResponse value) {
        return new JAXBElement<CallProcedureWithResultsResponse>(_CallProcedureWithResultsResponse_QNAME, CallProcedureWithResultsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidationException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.admin.obiee.oracle/", name = "ValidationException")
    public JAXBElement<ValidationException> createValidationException(ValidationException value) {
        return new JAXBElement<ValidationException>(_ValidationException_QNAME, ValidationException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartExtender }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.admin.obiee.oracle/", name = "startExtender")
    public JAXBElement<StartExtender> createStartExtender(StartExtender value) {
        return new JAXBElement<StartExtender>(_StartExtender_QNAME, StartExtender.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CallProcedure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.admin.obiee.oracle/", name = "callProcedure")
    public JAXBElement<CallProcedure> createCallProcedure(CallProcedure value) {
        return new JAXBElement<CallProcedure>(_CallProcedure_QNAME, CallProcedure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServerException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.admin.obiee.oracle/", name = "ServerException")
    public JAXBElement<ServerException> createServerException(ServerException value) {
        return new JAXBElement<ServerException>(_ServerException_QNAME, ServerException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CallProcedureWithResults }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.admin.obiee.oracle/", name = "callProcedureWithResults")
    public JAXBElement<CallProcedureWithResults> createCallProcedureWithResults(CallProcedureWithResults value) {
        return new JAXBElement<CallProcedureWithResults>(_CallProcedureWithResults_QNAME, CallProcedureWithResults.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnsupportedTypeException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.admin.obiee.oracle/", name = "UnsupportedTypeException")
    public JAXBElement<UnsupportedTypeException> createUnsupportedTypeException(UnsupportedTypeException value) {
        return new JAXBElement<UnsupportedTypeException>(_UnsupportedTypeException_QNAME, UnsupportedTypeException.class, null, value);
    }

}

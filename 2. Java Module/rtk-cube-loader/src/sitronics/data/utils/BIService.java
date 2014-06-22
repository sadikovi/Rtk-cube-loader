package sitronics.data.utils;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import oracle.bi.web.soap.SAWSessionService;

import oracle.obiee.admin.ws.AdminService;

import sitronics.data.utils.consts.Consts;

public class BIService {
    /*
     * If you do not have a class which contains the BI_ANALYTICS_URL constant
     * [Scheme + :// + ServerName + : + ServerPort], for instance "http://msk-02-orabits.tsretail.ru:9704"
     * then you have to declare a constant with appropriate reference to bi server here
     * for instance, static String BI_ANALYTICS_URL = "http://msk-02-orabits.tsretail.ru:9704"
     */
    
    public BIService() {
        super();
    }
    
    /**
     * Method returns server address for wsdl as URL type.
     * 
     * @param biServerURL
     * @return URL wsdlLocation
     */
    public static URL getWSDLLocationURL(String biServerURL)  {
        String wsdlLocation = null;
        if (biServerURL == null || biServerURL.length() <= 0) {
            wsdlLocation = Consts.getPM().BI_ANALYTICS_URL + "/analytics/" + "saw.dll/wsdl/v6";
        } else {
            wsdlLocation = biServerURL + "/analytics/" + "saw.dll/wsdl/v6";
        }
        
        URL wsdlLocationURL = SAWSessionService.class.getResource(wsdlLocation);
        try {
            if (wsdlLocationURL == null) {
              URL baseUrl = new File(".").toURL();
              wsdlLocationURL = new URL(baseUrl, wsdlLocation);
            }
        } catch (MalformedURLException e) {
            //Failed to create wsdlLocationURL
            e.printStackTrace();
        }
        
        return wsdlLocationURL;
    }
    
    /**
     * Method returns QName for bi server.
     * 
     * @param service
     * @return
     */
    public static QName getQName(String service)  {
        /*try services 
         * MetadataService
         * SAWSessionService
         */
        QName qName = new QName("urn://oracle.bi.webservices/v6", service);
        
        return qName;
    }
    
    public static URL getAdminLocationURL(String biServerURL) {
        try {
            URL wsdlLocationURL;
            
            URL baseUrl = AdminService.class.getResource(".");
            if (baseUrl == null) {
                wsdlLocationURL = AdminService.class.getResource(biServerURL + "/AdminService/AdminService?WSDL");
                if (wsdlLocationURL == null) {
                    baseUrl = new File(".").toURL();
                    wsdlLocationURL = new URL(baseUrl, biServerURL + "/AdminService/AdminService?WSDL");
                }
            } else {
                if (!baseUrl.getPath().endsWith("/")) {
                    baseUrl = new URL(baseUrl, baseUrl.getPath() + "/");
                }
                wsdlLocationURL = new URL(baseUrl, biServerURL + "/AdminService/AdminService?WSDL");
            }
            
            return wsdlLocationURL;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Method returns QName for Web Services for SOA.
     * 
     * @return
     */
    public static QName getAdminQName()  {
        /*try services 
         * MetadataService
         * SAWSessionService
         */
        QName qName = new QName("http://ws.admin.obiee.oracle/", "AdminService");
        
        return qName;
    }
}

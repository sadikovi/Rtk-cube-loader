package sitronics.data;

import java.net.URL;

import java.util.List;

import javax.xml.namespace.QName;

import oracle.bi.web.soap.MetadataService;
import oracle.bi.web.soap.MetadataServiceSoap;
import oracle.bi.web.soap.SASubjectArea;

import sitronics.data.utils.BIService;
import sitronics.data.utils.consts.Consts;


public class PMData {
    
    public PMData() {
        super();
    }

    /**
     * Method returns particular subject area information including tables and columns.
     * The result is represented by json string.
     * @param biServerURL
     * @param sessionID
     * @return String jsonSubjectArea
     */
    public static String getSubjectAreaMetadata(String biServerURL, String sessionID) {
        
        String jsonSubjectAreas = null;
        try {
            URL wsdlLocationURL = BIService.getWSDLLocationURL(biServerURL);
            QName qName = BIService.getQName(Consts.getPM().METADATA_SERVICE);
            
            MetadataService metadataService = new MetadataService(wsdlLocationURL, qName);
            MetadataServiceSoap metadataServiceSoap = metadataService.getMetadataServiceSoap();
            
            List<SASubjectArea> areas = metadataServiceSoap.getSubjectAreas(sessionID);

            jsonSubjectAreas = "[ ";
            if (areas.size() == 0) {
                return "[ { \"subjectArea\" : \"unknown\" } ]";
            }
            for (int i=0;i<areas.size();i++) {
                jsonSubjectAreas += "{ \"subjectArea\" : " + areas.get(i).getName()  + " }";
                jsonSubjectAreas += (i<areas.size()-1)?", " : "";
            }
            jsonSubjectAreas += " ]";
            
            return jsonSubjectAreas;
        } catch (Exception e) {
            return "[ { \"subjectArea\" : \"unknown\" } ]";
        }
    }
    
    public static boolean hasPresentationModel(String biServerURL, String sessionID, String PMName) {
        try {
            URL wsdlLocationURL = BIService.getWSDLLocationURL(biServerURL);
            QName qName = BIService.getQName(Consts.getPM().METADATA_SERVICE);
            
            MetadataService metadataService = new MetadataService(wsdlLocationURL, qName);
            MetadataServiceSoap metadataServiceSoap = metadataService.getMetadataServiceSoap();
            
            List<SASubjectArea> areas = metadataServiceSoap.getSubjectAreas(sessionID);
            for (int i=0;i<areas.size();i++) {
                if (areas.get(i).getName().equalsIgnoreCase("\"" + PMName + "\"")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}

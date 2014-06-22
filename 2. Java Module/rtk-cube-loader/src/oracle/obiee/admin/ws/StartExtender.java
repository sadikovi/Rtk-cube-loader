
package oracle.obiee.admin.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for startExtender complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="startExtender">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="adminBeanServerUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="biAdminUser" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="biAdminPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="jobID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="connectionDetails" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "startExtender", propOrder = {
    "adminBeanServerUrl",
    "biAdminUser",
    "biAdminPassword",
    "jobID",
    "connectionDetails"
})
public class StartExtender {

    @XmlElement(required = true)
    protected String adminBeanServerUrl;
    @XmlElement(required = true)
    protected String biAdminUser;
    @XmlElement(required = true)
    protected String biAdminPassword;
    @XmlElement(required = true)
    protected String jobID;
    @XmlElement(required = true)
    protected String connectionDetails;

    /**
     * Gets the value of the adminBeanServerUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdminBeanServerUrl() {
        return adminBeanServerUrl;
    }

    /**
     * Sets the value of the adminBeanServerUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdminBeanServerUrl(String value) {
        this.adminBeanServerUrl = value;
    }

    /**
     * Gets the value of the biAdminUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBiAdminUser() {
        return biAdminUser;
    }

    /**
     * Sets the value of the biAdminUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBiAdminUser(String value) {
        this.biAdminUser = value;
    }

    /**
     * Gets the value of the biAdminPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBiAdminPassword() {
        return biAdminPassword;
    }

    /**
     * Sets the value of the biAdminPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBiAdminPassword(String value) {
        this.biAdminPassword = value;
    }

    /**
     * Gets the value of the jobID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobID() {
        return jobID;
    }

    /**
     * Sets the value of the jobID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobID(String value) {
        this.jobID = value;
    }

    /**
     * Gets the value of the connectionDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnectionDetails() {
        return connectionDetails;
    }

    /**
     * Sets the value of the connectionDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnectionDetails(String value) {
        this.connectionDetails = value;
    }

}

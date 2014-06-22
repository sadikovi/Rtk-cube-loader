
package oracle.obiee.admin.ws.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for results complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="results">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rows" type="{http://ws.admin.obiee.oracle/types}resultsRow" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "results", propOrder = {
    "rows"
})
public class Results {

    @XmlElement(nillable = true)
    protected List<ResultsRow> rows;

    /**
     * Gets the value of the rows property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rows property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRows().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResultsRow }
     * 
     * 
     */
    public List<ResultsRow> getRows() {
        if (rows == null) {
            rows = new ArrayList<ResultsRow>();
        }
        return this.rows;
    }

}

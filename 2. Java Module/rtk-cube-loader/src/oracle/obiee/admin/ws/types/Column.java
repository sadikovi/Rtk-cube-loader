
package oracle.obiee.admin.ws.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for column complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="column">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.admin.obiee.oracle/types}typedValue">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://ws.admin.obiee.oracle/types}types" minOccurs="0"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "column", propOrder = {
    "type",
    "value"
})
public class Column
    extends TypedValue
{

    protected Types type;
    protected Object value;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link Types }
     *     
     */
    public Types getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link Types }
     *     
     */
    public void setType(Types value) {
        this.type = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setValue(Object value) {
        this.value = value;
    }

}

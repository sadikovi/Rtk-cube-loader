
package oracle.obiee.admin.ws.types;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for types.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="types">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BINARY"/>
 *     &lt;enumeration value="BLOB"/>
 *     &lt;enumeration value="BOOLEAN"/>
 *     &lt;enumeration value="DATE"/>
 *     &lt;enumeration value="DOUBLE"/>
 *     &lt;enumeration value="FLOAT"/>
 *     &lt;enumeration value="INTEGER"/>
 *     &lt;enumeration value="NSTRING"/>
 *     &lt;enumeration value="SHORT"/>
 *     &lt;enumeration value="STRING"/>
 *     &lt;enumeration value="TIME"/>
 *     &lt;enumeration value="TIMESTAMP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "types")
@XmlEnum
public enum Types {

    BINARY,
    BLOB,
    BOOLEAN,
    DATE,
    DOUBLE,
    FLOAT,
    INTEGER,
    NSTRING,
    SHORT,
    STRING,
    TIME,
    TIMESTAMP;

    public String value() {
        return name();
    }

    public static Types fromValue(String v) {
        return valueOf(v);
    }

}

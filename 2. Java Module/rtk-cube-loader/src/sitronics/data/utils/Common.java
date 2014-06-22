package sitronics.data.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;

import java.sql.Clob;
import java.sql.SQLException;

import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Common {
    /* 
     * class for common functions 
     * all the functions must be declared as public and static 
     * 
     */
    
    public Common() {
        super();
    }

    /**
     * Returns true if string is empty or has zero length.
     * @param a
     * @return [boolean]
     */
    public static boolean isEmpty(String a) {
        if (a == null || a.length() <= 0 || a.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if Input Stream object is empty or equals null.
     * @param in
     * @return [boolean]
     */
    public static boolean isEmpty(InputStream in) {
        if (in == null || in.equals(null)) {
            return true;
        } else {
            return false;            
        }
    }

    /**
     * Returns true if Object is empty or equals null.
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null || obj.equals(null)) {
            return true;
        } else {
            return false;            
        }
    }

    /**
     * Returns true if string object contains [;], ['], [:], [&], [.] symbols.
     * @param a
     * @return
     */
    public static boolean hasUnsufficientSymbol(String a) {
        if (a.contains("\"") || a.contains(";") 
            || a.contains("'") || a.contains(":") 
            || a.contains("&") || a.contains("#") 
            || a.contains("%") || a.contains("\\")
            || a.contains("*") || a.contains("/")) {
            return true;
        }
        return false;
    }

    /**
     * Returns false once string object has one of the characters which are not allowed by pattern:
     * "a-z" and "A-Z" and "0-9" and "_".
     * 
     * @param string
     * @return
     */
    public static boolean isOnlyAllowedSymbols(String string) {
        //symbols that allowed : "a-z" and "A-Z" and "0-9" and and "-"
        Pattern pattern = Pattern.compile("[^\\_a-zA-Z0-9]");
        if (pattern.matcher(string).find()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method for string array. 
     * If array contains string method returns "true".
     * Otherwise, "false".
     * 
     * @param a
     * @param stringValue
     * @return
     */
    public static boolean contains(String[] a, String stringValue) {
        for (String t : a) {
            if (t.equals(stringValue)) {
                return true;
            } else {
                continue;
            }
        }
        
        return false;
    }
    
    /**
     * Method for integer array. 
     * If array contains integer value method returns "true".
     * Otherwise, "false".
     * 
     * @param a
     * @param intValue
     * @return
     */
    public static boolean contains(int[] a, int intValue) {
        for (int t : a) {
            if (t == intValue) {
                return true;
            } else {
                continue;
            }
        }
        
        return false;
    }
    
    /**
     * Method for double array. 
     * If array contains double value method returns "true".
     * Otherwise, "false".
     * 
     * @param a
     * @param doubleValue
     * @return
     */
    public static boolean contains(double[] a, double doubleValue) {
        for (double t : a) {
            if (t == doubleValue) {
                return true;
            } else {
                continue;
            }
        }
        
        return false;
    }
    
    /**
     * [For Excel.java] Returns false once string object has one of the characters which are not allowed by pattern:
     * "0-9" and ",".
     * 
     * @param string
     * @param columnPosition
     * @return
     */
    public static boolean isValidParentPos(String string, String columnPosition) {
        //symbols that allowed : "0-9" and ","
        string = string.replace(".", ",");
        
        //Pattern pattern = Pattern.compile("[^,0-9]");
        Pattern pattern = Pattern.compile("[^0-9]");
        if (pattern.matcher(string).find()) {
            return false;
        } else if (Common.contains(string.split(","), "")) {
            return false;
        } else {
            if (Common.contains(string.split(","), columnPosition)) {
                return false;   
            } else {
                return true;
            }
        }
    }
    
    public static boolean isValidDoubleValue(String string) {
        //symbols that allowed : "0-9" and ","
        boolean isNegative = false;
        string = string.replace(".", ",");
        if (string.replace("-", "").isEmpty() || string.replace(",", "").isEmpty() || string.split(",").length > 2) {
            return false;
        } else {
            if (string.startsWith("-")) {
                string = string.substring(1);
                isNegative = true;
            }
            Pattern pattern = Pattern.compile("[^,0-9]");
            if (pattern.matcher(string).find()) {
                return false; 
            } else  {
                if (string.length() == 1) {
                    return true;
                } else {
                    if (string.startsWith("0")) {
                        if (string.startsWith(",", 1)) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return true;
                    }
                }
            }
        }
    }
    
    public static boolean isDate(String string, String mask) {
        // it is always "dd" and "mm" and "yyyy" ("yy")
        // 1. dd.mm.yyyy
        // 2. mm.dd.yyyy
        // 3. dd/mm/yyyy
        // 4. mm/dd/yyyy
        //
        try {
            String[] masks = { "dd#mm#yyyy", "mm#dd#yyyy", "dd#mm#yy", "mm#dd#yy" };
            mask = mask.replace(".", "#").replace("/","#").toLowerCase();
            
            if (string.isEmpty()) {
                return false;
            } else {
                if (string.length() > 10) {
                    return false;
                } else {
                    String dotS = ".";
                    boolean isDotS = false;
                    String slashS = "/";
                    Pattern pattern;
                    if (string.contains(dotS) && string.split("\\" + dotS).length == 3) {
                        pattern = Pattern.compile("[^" + "\\" + dotS + "0-9]");
                        isDotS = true;
                    } else if (string.contains(slashS) && string.split(slashS).length == 3) {
                        pattern = Pattern.compile("[^" + slashS + "0-9]");
                    } else {
                        return false;
                    }
                    
                    if (pattern.matcher(string).find()) {
                        return false;
                    } else {
                        String[] b = string.split((isDotS)?"\\"+dotS:slashS);
                        for (String a : b) {
                            if (a.length() != 2 && a.length() != 4) {
                                return false;
                            } else if (a.length() == 2 && a.equals("00") || a.length() == 4 && a.equals("0000")) {
                                return false;
                            }
                        }
                        
                        int dateCounter = 0;
                        int dayCnt = 0;
                        int monthCnt = 0;
                        int yearCnt = 0;
                        int shortYearCnt = 0;
                        
                        if (mask.isEmpty()) {
                            for (String ms : masks) {
                                String[] t = ms.split("#");
                                for (int i=0; i<b.length; i++) {
                                    if (b[i].length() == t[i].length() && t[i].equals("dd") && Integer.parseInt(b[i]) < 32) {
                                        dateCounter++;
                                        dayCnt++;
                                    } else if (b[i].length() == t[i].length() && t[i].equals("mm") && Integer.parseInt(b[i]) < 13) {
                                        dateCounter++;     
                                        monthCnt++;
                                    } else {
                                        if (b[i].length() == t[i].length() && t[i].equals("yyyy") && Integer.parseInt(b[i]) > 1950) {
                                            dateCounter++;
                                            yearCnt++;
                                        } else if (b[i].length() == t[i].length() && t[i].equals("yy")) {
                                            dateCounter++;
                                            shortYearCnt++;
                                        }
                                    }
                                }
                                
                                if (dateCounter == 3 && dayCnt == 1 && monthCnt == 1 && (yearCnt == 1 || shortYearCnt == 1)) {
                                    return true;
                                }
                                dateCounter = 0; dayCnt = 0; monthCnt = 0; yearCnt = 0; shortYearCnt = 0;
                            }
                            
                            return false;
                        } else {
                            String[] t = mask.split("#");
                            for (int i=0; i<b.length; i++) {
                                if (b[i].length() == t[i].length() && t[i].equals("dd") && Integer.parseInt(b[i]) < 32) {
                                    dateCounter++;
                                    dayCnt++;
                                } else if (b[i].length() == t[i].length() && t[i].equals("mm") && Integer.parseInt(b[i]) < 13) {
                                    dateCounter++;     
                                    monthCnt++;
                                } else if (b[i].length() == t[i].length() && t[i].equals("yyyy") && Integer.parseInt(b[i]) > 1950) {
                                    dateCounter++;
                                    yearCnt++;
                                } else if (b[i].length() == t[i].length() && t[i].equals("yy")) {
                                    dateCounter++;
                                    shortYearCnt++;
                                }
                            }
                            if (dateCounter == 3 && dayCnt == 1 && monthCnt == 1 && (yearCnt == 1 || shortYearCnt == 1)) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean isNumber(String string) {
        return isValidDoubleValue(string);
    }
    
    public static boolean isInteger(String string) {
        if (isNumber(string)) {
            string = string.replace(".", ",");
            if (string.split(",").length == 1) {
                return true;
            } else {
                return false;
            }
        }
        
        return false;
    }
    
    /**
     * [For Excel.java] Returns string replaced for valid parent position
     * 
     * @param string
     * @return
     */
    public static String validParentPos(String string) {
        return string.replace(".", ",");
    }

    /**
     * Returns substring between beginIndex and endIndex.
     * 
     * @param a
     * @param beginIndex
     * @param endIndex
     * @return String
     */
    public static String substr(String a, int beginIndex, int endIndex) {
        if (beginIndex > endIndex) {
            return a.substring(endIndex, beginIndex);
        }
        if (endIndex > a.length()) {
            return a;
        }
        
        return a.substring(beginIndex, endIndex);
    }

    /**
     * Returns substring from beginIndex and length.
     * 
     * @param a
     * @param beginIndex
     * @param length
     * 
     * @return String
     */
    public static String substrL(String a, int beginIndex, int length) {        
        if (beginIndex == -1) {
            return substr(a, a.length() - length, a.length());
        }
        return substr(a, beginIndex, length);
    }

    /**
     * Returns a new string as result of replacement symbol called seprator. 
     * 
     * @param a
     * @param separator
     * @param replacer
     * @return String
     */
    public static String replaceSeparatorInString(String a, String separator, String replacer) {
        // # -- U+266F
        // ; -- U+003B
        // : -- U+003A
        // ' -- U+0027
        if (a.contains(separator)) {
            return a.replace(separator, replacer);
        }
        return a;
    }

    /**
     * Returns a new string as result of replacement symbols called seprators.
     * Consists of two strings (separators and replacers) which lengths must be the same.
     * Each symbol in a string will be replaced by replacer from another.
     * The split symbol in both strings must be "--", for instance, "#--;--:" will be replaced with "U+266F--U+003B--U+003A".
     * 
     * @param a
     * @param separators
     * @param replacers
     * @return String
     */
    public static String replaceSepArrayInString(String a, String separators, String replacers) {
        // # -- U+266F
        // ; -- U+003B
        // : -- U+003A
        // ' -- U+0027
        String[] sepArray = separators.split("--");
        String[] replaceArray = replacers.split("--");
        if (sepArray.length == replaceArray.length) {
            for (int i=0;i<sepArray.length;i++) {
                if (a.contains(sepArray[i])) {
                     a = a.replace(sepArray[i], replaceArray[i]);
                }
            }
        }
        return a;
    }
    
    /**
     * Returns a new string as result of replacement symbols called seprators.
     * Consists of two strings (separators and replacers) which lengths must be the same, and splitString.
     * Each symbol in a string will be replaced by replacer from another, divided by splitString.
     * 
     * @param a
     * @param separators
     * @param replacers
     * @return String
     */
    public static String replaceSepArrayInStringWithSplit(String a, String separators, String replacers, String splitString) {
        // # -- U+266F
        // ; -- U+003B
        // : -- U+003A
        // ' -- U+0027
        String[] sepArray = separators.split(splitString);
        String[] replaceArray = replacers.split(splitString);
        if (sepArray.length == replaceArray.length) {
            for (int i=0;i<sepArray.length;i++) {
                if (a.contains(sepArray[i])) {
                     a = a.replace(sepArray[i], replaceArray[i]);
                }
            }
        }
        return a;
    }

    /**
     * Returns a new string as a result of CLOB conversion.
     * 
     * @param clb
     * @return String
     */
    public static String clobStringConversion(Clob clb) {
        if (clb == null) {
            return  "";
        }
        try {
            StringBuffer str = new StringBuffer();
            String strng;
                       
            BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());
            
            while (( strng = bufferRead.readLine() ) != null) {
                str.append(strng);
            }
            
            return str.toString();
        } catch (IOException e) {
            return "";
        }catch (SQLException e) {
            return "";
        }
    }

    /**
     * For int[].
     * Method join() is similar one in javascript.
     * 
     * @param a
     * @param separator
     * @return
     */
    public static String join(int[] a, String separator){
        String res = "";
        for (int element : a) {
            res += element + separator;
        }
        
        return (res.endsWith(separator)?res.substring(0, res.length() - separator.length()) : res);
    }

    /**
     * For int[].
     * Returns valid pivot columns positions string joined with separator.
     * If pivot columns positions are empty then empty string is returned.
     * 
     * @param a
     * @param separator
     * @return
     */
    public static String validPivotColumns(int[] a, String separator){
        if (a.length <= 1) {
            return "";
        }
        return join(a, separator);
    }

    /**
     * For String[].
     * Method join() is similar one in javascript.
     * 
     * @param a
     * @param separator
     * @return
     */
    public static String join(String[] a, String separator){
        String res = "";
        for (String element : a) {
            res += element + separator;
        }
        
        return (res.endsWith(separator)?res.substring(0, res.length() - separator.length()) : res);
    }

    /**
     * Returns integer array converted.
     * 
     * @param a
     * @return
     */
    public static int[] convertToIntArray(String[] a) {
        try {
            int[] b = new int[a.length];
            for (int i=0; i<a.length; i++) {
                b[i] = Integer.parseInt(a[i]);
            }
            
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * For String[].
     * Returns valid pivot columns positions string joined with separator.
     * If pivot columns positions are empty then empty string is returned.
     * 
     * @param a
     * @param separator
     * @return
     */
    public static String validPivotColumns(String[] a, String separator){
        if (a.length <= 1) {
            return "";
        }
        return join(a, separator);
    }
    
    // for [XMLGen.java and Repository.java]
    /**
     * [XMLGen.java and Repository.java].
     * Writes DOM to the file using filepath.
     * 
     * @param dom
     * @param filePath
     */
    public static void writeDomToTheFile(Document dom, String filePath) {
        FileOutputStream is;
        PrintStream pis;
        //dom to string
        String output = "";
        try {
            //create transformer to parse document to string
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            StringWriter writer = new StringWriter();
            t.transform(new DOMSource(dom), new StreamResult(writer));
            output = writer.getBuffer().toString();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        //write output to file
        try {
            is = new FileOutputStream(filePath);
            pis = new PrintStream(is);
            pis.println(output);
            
            pis.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * [XMLGen.java and Repository.java].
     * Writes XML String to the file using filepath.
     * 
     * @param xmlstring
     * @param filePath
     */
    public static void writeXMLStringToTheFile(String xmlstring, String filePath) {
        FileOutputStream is;
        PrintStream pis;
        try {
            is = new FileOutputStream(filePath);
            pis = new PrintStream(is);
            pis.println(xmlstring);
            
            pis.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /* methods for generating xml */
    
    /**
     * Method for generating common (dummy) XML.
     * Used for generating dummy DOM to create Element objects.
     * 
     * @return
     */
    public static Document getDom() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document domTree = null;
        String xmlstring =  "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>" +
                            "<DocumentElement xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                            "</DocumentElement>";
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            //domTree = db.parse(new ByteArrayInputStream(xmlstring.getBytes()));
            domTree = db.parse(new InputSource(new StringReader(xmlstring)));
        } catch (ParserConfigurationException pcE) {
            pcE.printStackTrace();
        } catch (SAXException saxE) {
            saxE.printStackTrace();
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
        
        return domTree;
    }
    
    /**
     * [XMLGen.java].
     * Method for generating XML. Must be used if you want to generate repository XUDML with "Declare" tag.
     * isadikov: Method to generate dom from xml which is already completed in the method in XMLGen.java
     * 
     * @return
     */
    public static Document generateDom() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document domTree = null; //"iso-8859-1", "windows-1252"
        String xmlstring = "" +
                           "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>" +
                            "<Repository xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
                            "<DECLARE>" +
                            "</DECLARE>" + 
                            "</Repository>";
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            //domTree = db.parse(new ByteArrayInputStream(xmlstring.getBytes()));
            domTree = db.parse(new InputSource(new StringReader(xmlstring)));
        } catch (ParserConfigurationException pcE) {
            pcE.printStackTrace();
        } catch (SAXException saxE) {
            saxE.printStackTrace();
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
        
        return domTree;
    }
    
    //method to generate om from random xml string

    /**
     * Method for generating XML from an xmlString [more common than generateDom() method].
     * 
     * @param xmlstring
     * @return
     */
    public static Document generateDom(String xmlstring) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document domTree = null;

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            //domTree = db.parse(new ByteArrayInputStream(xmlstring.getBytes()));
            domTree = db.parse(new InputSource(new StringReader(xmlstring)));
        } catch (ParserConfigurationException pcE) {
            pcE.printStackTrace();
        } catch (SAXException saxE) {
            saxE.printStackTrace();
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
        
        return domTree;
    }

    /**
     * Method to convert DOM structure to XML string.
     * It allows all the operations for String() objects.
     * 
     * @param dom
     * @return
     */
    public static String DomToXMLString(Document dom) {
        String output = "";
        try {
            //create transformer to parse document to string
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            StringWriter writer = new StringWriter();
            t.transform(new DOMSource(dom), new StreamResult(writer));
            output = writer.getBuffer().toString();
            
            return output;
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            return e.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
    
    /* replacer for special characters : "&", "<", ">" */
    /**
     * Method to replace special characters in XML.
     * ["&", "<", ">"]. Can be used after DOM to XMLString convertion.
     * 
     * @param xmlstring
     * @return
     */
    public static String XMLReplaceSpecCharacters(String xmlstring) {
        String amp = "&amp;"; // &
        String lt = "&lt;"; // <
        String gt = "&gt;"; // >
        
        return xmlstring.replace(amp, "&").replace(lt, "<").replace(gt, ">");
    }
}

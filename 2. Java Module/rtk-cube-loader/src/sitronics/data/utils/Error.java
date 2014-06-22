package sitronics.data.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;


public class Error {
    static String code;
    static String message;
    
    public static final String COMMON_E_CODE = "001";
    public static final String PM_E_CODE = "002";
    public static final String EX_E_CODE = "003";
    public static final String DB_E_CODE = "004";
    public static final String RE_E_CODE = "005";
    
    public Error() {
        super();
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    /**
     * Returns a new string with escaped characters
     * @param a
     * @return String
     */
    public static String escapeCharacters(String a) {
        String escape = "\\--;--:--\"--'--[--]--{--}";
        String replace = "\\u005C--\\u003B--\\u003A--\\u0022--\\u0027--\\u005B--\\u005D--\\u0028--\\u0029";
        
        return Common.replaceSepArrayInString(a, escape, replace);
    }

    /**
     * @param response
     * @param cd
     * @param msg
     * @throws ServletException
     * @throws IOException
     */
    public void throwError(HttpServletResponse response, String cd,
                           String msg) throws ServletException, IOException {
        this.code = cd;
        this.message = msg;
        response.getWriter().print("{\"status\": \"-1\", \"message\": \"" +
                                   this.message + "\", \"code\": \"" +
                                   this.code + "\"}");
        response.setStatus(400);

        //throw new IOException("Bad configuration");
    }


    /**
     * Static method for throwing an error.
     * Returns json object consisted from error code and error message.
     * @param response
     * @param cd
     * @param msg
     * @throws ServletException
     * @throws IOException
     */
    public static void thrown(HttpServletResponse response, String cd,
                           String msg) throws ServletException, IOException {
        response.getWriter().print("{\"status\": \"-1\", \"message\": \"" + msg + "\", \"code\": \"" + cd + "\"}");
        response.setStatus(400);
    }
    
    /**
     * Static method for throwing an error with characters escaping.
     * Returns json object consisted from error code and error message.
     * @param response
     * @param cd
     * @param msg
     * @throws ServletException
     * @throws IOException
     */
    public static void thrownWithEscape(HttpServletResponse response, String cd,
                           String msg) throws ServletException, IOException {
        response.getWriter().print("{\"status\": \"-1\", \"message\": \"" + escapeCharacters(msg) + "\", \"code\": \"" + cd + "\"}");
        response.setStatus(400);
    }
}
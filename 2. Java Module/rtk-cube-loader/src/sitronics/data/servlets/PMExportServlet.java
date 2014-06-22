package sitronics.data.servlets;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import sitronics.data.DBImport;
import sitronics.data.utils.AuthUtil;
import sitronics.data.utils.consts.Consts;

public class PMExportServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1251";
    @SuppressWarnings("compatibility:6971131659391305101")
    private static final long serialVersionUID = 1L;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        
        String sessionID = AuthUtil.getUserID(request);
        String username = AuthUtil.getCurrentUser(sessionID, Consts.getPM().BI_ANALYTICS_URL);

        String res = "{ \"ALL\" : " + DBImport.loadUserModelNames("") + ", \"MY\" : " + DBImport.loadUserModelNames(username) + " }";
        response.getWriter().println(res);
    }
}

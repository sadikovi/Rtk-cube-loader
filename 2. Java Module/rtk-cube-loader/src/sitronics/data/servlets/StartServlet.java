package sitronics.data.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import sitronics.data.Configuration;
import sitronics.data.Repository;
import sitronics.data.Models;
import sitronics.data.utils.consts.Consts;

public class StartServlet extends HttpServlet {
    @SuppressWarnings("compatibility:5501677579559558852")
    private static final long serialVersionUID = 1L;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        String configFilePath = config.getInitParameter("configFilePath");
        if (configFilePath == null || configFilePath.equals("")) {
            configFilePath = getServletContext().getRealPath("properties/");
        }
        // 1. Load configuration
        Configuration.loadConfiguration(configFilePath + "CLProperties.ini");
        
        // 2. Models path initialisation
        Models.PATH = configFilePath + "CLModels.ini";
        /* reset CLModels.ini
        try {
            Models.resetPropertiesInstance(Models.PATH, Models.COMMENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        
        // 3. Repository initialisation
        if (Consts.getRE().CREATE_VARIABLES_ON_APP_DEPLOY) {
            String res = Repository.initialiseFilesAndRunStartProcesses();
            System.out.println(res);    
        }
    }
}

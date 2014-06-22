package sitronics.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStreamReader;

import java.io.OutputStreamWriter;

import java.util.Properties;

import sitronics.data.utils.consts.Consts;

public class Models {
    
    public static String PATH = "properties/CLModels.ini";
    public static String COMMENT = "--- MODELS LIST (model name key = model number value pairs) ---";
    
    public Models() {
        super();
    }
    
    private static Models Instance = new Models();
    
    public static Models getInstance() {
        return Instance;
    }
    
    public static Properties getPropertiesInstance(String filepath) throws FileNotFoundException,
                                                                              IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(new File(filepath));
        InputStreamReader fisReader = new InputStreamReader(fis, "cp1251");
        //props.load(fis);
        props.load(fisReader);
        fis.close();
        
        return props;
    }
    
    public static void setPropertiesInstance(Properties props, String filepath, String comment) throws FileNotFoundException,
                                                                    IOException {
        FileOutputStream fos = new FileOutputStream(new File(filepath));
        OutputStreamWriter fosWriter = new OutputStreamWriter(fos, "cp1251"); 
        props.store(fosWriter, comment);
        fos.close();
    }
    
    public static void resetPropertiesInstance(String filepath, String comment) throws FileNotFoundException,
                                                                       IOException {
        Properties props = new Properties();
        FileOutputStream fos = new FileOutputStream(new File(filepath));
        props.store(fos, comment);
        fos.close();
    }
    
    public static String getModelNumber(Properties props, String pmName) {
        String modelN = props.getProperty("\"" + pmName + "\"");
        
        try {
            if (modelN.startsWith("\"") && modelN.endsWith("\"")) {
                modelN = modelN.substring(1, modelN.length() - 1);
            } else if (modelN.startsWith("\"")) {
                modelN = modelN.substring(1, modelN.length());
            } else if (modelN.endsWith("\"")) {
                modelN = modelN.substring(0, modelN.length()-1);
            }
            
            return modelN.isEmpty()?Consts.getRE().MODEL_NUMBER_GEN : modelN;
        } catch (NullPointerException e) {
            return Consts.getRE().MODEL_NUMBER_GEN;
        }
    }
    
    public static void setModelNumber(Properties props, String pmName, String modelNumber) {
        props.setProperty("\"" + pmName + "\"", "\"" + modelNumber + "\"");
    }
    
    private static String getKeyValue(Properties props, String key) {
        String value = props.getProperty(key);
        
        try {
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            } else if (value.startsWith("\"")) {
                value = value.substring(1, value.length());
            } else if (value.endsWith("\"")) {
                value = value.substring(0, value.length()-1);
            }
            
            return value.isEmpty()?Consts.getRE().MODEL_NUMBER_GEN : value;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Consts.getRE().MODEL_NUMBER_GEN;
        }
    }
    
    public static String getNextModelNumber(Properties props) {
        try {
            int[] list = new int[props.keySet().size()];
            int i=0;
            
            for (Object key : props.keySet()) {
                list[i] = Integer.parseInt(getKeyValue(props, String.valueOf(key)).split("\\.")[1]);
                i++;   
            }
            
            int max = 0;
            
            for (int op : list) {
                if (max < op) { max = op; }
            }
            
            return Consts.getRE().MODEL_NUMBER_GEN.split("\\.")[0] + "." + (++max);
        } catch (Exception e) {
            //e.printStackTrace();
            return Consts.getRE().MODEL_NUMBER_GEN.split("\\.")[0] + "." + "1";
        }
    }
    
    // primary methods
    public static String getModelNumber(String pmName) {
        try {
            Properties props = getPropertiesInstance(Models.getInstance().PATH);
            return getModelNumber(props, pmName);    
        } catch (Exception e) {
            return Consts.getRE().MODEL_NUMBER_GEN;
        }
    }
    
    public static void setModelNumber(String pmName, String modelNumber) {
        try {
            Properties props = getPropertiesInstance(Models.getInstance().PATH);
            setModelNumber(props, pmName, modelNumber);
            setPropertiesInstance(props, Models.getInstance().PATH, Models.COMMENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String getNextModelNumber() {
        try {
            Properties props = getPropertiesInstance(Models.getInstance().PATH);
            return getNextModelNumber(props);    
        } catch (Exception e) {
            return Consts.getRE().MODEL_NUMBER_GEN;
        }
    }
}

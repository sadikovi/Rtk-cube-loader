package sitronics.data.utils.consts;

public class Consts {    
    /*
     * init constants class
     * PM - for presentation model class
     * EX - for excel model class
     * DB - for db connection and db model class
     * RE - for repository model class
     * MSG - for error and events descriptions
     */
    
    public Consts() {
        super();
    }
    
    public static Pm getPM() {
        return Pm.getPm();
    }
    
    public static Ex getEX() {
        return Ex.getEx();
    }
    
    public static Db getDB() {
        return Db.getDb();
    }
    
    public static Re getRE() {
        return Re.getRe();
    }
    
    public static Msg getMSG() {
        return Msg.getMsg();
    }
}

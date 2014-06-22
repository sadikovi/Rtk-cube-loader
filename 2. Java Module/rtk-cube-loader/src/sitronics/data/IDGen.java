package sitronics.data;


public class IDGen {
    
    /* class for id and uid generation */
    public IDGen() {
        super();
    }
    
    //generate prefix for id
    public static int generatePrfx(int min, int max) {
        int r = min + (int)(Math.random() * ((max - min) + 1));
        return r;
    }
    
    public static String generateSt(int flag) {
        final String alph = "abcdefghigklmnopqrstuvwyz";
        final String numb = "0123456789";
        if (flag == 1) {
            char[] resF = { 
                numb.charAt(generatePrfx(0, numb.length()-1)), 
                numb.charAt(generatePrfx(0, numb.length()-1)),
                numb.charAt(generatePrfx(0, numb.length()-1)), 
                numb.charAt(generatePrfx(0, numb.length()-1)),
                numb.charAt(generatePrfx(0, numb.length()-1)),
                alph.charAt(generatePrfx(0, alph.length()-1)),
                numb.charAt(generatePrfx(0, numb.length()-1)),
                numb.charAt(generatePrfx(0, numb.length()-1))
            };
            return new String(resF);
        } else if (flag == 2) {
            char[] resS = { 
                numb.charAt(generatePrfx(0, numb.length()-1)), 
                alph.charAt(generatePrfx(0, alph.length()-1)), 
                numb.charAt(generatePrfx(0, numb.length()-1)), 
                alph.charAt(generatePrfx(0, alph.length()-1)) 
            };
            return new String(resS);
        } else if (flag == 3) {
            char[] resT = { 
                alph.charAt(generatePrfx(0, alph.length()-1)),
                numb.charAt(generatePrfx(0, numb.length()-1)), 
                alph.charAt(generatePrfx(0, alph.length()-1)),
                numb.charAt(generatePrfx(0, numb.length()-1)), 
                numb.charAt(generatePrfx(0, numb.length()-1))
            };
            return new String(resT);
        } else if (flag == 4) {
            char[] resF = { 
                alph.charAt(generatePrfx(0, alph.length()-1)),
                numb.charAt(generatePrfx(0, numb.length()-1)), 
                alph.charAt(generatePrfx(0, alph.length()-1)),
                numb.charAt(generatePrfx(0, numb.length()-1)), 
                numb.charAt(generatePrfx(0, numb.length()-1)),
                numb.charAt(generatePrfx(0, numb.length()-1)),
                numb.charAt(generatePrfx(0, numb.length()-1)),
                alph.charAt(generatePrfx(0, alph.length()-1)),
                numb.charAt(generatePrfx(0, numb.length()-1)), 
                numb.charAt(generatePrfx(0, numb.length()-1))
            };
            return new String(resF);
        } else if (flag == 5) {
            char[] resFv = {  
                numb.charAt(generatePrfx(0, numb.length()-1)),
                numb.charAt(generatePrfx(0, numb.length()-1)),
                numb.charAt(generatePrfx(0, numb.length()-1))
            };
            return new String(resFv);
        }
        return "";
    }
    //can be only xxxxxxx:xxxxxxxx and match [1-9]([0-9])*:([0-9]
    public static String generateIdTemplate(int instanceIndex, int index, int orderNum) {
        String firstPrfx = ""; //first part: x,xxxx,xx
        String secondPrfx = ""; //second part : xxxxx,x,xx
        
        if (instanceIndex != 0) {
            firstPrfx = "" + instanceIndex + generatePrfx(100, 999) + index + orderNum;
            secondPrfx = "" + generatePrfx(10000, 99999) + instanceIndex + index + orderNum;
        } else {
            firstPrfx = "" + 9 + generatePrfx(1000, 9999) + index + orderNum;
            secondPrfx = "" + generatePrfx(10000, 99999) + 9 + index + orderNum;
        }
         
        return firstPrfx + ":" + secondPrfx;
    }
    
    //can be xxxxxxxx-xxxx-xxxxx-xxxxxxxxxx-xxx
    public static String generateUidTemplate(int instanceIndex, int index, int orderNum) {
        String firstPart = ""; // xxxxxxxx
        String secondPart = ""; //xxxx
        String thirdPart = ""; //xxxxx
        String mainPart = ""; //xxxxxxxxxx
        String talePart = ""; //xxx
        
        firstPart = generateSt(1);
        secondPart = generateSt(2);
        thirdPart = generateSt(3);
        mainPart = generateSt(4);
        talePart = generateSt(5);
        
        return firstPart + "-" + secondPart + "-" + thirdPart + "-" + mainPart + instanceIndex + index + orderNum + "-" + talePart;
    }
}

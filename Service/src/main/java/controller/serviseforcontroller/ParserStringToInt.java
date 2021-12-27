package controller.serviseforcontroller;

public class ParserStringToInt {
    public static int  [] parseArrayString (String [] str) {
       int [] result = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            try {
               result[i] = Integer.parseInt(str[i]);
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

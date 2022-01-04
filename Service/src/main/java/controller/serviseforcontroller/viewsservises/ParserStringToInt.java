package controller.serviseforcontroller.viewsservises;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public static List<Integer> parseArrayStringToListInteger (String [] str) {
        List <Integer> result = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            try {
               result.add(Integer.parseInt(str[i]));
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
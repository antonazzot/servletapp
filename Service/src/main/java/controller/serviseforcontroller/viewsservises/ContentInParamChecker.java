package controller.serviseforcontroller.viewsservises;

public class ContentInParamChecker {
    public static boolean checkParam (String ... arg) {
        boolean result = false;
        for (int i = 0; i < arg.length; i++) {
            if (arg[i]==null || arg[i].equals(""))
                result=true;
            return  result;
        }
        return result;
    }
}

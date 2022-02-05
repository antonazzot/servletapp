package controller.serviseforcontroller.viewsservises;

public class ContentInParamChecker {
    public static boolean checkParam(String... arg)  {
        for (String s : arg) {
            return s == null || s.equals("");
        }
        return false;
    }
}

package controller.serviseforcontroller.viewsservises;

public class ContentInParamChecker {
    public static boolean checkParam(String... arg) {

        for (int i = 0; i < arg.length; i++) {
            if (arg[i] == null || arg[i].equals("")) {
                return true;
            } else return false;
        }
        return false;
    }
}

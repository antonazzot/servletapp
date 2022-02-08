package controller.serviseforcontroller.viewsservises;

import helperutils.myexceptionutils.AppValidException;

public class CheckTempUserParameters {
    public static void checkParameters(String name, String login, String password, int age) throws AppValidException {
        if (name==null || login==null || password==null || age==0 || name.equals("") || login.equals("") || password.equals("") || age<0 || age>100)
            throw new AppValidException("Data for registry are not valid, please try again");
    }
}

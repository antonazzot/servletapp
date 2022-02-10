package controller.serviseforcontroller.viewsservises;

import helperutils.myexceptionutils.AppValidException;

public class CheckParametrForResetPassword {
    public static void checkParameters(String repeatPaswword, String login, String password, String email) throws AppValidException {
        if (repeatPaswword==null
                || login==null
                || password==null
                || login.equals("")
                || password.equals("")
                || repeatPaswword.equals("")
                || email.equals("")
                || !EmailValidator.validate(email)
                || !repeatPaswword.equals(password) )
            throw new AppValidException("Data for reset password are not valid, please try again");
    }

    public static void checkUserParameters(String userEmail, String userLogin, String login, String email) throws AppValidException {
        if (!userEmail.equals(email) || !userLogin.equals(login) )
            throw new AppValidException("Data for reset password are not valid, please try again");
    }
}

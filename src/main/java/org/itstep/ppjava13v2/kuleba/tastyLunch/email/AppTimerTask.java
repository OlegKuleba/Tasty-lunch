package org.itstep.ppjava13v2.kuleba.tastyLunch.email;

import org.itstep.ppjava13v2.kuleba.tastyLunch.FileParser;
import org.itstep.ppjava13v2.kuleba.tastyLunch.constants.AppConstants;

import javax.servlet.ServletContext;
import java.util.Locale;
import java.util.TimerTask;

public class AppTimerTask extends TimerTask {

    ServletContext context;

    public AppTimerTask(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        FileParser fileParser = new FileParser();
        String textForEmail;
        textForEmail = fileParser.getStringFromProperties(Locale.getDefault(), "email.menuReminder", context);

        AppSender appSender = new AppSender(AppConstants.APPLICATION_EMAIL_ADDRESS, AppConstants.APPLICATION_EMAIL_PASSWORD);
        appSender.send(AppConstants.APPLICATION_NAME, textForEmail, "gonivo.rtk@gmail.com");
    }

}

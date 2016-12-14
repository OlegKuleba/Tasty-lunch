package org.itstep.ppjava13v2.kuleba.tastyLunch.controllers;

import org.itstep.ppjava13v2.kuleba.tastyLunch.constants.AppConstants;
import org.itstep.ppjava13v2.kuleba.tastyLunch.email.AppTimerTask;
import org.itstep.ppjava13v2.kuleba.tastyLunch.services.AppMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.util.*;

@Controller
public class StartController {

    Timer timer;
    TimerTask timerTask;
    Calendar calendar;

    @Autowired
    ServletContext context;

    @RequestMapping(value = {"/", ""})
    public String index(Locale locale) {
        if (timer == null) {
            timer = new Timer(true);
            timerTask = new AppTimerTask(context);
            calendar = Calendar.getInstance();
            calendar.clear(Calendar.SECOND);
            calendar.clear(Calendar.MILLISECOND);
            calendar.add(Calendar.MINUTE, 1);
            timer.schedule(timerTask, calendar.getTime(), AppConstants.WEEK);
        }
        System.out.println("StartController worked!!!");
        return "/index";
    }
}

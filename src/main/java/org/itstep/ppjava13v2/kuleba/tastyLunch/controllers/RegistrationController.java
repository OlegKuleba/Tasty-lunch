package org.itstep.ppjava13v2.kuleba.tastyLunch.controllers;

import org.itstep.ppjava13v2.kuleba.tastyLunch.FileParser;
import org.itstep.ppjava13v2.kuleba.tastyLunch.constants.AppConstants;
import org.itstep.ppjava13v2.kuleba.tastyLunch.email.AppSender;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.User;
import org.itstep.ppjava13v2.kuleba.tastyLunch.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.util.*;

@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    @Autowired
    ServletContext context;

    @RequestMapping(value = "/registration")
    public String registration(Model model) {
        return "registration";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView indexFromRegistration(@RequestParam(value = "inputEmail") final String email, Locale locale) {

        ModelAndView modelAndView = new ModelAndView("/index");

        FileParser fileParser = new FileParser();
        final String textForEmail;
        if (userService.provideAccess(email)) {
            final String password = generatePassword();
            userService.savePassword(password, email);
            userService.addOrUpdateUser(new User(email));
            textForEmail = fileParser.getStringFromProperties(locale, "email.provideAccess", context) + " " + password;
        } else {
            textForEmail = fileParser.getStringFromProperties(locale, "email.notProvideAccess", context);
        }

        final Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                AppSender appSender = new AppSender(AppConstants.APPLICATION_EMAIL_ADDRESS, AppConstants.APPLICATION_EMAIL_PASSWORD);
                appSender.send(AppConstants.APPLICATION_NAME, textForEmail, email);
                timer.cancel();
                timer.purge();
            }
        };
        Date dateStart = new Date();
        dateStart.setTime(dateStart.getTime() + 5000);
        timer.schedule(timerTask, dateStart);
        modelAndView.addObject("access", "A letter sent to your email");

        return modelAndView;
    }

    private String generatePassword() {

        StringBuilder password = new StringBuilder();
        Random random = new Random(new Date().getTime());
        for (int i = 0; i < 6; i++) {
            char next = 0;
            int range = 10;

            switch (random.nextInt(3)) {
                case 0: {
                    next = '0';
                    range = 10;
                }
                break;
                case 1: {
                    next = 'a';
                    range = 26;
                }
                break;
                case 2: {
                    next = 'A';
                    range = 26;
                }
                break;
            }
            password.append((char) ((random.nextInt(range)) + next));
        }
        return password.toString();
    }

}

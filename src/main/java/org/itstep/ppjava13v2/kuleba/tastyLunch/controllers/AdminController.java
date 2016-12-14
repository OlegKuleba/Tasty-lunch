package org.itstep.ppjava13v2.kuleba.tastyLunch.controllers;

import org.itstep.ppjava13v2.kuleba.tastyLunch.FileCreator;
import org.itstep.ppjava13v2.kuleba.tastyLunch.FileParser;
import org.itstep.ppjava13v2.kuleba.tastyLunch.constants.AppConstants;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.Dish;
import org.itstep.ppjava13v2.kuleba.tastyLunch.entities.User;
import org.itstep.ppjava13v2.kuleba.tastyLunch.services.DishService;
import org.itstep.ppjava13v2.kuleba.tastyLunch.services.OrderService;
import org.itstep.ppjava13v2.kuleba.tastyLunch.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController implements AppConstants {

    @Autowired
    ServletContext context;

    @Autowired
    DishService dishService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/adminPage")
    public String adminPage() {
        return "adminPage";
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file) {

        ModelAndView mv = new ModelAndView("adminPage");
        String name = file.getOriginalFilename();

        if (!file.isEmpty() && (name.matches(".+\\.xls$") | name.matches(".+\\.xlsx$"))) {
            try {
                byte[] bytes = file.getBytes();

                //Загрузка файла в ПРОЭКТ
                // папка для загрузки файла - tastylunch\MenuFilesDirectory
                String path = new File(context.getRealPath("")) + File.separator + DIRECTORY_FOR_MENU_FILES + File.separator;

                File menuFilesDirectory = new File(path);
                if (menuFilesDirectory.exists()) {
                    deleteFile(menuFilesDirectory);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (menuFilesDirectory.mkdir()) {
                    File inputFile = new File(path, name);
                    BufferedOutputStream realStream = new BufferedOutputStream(new FileOutputStream(inputFile));
                    realStream.write(bytes);
                    realStream.close();
                    List<Dish> dishList = FileParser.parse(path + name);

                    for (Dish dish : dishList) {
                        dishService.addDish(dish);
                    }

                    mv.addObject("uploadFileStatus", "You successfully uploaded " + name + "!");
                }

                return mv;
            } catch (Exception e) {
                mv.addObject("uploadErrorStatus", "You failed to upload " + name + ". Maybe menu has been already uploaded. => " + e.getMessage());
                return mv;
            }
        } else {
            mv.addObject("uploadErrorStatus", "You failed to upload " + name + " because the file was empty or it was not Excel-file");
            return mv;
        }
    }

    public void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                deleteFile(f);
            }
        }
        file.delete();
    }

    @RequestMapping(value = "/deleteMenu")
    public ModelAndView test() {

        ModelAndView modelAndView = new ModelAndView("adminPage");
        dishService.removeMenuForNextWeek();
        String path = new File(context.getRealPath("")) + File.separator + DIRECTORY_FOR_MENU_FILES + File.separator;
        File menuFilesDirectory = new File(path);
        deleteFile(menuFilesDirectory);
        modelAndView.addObject("deletedMenu", "Menu has been removed");
        return modelAndView;
    }

    @RequestMapping(value = "/createOrdersReport")
    public ModelAndView createOrdersReport() {
        ModelAndView modelAndView = new ModelAndView("adminPage");
        FileCreator.writeToUsersOrdersFile(orderService.getOrdersForNextWeek(), context);
        modelAndView.addObject("creatingReport", "Report file has just created");
        return modelAndView;
    }

    @RequestMapping(value = "/createDishesReport")
    public ModelAndView createDishesReport() {
        ModelAndView modelAndView = new ModelAndView("adminPage");
        FileCreator.writeToDishesAmountFile(dishService.getDishesForNextWeek(), dishService.getAmountOfEachDish(), context);
        modelAndView.addObject("creatingReport", "Report file has just created");
        return modelAndView;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ModelAndView addUser(@RequestParam("email") String email, @RequestParam("role") String role,
                                @RequestParam("name") String name, @RequestParam("surname") String surname,
                                @RequestParam("phone") String phone, @RequestParam("groupId") String groupId) {

        role = setRole(role);
        if (userService.addUser(new User(email, name, surname, phone, groupId), role)) {
        }

        ModelAndView modelAndView = new ModelAndView("adminPage");
        return modelAndView;
    }

    private String setRole(String role) {
        switch (role.toLowerCase()) {
            case "user":
                role = "ROLE_USER";
                break;
            case "admin":
                role = "ROLE_ADMIN";
                break;
            case "all":
                role = null;
                break;
            default:
                role = "ROLE_GUEST";
                break;
        }
        return role;
    }

    @RequestMapping(value = "/searchUser", method = RequestMethod.POST)
    public ModelAndView searchUser(@RequestParam("search-param-radio") String searchParam,
                                   @RequestParam("search-word") String searchWord,
                                   @RequestParam(required = false, value = "role") String role) {

        if (role != null) {
            role = setRole(role);
        }
        List<User> userList = userService.findUser(searchParam, searchWord, role);
        ModelAndView modelAndView = new ModelAndView("adminPage");
        modelAndView.addObject("users", userList);

        return modelAndView;
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String editUser(@RequestParam("loginEmail") String loginEmail, @RequestParam("name") String name,
                    @RequestParam("lastName") String lastName, @RequestParam("phone") String phone,
                    @RequestParam("groupId") String groupId, @RequestParam("id") String idString,
                    @RequestParam("role") String role) {

        User user = new User(loginEmail, name, lastName, phone, groupId);
        long id = Long.parseLong(idString);
        role = setRole(role);

        StringBuilder answer = new StringBuilder();
        user = userService.editUser(user, id, role);
        if (user.getId() != 0) {
            String separator = "-..-";
            answer.append(user.getId()).append(separator).append(user.getLoginEmail()).append(separator);
            answer.append(user.getName()).append(separator).append(user.getLastName()).append(separator);
            answer.append(user.getPhone()).append(separator).append(user.getGroupId());
        }
        return answer.toString();
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteUser(@RequestParam("id") String idString) {

        long id = Long.parseLong(idString);
        userService.deleteUser(id);
        String result = "" + id;

        return result;
    }

}

package com.vn.controller;

import com.vn.dao.UserDAO;
import com.vn.entity.Role;
import com.vn.entity.User;
import com.vn.untils.EncrytedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class UserController {
    @Autowired
    private UserDAO userDAO;
    @RequestMapping("/showUser")
    public String showRole(ModelMap model) {
        List<User> lstUser = userDAO.findAll();
        model.addAttribute("lstUser", lstUser);
        return "ListUser";
    }
    @RequestMapping("/addUser")
    public String addUser(ModelMap model) {
        return "addUser";
    }
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addtoUser(ModelMap model, @RequestParam("userName") String userName,
                            @RequestParam("fullName") String fullName, @RequestParam("gender") Integer gender, @RequestParam("password") String password) {
        EncrytedPasswordUtils encrytedPasswordUtils = new EncrytedPasswordUtils();
        String pass = encrytedPasswordUtils.encrytePassword(password);
        User user = new User(1, userName, fullName, gender, pass);
        if(userDAO.add(user)) {
            return "redirect:/showUser";
        } else {
            return "addUser";
        }
    }
    @RequestMapping("/updateUser/{id}")
    public String showUpdateUser(ModelMap model,  @PathVariable("id") int userId) {
        User user = userDAO.findById(userId);
        EncrytedPasswordUtils encrytedPasswordUtils = new EncrytedPasswordUtils();
        String passWordTemp = encrytedPasswordUtils.encrytePassword(user.getPassword());
        user.setPassword(passWordTemp);
        model.addAttribute("user", user);
        return "updateUser";
    }
    @RequestMapping(value = "/updateUser2", method = RequestMethod.POST)
    public String updateUser(User user) {
        if(userDAO.update(user)) {
            return "redirect:/showUser";
        } else {
            return "addUser";
        }
    }
    @RequestMapping(value = "/disableUser/{id}", method = RequestMethod.GET)
    public String disableUser( @PathVariable("id") int userId) {
        User user = userDAO.findById(userId);
        user.setStatus(0);
        if(userDAO.update(user)) {
            return "redirect:/showUser";
        } else {
            return "redirect:/showUser";
        }
    }
    @RequestMapping(value = "/searchUser", method = RequestMethod.GET)
    public String searchFunction(@RequestParam("searchText") String textSearch, ModelMap model) {
        List<User> lstUser = userDAO.findLstUser(textSearch);
        model.addAttribute("lstUser", lstUser);
        return "ListUser";
    }
}

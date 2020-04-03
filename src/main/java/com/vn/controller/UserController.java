package com.vn.controller;

import com.vn.dao.RoleDAO;
import com.vn.dao.RoleUserDAO;
import com.vn.dao.UserDAO;
import com.vn.entity.Role;
import com.vn.entity.RoleUser;
import com.vn.entity.User;
import com.vn.untils.EncrytedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
@Controller
public class UserController {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private RoleUserDAO roleUserDAO;
    @RequestMapping("/showUser")
    public String showRole(ModelMap model) {
        List<User> lstUser = userDAO.findAll();
        model.addAttribute("lstUser", lstUser);
        return "ListUser";
    }
    @RequestMapping("/addUser")
    public String addUser(ModelMap model) {
        List<Role> lstRole = roleDAO.findAll();
        model.addAttribute("lstRole", lstRole);
        return "addUser";
    }
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addtoUser(ModelMap model, @RequestParam("userName") String userName,
                            @RequestParam("fullName") String fullName, @RequestParam("gender") Integer gender, @RequestParam("roleId") Integer roleId, @RequestParam("password") String password,
                            @RequestParam("roleId") Integer[] lstRoleId) {
        boolean checkInsert = false;
        EncrytedPasswordUtils encrytedPasswordUtils = new EncrytedPasswordUtils();
        String pass = encrytedPasswordUtils.encrytePassword(password);
        User user = new User(1, userName, fullName, gender, pass);
        Integer userIdTemp = userDAO.add(user);
        User userTemp = userDAO.findById(userIdTemp);
        if(userTemp != null) {
            for(Integer n : lstRoleId) {
                Role role = roleDAO.findById(n);
                RoleUser roleUserNew = new RoleUser(role, userTemp, 1);
                if(roleUserDAO.addUserRole(roleUserNew)) {
                    checkInsert = true;
                } else {
                    checkInsert = false;
                }
            }
            if(checkInsert) {
                return "redirect:/showUser";
            } else {
                return "addUser";
            }
        } else {
            return "addUser";
        }
    }
    @RequestMapping("/updateUser/{id}")
    public String showUpdateUser(ModelMap model,  @PathVariable("id") int userId) {
        List<RoleUser> lstRoleChecked = roleUserDAO.findListRoleUser(userId);
        List<Role> lstRoleTemp = new ArrayList<>();
        List<Role> lstRole = roleDAO.findAll();
        for(RoleUser roleUser : lstRoleChecked) {
            Role roleTemp = roleUser.getRole();
            lstRoleTemp.add(roleTemp);
        }
        model.addAttribute("lstRoleTemp", lstRoleTemp);
        model.addAttribute("lstRole", lstRole);
        User user = userDAO.findById(userId);
        model.addAttribute("user", user);
        return "updateUser";
    }
    @RequestMapping(value = "/updateUser2", method = RequestMethod.POST)
    public String updateUser(User user, @RequestParam("roleId") Integer[] lstRole) {
        List<RoleUser> lstRoleUser = roleUserDAO.findListRoleUser(user.getUserId());
        for(RoleUser roleUser : lstRoleUser) {
            roleUserDAO.delete(roleUser);
        }
        for(Integer n : lstRole) {
            Role role = roleDAO.findById(n);
            roleUserDAO.addUserRole(new RoleUser(role, user, 1 ));
        }
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

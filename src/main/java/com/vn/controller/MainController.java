package com.vn.controller;

import com.vn.dao.RoleDAO;
import com.vn.dao.RoleFunctionDAO;
import com.vn.dao.UserDAO;
import com.vn.entity.Functions;
import com.vn.entity.Role;
import com.vn.entity.RoleFunction;
import com.vn.entity.User;
import com.vn.untils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleFunctionDAO roleFunctionDAO;
    @Autowired
    private RoleDAO roleDAO;
    @RequestMapping("/hello")
    public String getHello() {
        List<User> lst = userDAO.findAll();
        lst.forEach(a -> {
            System.out.println(a.getFullName());
        });
        return "hihi";
    }
    @RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "welcomePage";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

        org.springframework.security.core.userdetails.User loginedUser = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "adminPage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {

        return "loginPage";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        // Sau khi user login thanh cong se co principal
        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        org.springframework.security.core.userdetails.User loginedUser = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();
        List<String> lstRole = new ArrayList<>();
        List<Functions> lstFunction = new ArrayList<>();
        Collection<GrantedAuthority> demo = loginedUser.getAuthorities();
        demo.forEach(a -> {
            String roleTemp = a.getAuthority();
            lstRole.add(roleTemp);
        });
        for(String x : lstRole) {
            Role roleTemp =roleDAO.findByRoleName(x);
            List<RoleFunction> functionsList = roleFunctionDAO.lstFunction(roleTemp.getRoleId());
            for(RoleFunction roleFunction : functionsList) {
                if(!lstFunction.contains(roleFunction.getFunction())) {
                    lstFunction.add(roleFunction.getFunction());
                }
            }
        }
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("lstFunction", lstFunction);

        return "userInfoPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            org.springframework.security.core.userdetails.User loginedUser = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }
}

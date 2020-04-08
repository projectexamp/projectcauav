package com.vn.controller;

import com.vn.dao.*;
import com.vn.entity.Functions;
import com.vn.entity.Role;
import com.vn.entity.RoleFunction;
import com.vn.entity.RoleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FunctionController {
    @Autowired
    private FuctionDAO fuctionDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private RoleFunctionDAO roleFunctionDAO;

    @Autowired
    private RoleUserDAO roleUserDAO;

    @RequestMapping("/showFunction")
    public String showFunction(ModelMap model) {
        List<Functions> lstFunction = fuctionDAO.findAll();
        model.addAttribute("lstFunction", lstFunction);
        return "ListFunction";
    }
    @RequestMapping("/addFunction")
    public String addFunction(ModelMap model) {
        return "addFunction";
    }
    @RequestMapping(value = "/addFunction", method = RequestMethod.POST)
    public String addtoFunction(ModelMap model, @RequestParam("functionName") String functionName, @RequestParam("functionUrl") String functionUrl,
                                @RequestParam("description") String description, @RequestParam("functionCode") String functionCode, @RequestParam("functionOrder") int functionOrder) {
        Functions functions = new Functions(1, functionOrder, functionUrl, functionName, description, functionCode);
        if(fuctionDAO.add(functions)) {
            return "redirect:/showFunction";
        } else {
            return "addFunction";
        }
    }
    @RequestMapping("/updateFunction/{id}")
    public String showUpdateFunction(ModelMap model,  @PathVariable("id") int functionId) {
        Functions functions = fuctionDAO.findById(functionId);
        model.addAttribute("function", functions);
        return "updateFunction";
    }
    @RequestMapping(value = "/updateFunction2", method = RequestMethod.POST)
    public String updateFunction(Functions function2) {
        if(fuctionDAO.update(function2)) {
            return "redirect:/showFunction";
        } else {
            return "addFunction";
        }
    }
    @RequestMapping(value = "/disableFuction/{id}", method = RequestMethod.GET)
    public String disableFunction( @PathVariable("id") int functionId) {
        Functions functions = fuctionDAO.findById(functionId);
        functions.setStatus(0);
        if(fuctionDAO.update(functions)) {
            return "redirect:/showFunction";
        } else {
            return "redirect:/showFunction";
        }
    }
    @RequestMapping(value = "/searchFunction", method = RequestMethod.GET)
    public String searchFunction(@RequestParam("searchText") String textSearch, ModelMap model) {
        List<Functions> lstFunction = fuctionDAO.findLstFunction(textSearch);
        model.addAttribute("lstFunction", lstFunction);
        return "ListFunction";
    }
    @RequestMapping(value = "/addDepart/{id}", method = RequestMethod.GET)
    public String addDepart(@PathVariable("id") int functionId, Principal principal, HttpServletRequest request) {
        String test = request.getRequestURI();
        User userLogin = (User) ((Authentication) principal).getPrincipal();
        com.vn.entity.User userEntity = userDAO.findUserAccount(userLogin.getUsername());
        int userId = (userDAO.findUserAccount(userLogin.getUsername()).getUserId());
        boolean check = checkFuncitonWithRole(functionId, userId, test);
        if(check) {
            return "addDepart";
        } else {
            return "redirect:/403";
        }
    }
    @RequestMapping(value = "/updateDepart/{id}", method = RequestMethod.GET)
    public String updateDepart(@PathVariable("id") int functionId, Principal principal, HttpServletRequest request) {
        User userLogin = (User) ((Authentication) principal).getPrincipal();
        String test = request.getRequestURI();
        com.vn.entity.User userEntity = userDAO.findUserAccount(userLogin.getUsername());
        int userId = (userDAO.findUserAccount(userLogin.getUsername()).getUserId());
        boolean check = checkFuncitonWithRole(functionId, userId, test);
        if(check) {
            return "updateDepart";
        } else {
            return "redirect:/403";
        }
    }
    public boolean checkFuncitonWithRole(int functionId, int userId, String url) {
        boolean check = false;
        List<RoleUser> lstRoleUser = new ArrayList<>();
        List<RoleFunction> lstRoleFunction = new ArrayList<>();
        List<Integer> lstResultId = new ArrayList<>();
        List<String> lstResultString = new ArrayList<>();
        lstRoleUser = roleUserDAO.findListRoleUser(userId);
        if(!lstRoleUser.isEmpty() && lstRoleUser != null) {
            for(RoleUser roleUser : lstRoleUser) {
                lstRoleFunction = roleFunctionDAO.lstFunction(roleUser.getRole().getRoleId());
                if(!lstRoleFunction.isEmpty() && lstRoleFunction != null) {
                    for(RoleFunction roleFunction : lstRoleFunction) {
                        int a = roleFunction.getFunction().getFunctionId();
                        lstResultId.add(a);
                        lstResultString.add(roleFunction.getFunction().getFunctionUrl());
                    }
                    if(lstResultId.contains(functionId)) {
                        for(String a : lstResultString) {
                            if(url.contains(a)) {
                                check = true;
                            }
                        }
                    }
                }
            }
        }
        return check;
    }
}

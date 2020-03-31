package com.vn.controller;

import com.vn.dao.FuctionDAO;
import com.vn.dao.RoleDAO;
import com.vn.entity.Functions;
import com.vn.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RoleController {
    @Autowired
    private RoleDAO roleDAO;

    @RequestMapping("/showRole")
    public String showRole(ModelMap model) {
        List<Role> lstRole = roleDAO.findAll();
        model.addAttribute("lstRole", lstRole);
        return "ListRole";
    }
    @RequestMapping("/addRole")
    public String addRole(ModelMap model) {
        return "addRole";
    }
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public String addtoRole(ModelMap model, @RequestParam("roleName") String roleName,
                                @RequestParam("description") String description, @RequestParam("roleCode") String roleCode, @RequestParam("roleOrder") int roleOrder) {
        Role role = new Role(1, roleName, description, roleCode, roleOrder);
        if(roleDAO.add(role)) {
            return "redirect:/showRole";
        } else {
            return "addRole";
        }
    }
    @RequestMapping("/updateRole/{id}")
    public String showUpdateFunction(ModelMap model,  @PathVariable("id") int roleId) {
        Role role = roleDAO.findById(roleId);
        model.addAttribute("role", role);
        return "updateRole";
    }
    @RequestMapping(value = "/updateRole2", method = RequestMethod.POST)
    public String updateRole(Role role) {
        if(roleDAO.update(role)) {
            return "redirect:/showRole";
        } else {
            return "addRole";
        }
    }
    @RequestMapping(value = "/disableRole/{id}", method = RequestMethod.GET)
    public String disableFunction( @PathVariable("id") int roleId) {
        Role role = roleDAO.findById(roleId);
        role.setStatus(0);
        if(roleDAO.update(role)) {
            return "redirect:/showRole";
        } else {
            return "redirect:/showRole";
        }
    }
    @RequestMapping(value = "/searchRole", method = RequestMethod.GET)
    public String searchFunction(@RequestParam("searchText") String textSearch, ModelMap model) {
        List<Role> lstRole = roleDAO.findLstRole(textSearch);
        model.addAttribute("lstRole", lstRole);
        return "ListRole";
    }
}

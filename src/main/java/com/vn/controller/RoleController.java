package com.vn.controller;

import com.vn.dao.FuctionDAO;
import com.vn.dao.RoleDAO;
import com.vn.dao.RoleFunctionDAO;
import com.vn.entity.Functions;
import com.vn.entity.Role;
import com.vn.entity.RoleFunction;
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

    @Autowired
    private RoleFunctionDAO roleFunctionDAO;

    @Autowired
    private FuctionDAO fuctionDAO;

    @RequestMapping("/showRole")
    public String showRole(ModelMap model) {
        List<Role> lstRole = roleDAO.findAll();
        model.addAttribute("lstRole", lstRole);
        return "ListRole";
    }
    @RequestMapping("/addRole")
    public String addRole(ModelMap model) {
        List<Functions> lstFunction = fuctionDAO.findAll();
        model.addAttribute("lstFunction", lstFunction);
        return "addRole";
    }
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public String addtoRole(ModelMap model, @RequestParam("roleName") String roleName,
                                @RequestParam("description") String description, @RequestParam("roleCode") String roleCode, @RequestParam("roleOrder") int roleOrder,
                            @RequestParam("functionId") Integer[] arrFunctionId) {
        boolean checkInsert = false;
        Role role = new Role(1, roleName, description, roleCode, roleOrder);
        Integer idTemp = roleDAO.add(role);
        Role roleToAdd = roleDAO.findById(idTemp);
        if(idTemp != null && idTemp != 0) {
            for(Integer a : arrFunctionId) {
                Functions functions = fuctionDAO.findById(a);
                if(roleFunctionDAO.addRoleFunction(new RoleFunction(functions, roleToAdd, 1))) {
                    checkInsert = true;
                } else {
                    checkInsert = false;
                }
            } if(checkInsert) {
                return "redirect:/showRole";
            } else {
                return "addRole";
            }
        } else {
            return "addRole";
        }
    }
    @RequestMapping("/updateRole/{id}")
    public String showUpdateFunction(ModelMap model,  @PathVariable("id") int roleId) {
        List<Functions> lstFunction = fuctionDAO.findAll();
        model.addAttribute("lstFunction", lstFunction);
        Role role = roleDAO.findById(roleId);
        model.addAttribute("role", role);
        return "updateRole";
    }
    @RequestMapping(value = "/updateRole2", method = RequestMethod.POST)
    public String updateRole(Role role, @RequestParam("functionId")Integer[] functionId) {
        List<RoleFunction> lstRoleFunction = roleFunctionDAO.lstFunction(role.getRoleId());
        for(RoleFunction roleFunctionTemp : lstRoleFunction) {
            roleFunctionDAO.deleteRoleFunction(roleFunctionTemp);
        }
        for(Integer n : functionId) {
            Functions functions = fuctionDAO.findById(n);
            roleFunctionDAO.addRoleFunction(new RoleFunction(functions, role, 1));
        }
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

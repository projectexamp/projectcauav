package com.vn.controller;

import com.vn.dao.FuctionDAO;
import com.vn.entity.Functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FunctionController {
    @Autowired
    private FuctionDAO fuctionDAO;

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
}

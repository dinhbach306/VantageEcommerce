package com.magnus.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    @RequestMapping(value={"/index","/" }, method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liang.minisearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author zhangliang
 */
@Controller
public class TemplateController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex(Model model) {
        return "index";
    }
}

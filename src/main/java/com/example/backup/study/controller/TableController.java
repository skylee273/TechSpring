package com.example.backup.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TableController {


    @GetMapping("/table")
    public String table(){
        return "tables";
    }

}

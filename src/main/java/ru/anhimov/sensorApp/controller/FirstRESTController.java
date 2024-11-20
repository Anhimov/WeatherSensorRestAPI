package ru.anhimov.sensorApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Neil Alishev
 */
@Controller
@RequestMapping("/api")
public class FirstRESTController {

    @ResponseBody
    @GetMapping("/sayHello")
    public String sayHello() {
         return "Hello world!";
    }
}


package com.example.demo;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class LogController {


    @GetMapping({"/", "/hello"})
    public String hello() {
        return "hello";
    }

    @GetMapping({"/chart"})
    public String drawChart() {
            return "chart";
    }
}
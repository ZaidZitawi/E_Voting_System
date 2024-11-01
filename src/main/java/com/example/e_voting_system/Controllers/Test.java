package com.example.e_voting_system.Controllers;


import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class Test {


    @GetMapping("/t")
    public String test() {
        return "Hello World";
    }

}

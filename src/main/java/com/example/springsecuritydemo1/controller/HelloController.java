package com.example.springsecuritydemo1.controller;


import com.example.springsecuritydemo1.entity.Users;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class HelloController {

    @GetMapping("hello")
    public String hello(){
        return "Hello World";
    }

    @GetMapping("index")
    public String index(){
        return "hello index";
    }

    //@Secured({"ROLE_sale","ROLE_manager"})
    //@PreAuthorize("hasAnyAuthority('admins')")
    @PostAuthorize("hasAnyAuthority('admins')")
    @GetMapping("update")
    public String update(){
        System.out.println("update...");
        System.out.println("update...");
        return "hello update";
    }

    @GetMapping("getAll")
    @PostAuthorize("hasAnyAuthority('admins')")
    @PostFilter("filterObject.userName == 'admin1'")
    public List<Users> getAll(){

        List<Users> list = new ArrayList<>();
        list.add(new Users(1,"admin1","admin123"));
        list.add(new Users(2,"admin2","admin123"));
        System.out.println(list);
        return list;
    }
}

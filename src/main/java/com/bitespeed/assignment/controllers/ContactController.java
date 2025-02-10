package com.bitespeed.assignment.controllers;


import com.bitespeed.assignment.models.Contact;
import com.bitespeed.assignment.models.ContactResponse;
import com.bitespeed.assignment.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ContactController {
    @Autowired
    private ContactService contactService;

    @RequestMapping("/")
    public String root(){
        return "index";
    }


    @RequestMapping("/identify")
    @ResponseBody
    public ResponseEntity<?> identify(@RequestParam("emial") String email,
                                                   @RequestParam("phoneNumber") String phone){

        ContactResponse ret = contactService.identify(email, phone);
        Map<String, ContactResponse> map = new HashMap<>();
        map.put("contact", ret);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(map);
    }

    @RequestMapping("/getAllData")
    @ResponseBody
    public ResponseEntity<?> getAllData(){
        List<Contact> contacts = contactService.getAllData();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(contacts);
    }

}

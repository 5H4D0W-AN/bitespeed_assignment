package com.bitespeed.assignment.services;

import com.bitespeed.assignment.models.Contact;
import com.bitespeed.assignment.models.ContactResponse;

import java.util.List;
import java.util.Map;

public interface ContactService {
    ContactResponse identify(String email, String phone);

    List<Contact> getAllData();
}

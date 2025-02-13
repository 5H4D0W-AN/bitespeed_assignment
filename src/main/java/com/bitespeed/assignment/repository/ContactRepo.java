package com.bitespeed.assignment.repository;

import com.bitespeed.assignment.models.Contact;

import java.util.ArrayList;
import java.util.List;

public interface ContactRepo {
    List<Contact> findByEmailOrPhoneNumber(String email, String phoneNumber);

    List<Contact> findAllByGroup(Long primaryId);

    void save(Contact contact);
    void update(Contact contact);

    List<Contact> getAllContacts();

    List<Contact> findAllByIds(ArrayList<Long> longs);
}

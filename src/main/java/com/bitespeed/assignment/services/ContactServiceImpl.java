package com.bitespeed.assignment.services;

import com.bitespeed.assignment.models.Contact;
import com.bitespeed.assignment.models.ContactResponse;
import com.bitespeed.assignment.repository.ContactRepo;
import com.bitespeed.assignment.repository.ContactRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepo contactRepo;

    @Override
    public ContactResponse identify(String email, String phone) {
        List<Contact> matches = contactRepo.findByEmailOrPhoneNumber(email, phone);
        if(matches.isEmpty()){
            Contact newContact = new Contact();
            newContact.setEmial(email);
            newContact.setPhoneNumber(phone);
            newContact.setLinkPrecedence("primary");
            newContact.setCreatedAt(LocalDateTime.now());
            newContact.setUpdatedAt(LocalDateTime.now());
            contactRepo.save(newContact);
            List<String> emails = new ArrayList<>();
            List<String> phones = new ArrayList<>();
            if(email != null) emails.add(email);
            if(phone != null) phones.add(phone);
            return new ContactResponse(newContact.getId(), emails, phones, new ArrayList<Long>());
        }

        List<Contact> primaryContacts = matches.stream().filter(c->"primary".equals(c.getLinkPrecedence())).collect(Collectors.toList());
        Collections.sort(primaryContacts, Comparator.comparing(Contact :: getCreatedAt));
        int ind = 1;
        while(ind < primaryContacts.size()){
            Contact c = primaryContacts.get(ind++);
            c.setLinkPrecedence("secondary");
            c.setUpdatedAt(LocalDateTime.now());
            c.setLinkedId(primaryContacts.get(0).getId());
            contactRepo.update(c);
        }


        List<Contact> allContacts = contactRepo.findAllByGroup(primaryContacts.get(0).getId());

        Set<String> emails = new HashSet<>();
        Set<String> phones = new HashSet<>();
        for(Contact c: allContacts){
            if(c.getEmial() != null) emails.add(c.getEmial());
            if(c.getPhoneNumber() != null) phones.add(c.getPhoneNumber());
        }
        boolean updateNeeded = false;
        if(email != null && !emails.contains(email)) updateNeeded = true;
        if(phone != null && !phones.contains(phone)) updateNeeded = true;
        if(updateNeeded){
            Contact newContact = new Contact();
            newContact.setEmial(email);
            newContact.setPhoneNumber(phone);
            newContact.setLinkPrecedence("secondary");
            newContact.setCreatedAt(LocalDateTime.now());
            newContact.setUpdatedAt(LocalDateTime.now());
            newContact.setLinkedId(primaryContacts.get(0).getId());
            contactRepo.save(newContact);
            if(email != null) emails.add(email);
            if(phone != null) phones.add(phone);
        }
        List<String> respEmails = new ArrayList<>();
        List<String> respPhones = new ArrayList<>();
        List<Long> secondaryIds = new ArrayList<>();
        Contact primary = primaryContacts.get(0);
        if(primary.getEmial() != null) respEmails.add(primary.getEmial());
        if(primary.getPhoneNumber() != null) respPhones.add(primary.getPhoneNumber());
        for(Contact c: allContacts){
            if(!c.getId().equals(primary.getId())){
                if(c.getEmial() != null && !respEmails.contains(c.getEmial())){
                    respEmails.add(c.getEmial());
                }
                if (c.getPhoneNumber() != null && !respPhones.contains(c.getPhoneNumber())) {
                    respPhones.add(c.getPhoneNumber());
                }
                secondaryIds.add(c.getId());
            }
        }
        return new ContactResponse(primary.getId(), respEmails, respPhones, secondaryIds);
    }

    @Override
    public List<Contact> getAllData() {
        return contactRepo.getAllContacts();
    }
}

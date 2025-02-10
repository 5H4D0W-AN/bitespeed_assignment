package com.bitespeed.assignment.models;

import java.util.List;

public class ContactResponse {
    private Long primaryId;
    private List<String> emails;
    private List<String> phoneNumbers;
    private List<Long> secondaryContactIds;

    public Long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(Long primaryId) {
        this.primaryId = primaryId;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<Long> getSecondaryContactIds() {
        return secondaryContactIds;
    }

    public void setSecondaryContactIds(List<Long> secondaryContactIds) {
        this.secondaryContactIds = secondaryContactIds;
    }

    public ContactResponse(Long primaryId, List<String> emails, List<String> phoneNumbers, List<Long> secondaryContactIds) {
        this.primaryId = primaryId;
        this.emails = emails;
        this.phoneNumbers = phoneNumbers;
        this.secondaryContactIds = secondaryContactIds;
    }

    public ContactResponse() {
    }
}

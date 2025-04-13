package org.example.webboot.service;

import org.example.webboot.dto.ContactDTO;
import org.example.webboot.util.PageResult;

public interface ContactService {
    int addContact(ContactDTO contactDTO);
    void updateContact(ContactDTO contactDTO);
    void deleteContact(int id);
    ContactDTO getContactById(int id);
    PageResult<ContactDTO> getContactList(Integer page, Integer size, String name);
}
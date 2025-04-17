package org.example.webboot.controller;

import org.example.webboot.dto.ContactDTO;
import org.example.webboot.entity.Contact;
import org.example.webboot.repository.ContactRepository;
import org.example.webboot.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);


    @Autowired
    private ContactRepository contactRepository;

    // 获取联系人列表，支持分页和模糊查询
    @GetMapping("/list")
    public ResponseEntity<?> getContactList(
            @RequestParam(required = false, defaultValue = "") String searchQuery,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Contact> contactPage = contactService.getContacts(searchQuery, pageable);

            // 转换为DTO列表
            List<ContactDTO> dtoList = contactPage.getContent().stream()
                    .map(contact -> {
                        ContactDTO dto = new ContactDTO();
                        dto.setId(contact.getId());
                        dto.setName(contact.getName());
                        dto.setProvince(contact.getProvince());
                        dto.setCity(contact.getCity());
                        dto.setAddress(contact.getAddress());
                        dto.setPostalCode(contact.getPostalCode());
                        dto.setCreateTime(contact.getCreateTime() != null
                                ? contact.getCreateTime().toString()
                                : "未设置");
                        return dto;
                    })
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("data", dtoList);
            response.put("total", contactPage.getTotalElements());
            response.put("page", page);
            response.put("size", size);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("获取联系人列表失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "获取联系人列表失败",
                            "message", e.getMessage(),
                            "timestamp", LocalDateTime.now()
                    ));
        }
    }

    // 新增联系人
    @PostMapping
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
        Contact savedContact = contactService.addContact(contact);
        return ResponseEntity.ok(savedContact);
    }

    // 更新联系人
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contact) {
        contact.setId(id);
        Contact updatedContact = contactService.updateContact(contact);
        return ResponseEntity.ok(updatedContact);
    }

    // 删除联系人
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}

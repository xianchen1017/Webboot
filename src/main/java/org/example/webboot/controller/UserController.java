package org.example.webboot.controller;

import org.example.webboot.dto.ContactDTO;
import org.example.webboot.dto.ListResult;
import org.example.webboot.entity.Contact;
import org.example.webboot.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private ContactService contactService;

    // 用户列表的分页接口
    @GetMapping("/list")
    public ListResult<ContactDTO> getAllContacts(@RequestParam int page, @RequestParam int size) {
        try {
            List<ContactDTO> contacts = contactService.getAllContacts();
            int total = contacts.size();
            int start = (page - 1) * size;
            int end = Math.min(start + size, total);
            List<ContactDTO> paginatedContacts = contacts.subList(start, end);

            // 返回分页数据
            return new ListResult<>(paginatedContacts, total);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("查询联系人失败", e);
        }
    }

    @GetMapping("/{id}")
    public ContactDTO getContactById(@PathVariable int id) {
        return contactService.getContactById(id);
    }

    @PostMapping
    public ContactDTO createContact(@RequestBody ContactDTO contactDTO) {
        return contactService.createContact(contactDTO);
    }

    @PutMapping("/{id}")
    public ContactDTO updateContact(@PathVariable int id, @RequestBody ContactDTO contactDTO) {
        return contactService.updateContact(id, contactDTO);
    }

    // 删除联系人
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {  // id 类型改为 Long
        boolean isDeleted = contactService.deleteContact(id);  // 使用 Long 类型的 id
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}


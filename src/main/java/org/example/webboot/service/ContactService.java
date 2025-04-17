package org.example.webboot.service;

import org.example.webboot.controller.ContactController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.webboot.dto.ContactDTO;
import org.example.webboot.entity.Contact;
import org.example.webboot.repository.ContactRepository; // 确保导入了正确的 ContactRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);


    @Autowired
    private ContactRepository contactRepository; // 使用Spring Data JPA 的 repository

    // 获取所有联系人
    public List<ContactDTO> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream()
                .map(contact -> convertToDTO(contact))
                .collect(Collectors.toList());
    }

    // 分页获取联系人
    public Page<ContactDTO> getContactsPaged(Pageable pageable) {
        Page<Contact> contactPage = contactRepository.findAll(pageable); // 使用 Spring Data JPA 的分页
        return contactPage.map(contact -> convertToDTO(contact)); // 使用 map 方法进行转换
    }

    // 分页获取联系人列表，支持按姓名模糊查询
    public Page<Contact> getContacts(String name, Pageable pageable) {
        log.info("执行查询 - 参数: name={}, page={}, size={}",
                name, pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<Contact> result = name != null && !name.isEmpty()
                    ? contactRepository.findByNameContaining(name, pageable)
                    : contactRepository.findAll(pageable);

            log.info("查询结果: {}条记录", result.getNumberOfElements());
            result.getContent().forEach(contact ->
                    log.debug("联系人数据: id={}, name={}", contact.getId(), contact.getName()));

            return result;
        } catch (Exception e) {
            log.error("查询联系人失败", e);
            throw e;
        }
    }


    // 将 Contact 转换为 ContactDTO
    private ContactDTO convertToDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setName(contact.getName());
        contactDTO.setProvince(contact.getProvince());
        contactDTO.setCity(contact.getCity());
        contactDTO.setAddress(contact.getAddress());
        contactDTO.setPostalCode(contact.getPostalCode());
        // 安全处理createTime
        if(contact.getCreateTime() != null) {
            contactDTO.setCreateTime(contact.getCreateTime().toString());
        } else {
            contactDTO.setCreateTime("未设置"); // 设置默认值
        }
        return contactDTO;
    }

    // 根据ID获取联系人
    public ContactDTO getContactById(int id) {
        Contact contact = contactRepository.findById((long) id).orElseThrow(() -> new RuntimeException("Contact not found"));
        return convertToDTO(contact);
    }

    // 创建联系人
    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact contact = convertToEntity(contactDTO);
        contact = contactRepository.save(contact); // 使用 Spring Data JPA 的保存方法
        return convertToDTO(contact);
    }

    // 更新联系人
    public ContactDTO updateContact(int id, ContactDTO contactDTO) {
        Contact contact = convertToEntity(contactDTO);
        contact.setId(id);
        contact = contactRepository.save(contact); // 使用 Spring Data JPA 的保存方法
        return convertToDTO(contact);
    }

    // 删除联系人
    public void deleteContact(int id) {
        contactRepository.deleteById((long) id); // 使用 Spring Data JPA 的删除方法
    }

    // 将 ContactDTO 转换为实体
    private Contact convertToEntity(ContactDTO dto) {
        Contact contact = new Contact();
        contact.setId(dto.getId());
        contact.setName(dto.getName());
        contact.setProvince(dto.getProvince());
        contact.setCity(dto.getCity());
        contact.setAddress(dto.getAddress());
        contact.setPostalCode(dto.getPostalCode());
        return contact;
    }

    // 获取所有联系人
    public List<Contact> getAllContacts(int page, int size, String searchQuery) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Contact> contactPage;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            contactPage = (Page<Contact>) contactRepository.findByNameContaining(searchQuery, (Pageable) pageable);
        } else {
            contactPage = contactRepository.findAll(pageable);
        }

        return contactPage.getContent();
    }

    // 新增联系人
    public Contact addContact(Contact contact) {
        return contactRepository.save(contact);
    }

    // 更新联系人
    public Contact updateContact( Contact contact) {
        return contactRepository.save(contact);
    }

    // 删除联系人
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}

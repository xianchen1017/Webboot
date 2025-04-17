package org.example.webboot.repository;

import org.example.webboot.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    // 使用Spring Data JPA的分页查询
    Page<Contact> findByNameContaining(String name, Pageable pageable);
}

package org.example.webboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 确保是 Long 类型

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "province", length = 50)
    private String province;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime;

    // 默认构造函数
    public Contact() {}

    public Long getId() {
        return id;
    }

    // 添加 setCreateTime 方法
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public LocalDateTime getCreateTime() {
        return this.createTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Column(name = "created_by")
    private String createdBy;

}


package org.example.webboot.entity;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "contact") // 明确指定表名
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "create_time")
    private LocalDateTime createTime;

    // 默认构造函数
    public Contact() {}

    // 用于测试数据的构造函数
    public Contact(String name, String province, String city, String address, String postalCode) {
        this.name = name;
        this.province = province;
        this.city = city;
        this.address = address;
        this.postalCode = postalCode;
    }

    public int getId() {
        return Math.toIntExact(id);
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
}


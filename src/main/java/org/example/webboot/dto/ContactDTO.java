package org.example.webboot.dto;

import org.example.webboot.entity.Contact;

public class ContactDTO {
    private int id;
    private String name;
    private String province;
    private String city;
    private String address;
    private String postalCode;
    private String createTime;

    // 如果没有无参构造器，手动添加
    public ContactDTO() {}

    // 添加一个构造函数，用于将 Contact 实体转换为 ContactDTO
    public ContactDTO(Contact contact) {
        this.id = contact.getId();
        this.name = contact.getName();
        this.province = contact.getProvince();
        this.city = contact.getCity();
        this.address = contact.getAddress();
        this.postalCode = contact.getPostalCode();
        this.createTime = contact.getCreateTime().toString();
    }

    // getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

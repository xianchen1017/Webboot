package org.example.webboot.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Contact {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String province;
    private String city;
    private String address;
    private String company;
    private String position;
    private String notes;
    private int userId;
    private Date createTime;
    private Date updateTime;
}
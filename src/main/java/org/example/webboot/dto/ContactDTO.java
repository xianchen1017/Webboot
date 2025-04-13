package org.example.webboot.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ContactDTO {
    private int id;

    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotBlank(message = "电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    private String email;
    private String province;
    private String city;
    private String address;
    private String company;
    private String position;
    private String notes;
}
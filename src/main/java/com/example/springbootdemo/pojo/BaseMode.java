package com.example.springbootdemo.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class BaseMode {
    Long id;
    String createdBy;
    String modifiedBy;
    Date createdAt;
    Date modifiedAt;
}

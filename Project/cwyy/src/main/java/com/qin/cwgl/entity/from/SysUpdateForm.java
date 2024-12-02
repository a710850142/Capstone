package com.qin.cwgl.entity.from;

import lombok.Data;

@Data
public class SysUpdateForm {
    private String id;
    private String account;
    private String password;
    private String name;
    private String phone;
    private int type;
    private int status;
}

package com.qin.cwgl.entity.from;

import lombok.Data;

/**
 * 登录表单 Login Form 用于处理用户登录时的数据
 */
@Data
public class SysLoginForm {
    private String username;
    private String password;
    private String captcha;
    private String uuid;


}

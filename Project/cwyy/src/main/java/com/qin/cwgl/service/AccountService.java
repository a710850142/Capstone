package com.qin.cwgl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.cwgl.common.utils.PageUtils;

import com.qin.cwgl.entity.AccountEntity;
import com.qin.cwgl.entity.from.SysUpdateForm;


import java.util.List;
import java.util.Map;

//服务接口，定义了账户管理需要实现的基本方法

public interface AccountService extends IService<AccountEntity> {
//分页查询账户信息
    PageUtils queryPage(Map<String, Object> params);
//根据账户名获取账户信息
    public AccountEntity getByaccount(String account);
//获取所有账户信息
    public List<AccountEntity> allaccount(Map<String,Object> map);
//处理账户的新增或更新操作
    public AccountEntity pd(SysUpdateForm form);
}


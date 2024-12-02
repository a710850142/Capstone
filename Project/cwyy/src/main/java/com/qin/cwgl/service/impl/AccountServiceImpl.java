package com.qin.cwgl.service.impl;

import com.qin.cwgl.common.utils.Constant;
import com.qin.cwgl.common.utils.PageUtils;
import com.qin.cwgl.common.utils.Query;
import com.qin.cwgl.entity.AccountEntity;
import com.qin.cwgl.entity.from.SysUpdateForm;
import com.qin.cwgl.service.AccountService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qin.cwgl.dao.AccountDao;

//AccountService 接口的实现 Implementation of AccountService interface
//使用 @Service 注解标记为服务类
//继承了 ServiceImpl<AccountDao, AccountEntity> 获取基础的 CRUD 功能
@Service("accountService")
public class AccountServiceImpl extends ServiceImpl<AccountDao, AccountEntity> implements AccountService {

    @Autowired
    private AccountDao accountDao;
// 分页查询，支持按用户名、锁定状态和手机号筛选
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        if(params.get("userMobile").equals(""))
        {
            IPage<AccountEntity> page = this.page(
                    new Query<AccountEntity>().getPage(params),
                    new QueryWrapper<AccountEntity>()
                            .like("account", params.get("userName"))
                            .like("status",params.get("isLock"))
            );
            return new PageUtils(page);
        }else
        {

            IPage<AccountEntity> page = this.page(
                    new Query<AccountEntity>().getPage(params),
                    new QueryWrapper<AccountEntity>()
                            .like("account", params.get("userName"))
                            .like("status",params.get("isLock"))
                            .like("phone",params.get("userMobile"))
            );
            return new PageUtils(page);
        }



    }
// 根据账户名查询账户信息
    @Override
    public AccountEntity getByaccount(String account) {
        AccountEntity accountEntity=accountDao.getByaccount(account);
        return accountEntity;
    }
//获取所有账户信息
    @Override
    public List<AccountEntity> allaccount(Map<String, Object> map) {
        List<AccountEntity> list =new ArrayList<>();
        list=accountDao.allaccount(map);
        return list;
    }

    // 处理账户新增/更新
    // 如果账户不存在，创建新账户
    // 如果账户存在，更新已有账户信息
    @Override
    public AccountEntity pd(SysUpdateForm form) {
        AccountEntity account = new AccountEntity();
        AccountEntity xaccount = new AccountEntity();

        AccountEntity pdaccount = accountDao.getByaccount(form.getAccount());
        if(pdaccount == null )
        {
            account.setAccount(form.getAccount());
            account.setPassword(form.getPassword());
            account.setType(form.getType());
            account.setStatus(form.getStatus());
            account.setName(form.getName());
            account.setPhone(form.getPhone());
            account.setCreateTime(new Date());
            return account;
        }
        if(pdaccount.getAccount().equals(form.getAccount()))
        {
            xaccount.setId(pdaccount.getId());
            xaccount.setAccount(form.getAccount());
            xaccount.setPassword(form.getPassword());
            xaccount.setType(form.getType());
            xaccount.setStatus(form.getStatus());
            xaccount.setName(form.getName());
            xaccount.setPhone(form.getPhone());
            xaccount.setUpdateTime(new Date());
            return xaccount;
        }
        return account;
    }


}

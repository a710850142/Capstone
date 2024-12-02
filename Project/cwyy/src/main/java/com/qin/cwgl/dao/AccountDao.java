package com.qin.cwgl.dao;

import com.qin.cwgl.entity.AccountEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

//这个接口是数据访问对象Data Access Object（DAO），用于 AccountEntity 的数据库操作。它利用了 MyBatis 和 MyBatis-Plus 框架，简化了数据库访问操作
@Mapper
public interface AccountDao extends BaseMapper<AccountEntity> {

    public AccountEntity getByaccount(String account);

    public List<AccountEntity> allaccount(Map<String,Object> map);

}

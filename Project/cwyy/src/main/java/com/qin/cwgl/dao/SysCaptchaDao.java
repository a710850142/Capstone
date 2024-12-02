package com.qin.cwgl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qin.cwgl.entity.SysCaptchaEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 验证码
 */
@Repository
@Mapper
public interface SysCaptchaDao extends BaseMapper<SysCaptchaEntity> {

}

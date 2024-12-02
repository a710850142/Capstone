package com.qin.cwgl.dao;

import com.qin.cwgl.entity.PetEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qin.cwgl.entity.from.idname;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;


@Mapper
public interface PetDao extends BaseMapper<PetEntity> {
    public List<idname> selectpet(Map<String,Object> map);
}

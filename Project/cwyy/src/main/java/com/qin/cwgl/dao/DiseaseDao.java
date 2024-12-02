package com.qin.cwgl.dao;

import com.qin.cwgl.entity.DiseaseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qin.cwgl.entity.from.idname;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;


@Mapper
public interface DiseaseDao extends BaseMapper<DiseaseEntity> {

    public List<idname> selectdis();


}

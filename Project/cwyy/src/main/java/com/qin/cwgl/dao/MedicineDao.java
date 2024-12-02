package com.qin.cwgl.dao;

import com.qin.cwgl.entity.AccountEntity;
import com.qin.cwgl.entity.MedicineEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qin.cwgl.entity.from.idname;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;


@Mapper
public interface MedicineDao extends BaseMapper<MedicineEntity> {

    public List<idname> selectmed();

}

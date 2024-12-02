package com.qin.cwgl.dao;

import com.qin.cwgl.entity.AppointEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qin.cwgl.entity.from.Yuyu;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface AppointDao extends BaseMapper<AppointEntity> {

    public List<Yuyu> yuyu (Map<String, Object> map);


}

package com.qin.cwgl.dao;

import com.qin.cwgl.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qin.cwgl.entity.from.idcount;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;


@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    public double mon(OrderEntity order);

    public List<idcount> t1();
    public List<idcount> t2();
    public List<idcount> t3();
    public List<idcount> t4();
    public List<idcount> t5();
}

package com.qin.cwgl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.cwgl.common.utils.PageUtils;

import com.qin.cwgl.entity.OrderEntity;
import com.qin.cwgl.entity.from.idcount;
import com.qin.cwgl.entity.from.idname;

import java.util.List;
import java.util.Map;

public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    public double mon(OrderEntity order);

    public List<idcount> t1();
    public List<idcount> t2();
    public List<idcount> t3();
    public List<idcount> t4();
    public List<idcount> t5();

}


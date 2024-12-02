package com.qin.cwgl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.cwgl.common.utils.PageUtils;

import com.qin.cwgl.entity.AppointEntity;
import com.qin.cwgl.entity.from.Yuyu;

import java.util.List;
import java.util.Map;


public interface AppointService extends IService<AppointEntity> {

    PageUtils queryPage(Map<String, Object> params);


    public List<Yuyu> yuyu(Map<String, Object> map);
}


package com.qin.cwgl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.cwgl.common.utils.PageUtils;
import com.qin.cwgl.entity.PetEntity;
import com.qin.cwgl.entity.from.idname;

import java.util.List;
import java.util.Map;


public interface PetService extends IService<PetEntity> {

    PageUtils queryPage(Map<String, Object> params);

    public List<idname> selectpet(Map<String,Object> map);

}


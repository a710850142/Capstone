package com.qin.cwgl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.cwgl.common.utils.PageUtils;
import com.qin.cwgl.common.utils.PageUtils;
import com.qin.cwgl.entity.DiseaseEntity;
import com.qin.cwgl.entity.from.idname;

import java.util.List;
import java.util.Map;


public interface DiseaseService extends IService<DiseaseEntity> {

    PageUtils queryPage(Map<String, Object> params);
    public List<idname> selectdis();
}


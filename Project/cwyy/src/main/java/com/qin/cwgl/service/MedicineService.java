package com.qin.cwgl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qin.cwgl.common.utils.PageUtils;;
import com.qin.cwgl.entity.MedicineEntity;
import com.qin.cwgl.entity.from.idname;

import java.util.List;
import java.util.Map;


public interface MedicineService extends IService<MedicineEntity> {

    PageUtils queryPage(Map<String, Object> params);

    public List<idname>  selectmed();
}


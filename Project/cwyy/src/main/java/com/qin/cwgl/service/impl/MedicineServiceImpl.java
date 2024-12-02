package com.qin.cwgl.service.impl;

import com.qin.cwgl.common.utils.PageUtils;
import com.qin.cwgl.common.utils.Query;
import com.qin.cwgl.dao.AccountDao;
import com.qin.cwgl.entity.from.idname;
import com.qin.cwgl.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.qin.cwgl.dao.MedicineDao;
import com.qin.cwgl.entity.MedicineEntity;


@Service("medicineService")
public class MedicineServiceImpl extends ServiceImpl<MedicineDao, MedicineEntity> implements MedicineService {

    @Autowired
    private MedicineDao medicineDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MedicineEntity> page = this.page(
                new Query<MedicineEntity>().getPage(params),
                new QueryWrapper<MedicineEntity>()
                        .like("id", params.get("id"))
                        .like("name",params.get("name"))
                        .like("treatment",params.get("treatment"))
        );

        return new PageUtils(page);
    }

    @Override
    public List<idname>  selectmed() {
        List<idname> list = new ArrayList<>();
             list =   medicineDao.selectmed();

        System.out.println(list);
        return list;
    }


}

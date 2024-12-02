package com.qin.cwgl.service.impl;

import com.qin.cwgl.common.utils.Query;
import com.qin.cwgl.entity.from.idname;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qin.cwgl.common.utils.PageUtils;
import com.qin.cwgl.dao.DiseaseDao;
import com.qin.cwgl.entity.DiseaseEntity;
import com.qin.cwgl.service.DiseaseService;


@Service("diseaseService")
public class DiseaseServiceImpl extends ServiceImpl<DiseaseDao, DiseaseEntity> implements DiseaseService {

    @Autowired
    private DiseaseDao diseaseDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DiseaseEntity> page = this.page(
                new Query<DiseaseEntity>().getPage(params),
                new QueryWrapper<DiseaseEntity>()
                        .like("id", params.get("id"))
                        .like("name",params.get("name"))
                        .like("symptom",params.get("symptom"))
        );

        return new PageUtils(page);
    }


    @Override
    public List<idname> selectdis() {
        List<idname> list = new ArrayList<>();
        list = diseaseDao.selectdis();

        System.out.println(list);
        return list;
    }

}

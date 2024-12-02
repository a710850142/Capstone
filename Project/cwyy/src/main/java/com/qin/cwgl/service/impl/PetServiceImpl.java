package com.qin.cwgl.service.impl;

import com.qin.cwgl.common.utils.PageUtils;
import com.qin.cwgl.common.utils.Query;
import com.qin.cwgl.entity.from.idname;
import com.qin.cwgl.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.qin.cwgl.dao.PetDao;
import com.qin.cwgl.entity.PetEntity;


@Service("petService")
public class PetServiceImpl extends ServiceImpl<PetDao, PetEntity> implements PetService {

    @Autowired
    private  PetDao petDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {



        if (params.get("type") .equals("2") )
        {
            System.out.println("1231111");
            System.out.println(params.get("userid"));
            //params.get("userid")账号
            IPage<PetEntity>  page1 = this.page(
                    new Query<PetEntity>().getPage(params),
                    new QueryWrapper<PetEntity>()
                            .eq("userid",params.get("userid"))
                            .like("id", params.get("id"))
                            .like("name",params.get("name"))
            );

            return new PageUtils(page1);

        }else
        {
            IPage<PetEntity> page = this.page(
                    new Query<PetEntity>().getPage(params),
                    new QueryWrapper<PetEntity>()
                            .like("id", params.get("id"))
                            .like("name", params.get("name"))

            );
            return new PageUtils(page);

        }



    }

    @Override
    public List<idname> selectpet(Map<String,Object> map) {
        List<idname> list = new ArrayList<>();
        list = petDao.selectpet(map);

        System.out.println(list);
        return list;
    }

}

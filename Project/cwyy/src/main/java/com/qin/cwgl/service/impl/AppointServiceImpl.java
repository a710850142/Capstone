package com.qin.cwgl.service.impl;

import com.qin.cwgl.common.utils.PageUtils;
import com.qin.cwgl.common.utils.Query;
import com.qin.cwgl.entity.PetEntity;
import com.qin.cwgl.entity.from.Yuyu;
import com.qin.cwgl.entity.from.idname;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qin.cwgl.dao.AppointDao;
import com.qin.cwgl.entity.AppointEntity;
import com.qin.cwgl.service.AppointService;


@Service("appointService")
public class AppointServiceImpl extends ServiceImpl<AppointDao, AppointEntity> implements AppointService {

    @Autowired
    AppointDao appointDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {



        System.out.println("qwe");
        System.out.println(params);
        if (params.get("type") .equals("3") )
        {
            System.out.println("123");
            IPage<AppointEntity>  page1 = this.page(
                    new Query<AppointEntity>().getPage(params),
                    new QueryWrapper<AppointEntity>()
                            .like("id", params.get("id"))
                            .like("doctid", params.get("name"))
                            .like("doctid",params.get("userid"))
            );

            return new PageUtils(page1);

        }else
        if (params.get("type") .equals("2") )
        {
            System.out.println("qwq");

            //params.get("userid")账号
            IPage<AppointEntity>  page1 = this.page(
                    new Query<AppointEntity>().getPage(params),
                    new QueryWrapper<AppointEntity>()
                            .like("id", params.get("id"))
                            .like("doctid", params.get("name"))
                            .like("userid",params.get("userid"))
            );


            return new PageUtils(page1);

        }else
        {
            IPage<AppointEntity> page = this.page(
                    new Query<AppointEntity>().getPage(params),
                    new QueryWrapper<AppointEntity>()
                            .like("id", params.get("id"))
                            .like("doctid", params.get("name"))
            );

            return new PageUtils(page);

        }





    }

    @Override
    public List<Yuyu> yuyu(Map<String, Object> map) {

        List<Yuyu> list = new ArrayList<>();
        list = appointDao.yuyu(map);

        return list;
    }

}

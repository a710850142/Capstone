package com.qin.cwgl.service.impl;

import com.qin.cwgl.common.utils.PageUtils;
import com.qin.cwgl.common.utils.Query;
import com.qin.cwgl.entity.AppointEntity;
import com.qin.cwgl.entity.from.idcount;
import com.qin.cwgl.entity.from.idname;
import com.qin.cwgl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.qin.cwgl.dao.OrderDao;
import com.qin.cwgl.entity.OrderEntity;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        System.out.println("qwe");
        System.out.println(params);
        if (params.get("type") .equals("3") )
        {
            System.out.println("321");
            //params.get("userid")账号
            IPage<OrderEntity> page3 = this.page(
                    new Query<OrderEntity>().getPage(params),
                    new QueryWrapper<OrderEntity>()
                            .like("id", params.get("id"))
                            .like("status",params.get("status"))
                            .like("userid",params.get("userid"))
                            .like("petid",params.get("petid"))
                            .like("doctid",params.get("doctid"))
                            .like("doctid",params.get("uid"))

            );

            return new PageUtils(page3);

        }else
        if (params.get("type") .equals("2") )
        {
            System.out.println("123");
            //params.get("userid")账号
            IPage<OrderEntity> page1 = this.page(
                    new Query<OrderEntity>().getPage(params),
                    new QueryWrapper<OrderEntity>()
                            .like("id", params.get("id"))
                            .like("status",params.get("status"))
                            .like("userid",params.get("userid"))
                            .like("petid",params.get("petid"))
                            .like("doctid",params.get("doctid"))
                            .like("userid",params.get("uid"))

            );

            return new PageUtils(page1);

        }else
        {
             IPage<OrderEntity> page = this.page(
                    new Query<OrderEntity>().getPage(params),
                    new QueryWrapper<OrderEntity>()
                            .like("id", params.get("id"))
                            .like("status",params.get("status"))
                            .like("userid",params.get("userid"))
                            .like("petid",params.get("petid"))
                            .like("doctid",params.get("doctid"))


            );

            return new PageUtils(page);

        }





    }

    @Override
    public double mon(OrderEntity order) {
        double asd =orderDao.mon(order);
        return asd;
    }

    @Override
    public List<idcount> t1() {
        List<idcount> list = new ArrayList<>();
        list = orderDao.t1();
        System.out.println(list);
        return list;

    }

    @Override
    public List<idcount> t2() {
        List<idcount> list = new ArrayList<>();
        list = orderDao.t2();
        System.out.println(list);
        return list;
    }

    @Override
    public List<idcount> t3() {
        List<idcount> list = new ArrayList<>();
        list = orderDao.t3();
        System.out.println(list);
        return list;
    }

    @Override
    public List<idcount> t4() {
        List<idcount> list = new ArrayList<>();
        list = orderDao.t4();
        System.out.println(list);
        return list;
    }

    @Override
    public List<idcount> t5() {
        List<idcount> list = new ArrayList<>();
        list = orderDao.t5();
        System.out.println(list);
        return list;
    }

}

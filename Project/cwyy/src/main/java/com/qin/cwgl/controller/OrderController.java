package com.qin.cwgl.controller;

import java.util.*;


import com.qin.cwgl.entity.from.idcount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qin.cwgl.entity.OrderEntity;
import com.qin.cwgl.service.OrderService;
import com.qin.cwgl.common.utils.PageUtils;
import com.qin.cwgl.common.utils.R;


@RestController
@RequestMapping("product/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermiss("product:order:list")
    public R list(@RequestParam Map<String, Object> params){
        System.out.println(params);
        PageUtils page = orderService.queryPage(params);
        System.out.println(page.getList());
        return R.ok().put("data", page.getList()).put("count",page.getTotalCount());

    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermiss("product:order:info")
    public R info(@PathVariable("id") Integer id){
		OrderEntity order = orderService.getById(id);

        return R.ok().put("order", order);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermiss("product:order:save")
    public R save(@RequestBody OrderEntity order){
        System.out.println(order);
        order.setSumprice( String.valueOf(orderService.mon(order)));
        order.setStatus(1);
        order.setCreateTime(new Date());
        System.out.println(String.valueOf(orderService.mon(order)));
        System.out.println(123321);
        System.out.println(order);
        orderService.save(order);
        return R.ok().put("sta",1);


    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermiss("product:order:update")
    public R update(@RequestBody OrderEntity order){
		orderService.updateById(order);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermiss("product:order:delete")
    public R delete(@RequestBody Integer params){
        OrderEntity order=new OrderEntity();
        order.setId(params);
		orderService.removeById(order);

        return R.ok().put("sta",1);
    }


    @RequestMapping("/zifu")
    //@RequiresPermiss("product:order:delete")
    public R zifu(@RequestBody Integer params){
        OrderEntity order=new OrderEntity();
        order.setId(params);
        order.setStatus(2);
        orderService.updateById(order);

        return R.ok().put("sta",1);
    }

    @RequestMapping("/tj")
    //@RequiresPermiss("product:order:delete")
    public R tj(){

        List<idcount> list1=orderService.t1();

        List<Integer> l1 = new ArrayList<>();
        for (idcount idcount : list1) {
            l1.add(Integer.valueOf(idcount.getValue()));
        }


        List<idcount> list2 = new ArrayList<>();
        list2=orderService.t2();

        List<String> l2 = new ArrayList<>();
        for (idcount idcount : list2) {
            l2.add(idcount.getValue());
        }

        List<idcount> list3 = new ArrayList<>();
        list3=orderService.t3();
        List<idcount> list4 = new ArrayList<>();
        list4=orderService.t4();
        List<String> l4 = new ArrayList<>();
        for (idcount idcount : list4) {
            l4.add(idcount.getValue());
        }

        List<idcount> list5 = new ArrayList<>();
        list5=orderService.t5();

        System.out.println("-------------");
        System.out.println(l1);
        System.out.println(l2);
        System.out.println(l4);
        System.out.println(list3);
        System.out.println(list5);



        return R.ok().put("l1",l1)
                .put("l2",l2)
                .put("l3",list3)
                .put("l4",list4)
                .put("l5",list5)
                ;
    }


}

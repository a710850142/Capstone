package com.qin.cwgl.controller;

import java.util.*;
import com.qin.cwgl.entity.OrderEntity;
import com.qin.cwgl.entity.from.Yuyu;
import com.qin.cwgl.entity.from.idname;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.qin.cwgl.entity.AppointEntity;
import com.qin.cwgl.service.AppointService;
import com.qin.cwgl.common.utils.PageUtils;
import com.qin.cwgl.common.utils.R;




@RestController
@RequestMapping("product/appoint")
public class AppointController {
    @Autowired
    private AppointService appointService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermiss("product:appoint:list")
    public R list(@RequestParam Map<String, Object> params){
        System.out.println("1");
        System.out.println(params);
        PageUtils page = appointService.queryPage(params);

        return R.ok().put("data",page.getList()).put("count",page.getTotalCount());
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermiss("product:appoint:info")
    public R info(@PathVariable("id") Integer id){
		AppointEntity appoint = appointService.getById(id);

        return R.ok().put("appoint", appoint);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermiss("product:appoint:save")
    public R save(@RequestBody AppointEntity appoint){
        appoint.setCreateTime(new Date());
        appoint.setStatus(1);
        appoint.setApppointtime(new Date());
		appointService.save(appoint);

        return R.ok().put("sta",1);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermiss("product:appoint:update")
    public R update(@RequestBody AppointEntity appoint){
		appointService.updateById(appoint);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermiss("product:appoint:delete")
    public R delete(@RequestBody Integer params){


        AppointEntity appoint=new AppointEntity();
        appoint.setId(params);
        appointService.removeById(appoint);


        return R.ok().put("sta",1);


    }

    @RequestMapping("/yuyu")
    //@RequiresPermiss("product:appoint:delete")
    public R yuyu(@RequestParam Map<String, Object> params){
        System.out.println(params);

        List<Yuyu> list = new ArrayList<>();
        list  = appointService.yuyu(params);

        return R.ok().put("data",list);
    }


}

package com.qin.cwgl.controller;

import java.util.*;
import com.qin.cwgl.entity.AccountEntity;
import com.qin.cwgl.entity.from.idname;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.qin.cwgl.entity.MedicineEntity;
import com.qin.cwgl.service.MedicineService;
import com.qin.cwgl.common.utils.PageUtils;
import com.qin.cwgl.common.utils.R;




@RestController
@RequestMapping("product/medicine")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermiss("product:medicine:list")
    public R list(@RequestParam Map<String, Object> params){
        System.out.println(params);
        PageUtils page = medicineService.queryPage(params);

        return R.ok().put("data", page.getList()).put("count",page.getTotalCount());
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermiss("product:medicine:info")
    public R info(@PathVariable("id") Integer id){
		MedicineEntity medicine = medicineService.getById(id);

        return R.ok().put("medicine", medicine);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermiss("product:medicine:save")
    public R save(@RequestBody MedicineEntity medicine){


        if (medicine.getId() != null )
        {
            medicine.setUpdateTime(new Date());
            medicineService.updateById(medicine);
            System.out.println(medicine);
            return R.ok().put("sta",1);
        }else
        {
            medicine.setCreateTime(new Date());
            medicineService.save(medicine);
            System.out.println(medicine);
            return R.ok().put("sta",1);
        }

    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermiss("product:medicine:update")
    public R update(@RequestBody MedicineEntity medicine){
		medicineService.updateById(medicine);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermiss("product:medicine:delete")
    public R delete(@RequestBody Integer params){
        System.out.println(params);
        MedicineEntity medicine =new MedicineEntity();
        medicine.setId(params);
		medicineService.removeById(params);


        return R.ok().put("sta",1);
    }


    @RequestMapping("/selectmed")
    //@RequiresPermiss("product:medicine:delete")
    public R selectmed(){

        List<idname> list = new ArrayList<>();
        list = medicineService.selectmed();

        return R.ok().put("data",list);
    }



}

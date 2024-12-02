package com.qin.cwgl.controller;

import java.util.*;


import com.qin.cwgl.entity.from.idname;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qin.cwgl.entity.PetEntity;
import com.qin.cwgl.service.PetService;
import com.qin.cwgl.common.utils.PageUtils;
import com.qin.cwgl.common.utils.R;


@RestController
@RequestMapping("product/pet")
public class PetController {
    @Autowired
    private PetService petService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermiss("product:pet:list")
    public R list(@RequestParam Map<String, Object> params){
        System.out.println(params);

        PageUtils page = petService.queryPage(params);

        return R.ok().put("data", page.getList()).put("count",page.getTotalCount());
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermiss("product:pet:info")
    public R info(@PathVariable("id") Integer id){
		PetEntity pet = petService.getById(id);

        return R.ok().put("pet", pet);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermiss("product:pet:save")
    public R save(@RequestBody PetEntity pet){
        System.out.println(pet);
        if(pet.getId() != null)
        {
            pet.setUpdateTime(new Date());
            petService.updateById(pet);
            return R.ok().put("sta",1);

        }else
        {
            pet.setCreateTime(new Date());
            petService.save(pet);
            return R.ok().put("sta",1);

        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermiss("product:pet:update")
    public R update(@RequestBody PetEntity pet){
		petService.updateById(pet);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermiss("product:pet:delete")
    public R delete(@RequestBody Integer params){
		petService.removeByIds(Arrays.asList(params));

        return R.ok().put("sta", 1);
    }


    @RequestMapping("/selectpet")
    //@RequiresPermiss("product:medicine:delete")
    public R selectmed(@RequestBody Map<String, Object> params){

        System.out.println(params);
        System.out.println("222");
        List<idname> list = new ArrayList<>();
        list = petService.selectpet(params);

        return R.ok().put("data",list);

    }


}

package com.qin.cwgl.controller;

import java.util.*;
import com.qin.cwgl.entity.from.idname;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.qin.cwgl.entity.DiseaseEntity;
import com.qin.cwgl.service.DiseaseService;
import com.qin.cwgl.common.utils.PageUtils;
import com.qin.cwgl.common.utils.R;



@RestController
@RequestMapping("product/disease")
public class DiseaseController {
    @Autowired
    private DiseaseService diseaseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermiss("product:disease:list")
    public R list(@RequestParam Map<String, Object> params){
        System.out.println(params);
        PageUtils page = diseaseService.queryPage(params);

        System.out.println(page.getList());
        return R.ok().put("data", page.getList()).put("count",page.getTotalCount());
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermiss("product:disease:info")
    public R info(@PathVariable("id") Integer id){
		DiseaseEntity disease = diseaseService.getById(id);

        return R.ok().put("disease", disease);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermiss("product:disease:save")
    public R save(@RequestBody DiseaseEntity disease){
        System.out.println(disease);
        if(disease.getId() != null)
        {
            disease.setUpdateTime(new Date());
            diseaseService.updateById(disease);
            return R.ok().put("sta",1);

        }else
        {
            disease.setCreateTime(new Date());
            diseaseService.save(disease);
            return R.ok().put("sta",1);

        }


    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermiss("product:disease:update")
    public R update(@RequestBody DiseaseEntity disease){
		diseaseService.updateById(disease);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermiss("product:disease:delete")
    public R delete(@RequestBody Integer params){
        System.out.println(params);
        diseaseService.removeByIds(Arrays.asList(params));

        return R.ok().put("sta", 1);
    }


    @RequestMapping("/selectdis")
    //@RequiresPermiss("product:medicine:delete")
    public R selectdis(){

        List<idname> list = new ArrayList<>();
        list = diseaseService.selectdis();

        return R.ok().put("data",list);
    }


}

package com.qin.cwgl.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import com.qin.cwgl.common.utils.Constant;
import com.qin.cwgl.entity.from.SysLoginForm;
import com.qin.cwgl.entity.from.SysUpdateForm;
import com.qin.cwgl.entity.from.SysZhuceForm;
import com.qin.cwgl.service.SysCaptchaService;
import com.qin.cwgl.service.SysUserTokenService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.io.IOUtils;
import com.qin.cwgl.entity.AccountEntity;
import com.qin.cwgl.service.AccountService;
import com.qin.cwgl.common.utils.PageUtils;
import com.qin.cwgl.common.utils.R;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import static ch.qos.logback.core.joran.action.ActionConst.NULL;

//使用R工具类来处理返回结果
@RestController
@RequestMapping("product/account")
//@CrossOrigin  //跨域
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private SysCaptchaService sysCaptchaService;

    @Autowired
    private SysUserTokenService sysUserTokenService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermiss("product:account:list")
    public R list(@RequestParam Map<String,Object> params){
        System.out.println(params);

        PageUtils page = accountService.queryPage(params);
        System.out.println(page.getList());

        return R.ok().put("data",page.getList()).put("count",page.getTotalCount());
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermiss("product:account:info")
    public R info(@PathVariable("id") Integer id){
		AccountEntity account = accountService.getById(id);

        return R.ok().put("account", account);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermiss("product:account:save")
    public R save(@RequestBody SysUpdateForm form){

        AccountEntity account=accountService.pd(form);

        if(form.getId() != null && form.getId() != "" )
        {
            accountService.updateById(account);
            return R.ok().put("sta",1);
        }else
        {
            accountService.save(account);
            return R.ok().put("sta",1);
        }

    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AccountEntity account){
		accountService.updateById(account);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    //@RequiresPermiss("product:account:delete")
    public R delete(@RequestBody Integer params){
        System.out.println(params);
        AccountEntity account =new AccountEntity();
        account.setId(params);
		accountService.removeById(account);

        return R.ok().put("sta",1);
    }

    /**
     * 重置密码
     */
    @RequestMapping(value = "/Reset")
    //@RequiresPermiss("product:account:delete")
    public R Resetpas(@RequestBody Integer params){
        System.out.println(params);
        AccountEntity account =new AccountEntity();
        account.setId(params);
        account.setPassword("123456");
        account.setUpdateTime(new Date());
        accountService.updateById(account);

        return R.ok().put("sta",1);
    }


    /**
     * 验证码
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    //@RequiresPermiss("product:account:delete")
    public R login(@RequestBody SysLoginForm form){
        boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
        if(!captcha){
            return R.error("Incorrect verification code");
        }
        //用户信息
        AccountEntity user = accountService.getByaccount(form.getUsername());
        System.out.println(user);
        //账号不存在、密码错误
        if(user == null || !user.getPassword().equals(form.getPassword())) {
            return R.error("Incorrect account or password");
        }

        //账号锁定
        if(user.getStatus() == 2){
            return R.error("The account has been locked, please contact the administrator");
        }

        //生成token，并保存到数据库
        //id生产token,不是账号
        R r = sysUserTokenService.createToken(user.getId());
        System.out.println(r.put("type",user.getType()));
        System.out.println(user.getId());
        System.out.println(1111);
        System.out.println(2222);
        System.out.println(user.getId());
        return r.put("type",user.getType()).put("userid",user.getAccount()).put("uid",user.getId()).put("guuid",user.getId());
    }


    /**
     * 注册
     */
    @RequestMapping("/zhuce")
    //@RequiresPermiss("product:account:save")
    public R save(@RequestBody SysZhuceForm form){
        boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
        if(!captcha){
            return R.error("Incorrect verification code");
        }

        AccountEntity byaccount = accountService.getByaccount(form.getUsername());
        if(byaccount != null)
        {
            return R.error("Account already exists！");
        }

        AccountEntity account = new AccountEntity();
        account.setAccount(form.getUsername());
        account.setPassword(form.getPassword());
        account.setType(form.getType());
        account.setStatus(1);
        account.setCreateTime(new Date());
        System.out.println(account);
        accountService.save(account);
        System.out.println(1);
        return R.ok();
    }

    @RequestMapping(value = "/xx")
    //@RequiresPermiss("product:account:delete")
    public R xx(@RequestBody Integer params){
        System.out.println(params);

        accountService.getById(params);

        System.out.println(666);
        System.out.println(accountService.getById(params));
        return R.ok().put("data",accountService.getById(params));
    }

    /**
     * 保存
     */
    @RequestMapping("/xxsave")
    //@RequiresPermiss("product:account:save")
    public R xxsave(@RequestBody AccountEntity account){
        System.out.println(845);
        System.out.println(account);
        if(account.getPassword().equals(""))
            account.setPassword(null);
        if(account.getName().equals(""))
            account.setName(null);
        if(account.getUrl().equals(""))
            account.setUrl(null);
        if(account.getPhone().equals(""))
            account.setPhone(null);
        accountService.updateById(account);

        return R.ok().put("sta",1);

    }


}

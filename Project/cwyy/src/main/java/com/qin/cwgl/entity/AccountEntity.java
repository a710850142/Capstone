package com.qin.cwgl.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

//Used to interact with the database 是数据库表 qh_account 的映射实体类
@Data
@TableName("qh_account")//指定数据库表名
public class AccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String account;
	/**
	 * 
	 */
	private String password;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String phone;
	/**
	 * 1.管理员2.用户3.医生
	 */
	private Integer type;
	/**
	 * 1.启用2.停用
	 */
	private Integer status;
	/**
	 * 
	 */
	private String url;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

}

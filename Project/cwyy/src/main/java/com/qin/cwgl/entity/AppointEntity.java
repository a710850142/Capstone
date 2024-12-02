package com.qin.cwgl.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
@TableName("qh_appoint")
public class AppointEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 *
	 */
	private Integer userid;
	/**
	 *
	 */
	private Integer doctid;
	/**
	 *
	 */
	private Date apppointtime;
	/**
	 * 1.成功2.取消3.过期
	 */
	private Integer status;

	private String sxwu;
	/**
	 *
	 */
	private Date createTime;
	/**
	 *
	 */
	private Date updateTime;

}

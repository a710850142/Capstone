package com.qin.cwgl.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;


@Data
@TableName("qh_order")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 *
	 */
	private Integer petid;
	/**
	 *
	 */
	private Integer userid;
	/**
	 *
	 */
	private Integer doctid;
	/**
	 * 1.待支付2.已支付3.已过期
	 */
	private Integer status;
	/**
	 *
	 */
	private String sumprice ;
	/**
	 *
	 */
	private Integer dis1;
	/**
	 *
	 */
	private Integer dis2;
	/**
	 *
	 */
	private Integer dis3;
	/**
	 *
	 */
	private Integer dis4;
	/**
	 *
	 */
	private Integer dis5;
	/**
	 *
	 */
	private Integer med1;
	/**
	 *
	 */
	private Integer med2;
	/**
	 *
	 */
	private Integer med3;
	/**
	 *
	 */
	private Integer med4;
	/**
	 *
	 */
	private Integer med5;
	/**
	 *
	 */
	private Integer med6;
	/**
	 *
	 */
	private Integer med7;
	/**
	 *
	 */
	private Integer med8;
	/**
	 *
	 */
	private Date createTime;
	/**
	 *
	 */
	private Date updateTime;


	private String jy;

}

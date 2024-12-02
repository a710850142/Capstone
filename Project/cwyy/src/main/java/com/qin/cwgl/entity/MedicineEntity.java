package com.qin.cwgl.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;


@Data
@TableName("qh_medicine")
public class MedicineEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 *
	 */
	private String name;
	/**
	 * 用药说明
	 */
	private String instruction;
	/**
	 * 治疗效果
	 */
	private String treatment;
	/**
	 *
	 */
	private String price;
	/**
	 *
	 */
	private Integer stock;
	/**
	 *
	 */
	private Date createTime;
	/**
	 *
	 */
	private Date updateTime;

//	public static void main(String[] args) {
//		BigDecimal bigDecimal = new BigDecimal("");
//		bigDecimal.
//	}

}

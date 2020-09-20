package com.study.gmall.wms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:35:39
 */
@ApiModel
@Data
@TableName("wms_purchase_detail")
public class PurchaseDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "")
	private Long id;
	/**
	 * 采购单id
	 */
	@ApiModelProperty(name = "purchaseId",value = "采购单id")
	private Long purchaseId;
	/**
	 * 采购商品id
	 */
	@ApiModelProperty(name = "skuId",value = "采购商品id")
	private Long skuId;
	/**
	 * 采购数量
	 */
	@ApiModelProperty(name = "skuNum",value = "采购数量")
	private Integer skuNum;
	/**
	 * 采购金额
	 */
	@ApiModelProperty(name = "skuPrice",value = "采购金额")
	private BigDecimal skuPrice;
	/**
	 * 仓库id
	 */
	@ApiModelProperty(name = "wareId",value = "仓库id")
	private Long wareId;
	/**
	 * 状态[0新建，1已分配，2正在采购，3已完成，4采购失败]
	 */
	@ApiModelProperty(name = "status",value = "状态[0新建，1已分配，2正在采购，3已完成，4采购失败]")
	private Integer status;

}

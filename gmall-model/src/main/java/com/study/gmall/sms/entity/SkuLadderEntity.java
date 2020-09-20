package com.study.gmall.sms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品阶梯价格
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:32:31
 */
@ApiModel
@Data
@TableName("sms_sku_ladder")
public class SkuLadderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "id")
	private Long id;
	/**
	 * spu_id
	 */
	@ApiModelProperty(name = "skuId",value = "spu_id")
	private Long skuId;
	/**
	 * 满几件
	 */
	@ApiModelProperty(name = "fullCount",value = "满几件")
	private Integer fullCount;
	/**
	 * 打几折
	 */
	@ApiModelProperty(name = "discount",value = "打几折")
	private BigDecimal discount;
	/**
	 * 是否叠加其他优惠[0-不可叠加，1-可叠加]
	 */
	@ApiModelProperty(name = "addOther",value = "是否叠加其他优惠[0-不可叠加，1-可叠加]")
	private Integer addOther;

}

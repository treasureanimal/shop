package com.study.gmall.pms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * sku销售属性&值
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@ApiModel
@Data
@TableName("pms_sku_attr_value")
public class SkuAttrValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "id")
	private Long id;
	/**
	 * sku_id
	 */
	@ApiModelProperty(name = "skuId",value = "sku_id")
	private Long skuId;
	/**
	 * attr_id
	 */
	@ApiModelProperty(name = "attrId",value = "attr_id")
	private Long attrId;
	/**
	 * 销售属性名
	 */
	@ApiModelProperty(name = "attrName",value = "销售属性名")
	private String attrName;
	/**
	 * 销售属性值
	 */
	@ApiModelProperty(name = "attrValue",value = "销售属性值")
	private String attrValue;
	/**
	 * 顺序
	 */
	@ApiModelProperty(name = "sort",value = "顺序")
	private Integer sort;

}

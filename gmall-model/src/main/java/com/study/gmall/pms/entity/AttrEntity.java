package com.study.gmall.pms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品属性
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@ApiModel
@Data
@TableName("pms_attr")
public class AttrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性id
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "属性id")
	private Long id;
	/**
	 * 属性名
	 */
	@ApiModelProperty(name = "name",value = "属性名")
	private String name;
	/**
	 * 是否需要检索[0-不需要，1-需要]
	 */
	@ApiModelProperty(name = "searchType",value = "是否需要检索[0-不需要，1-需要]")
	private Integer searchType;
	/**
	 * 属性图标
	 */
	@ApiModelProperty(name = "icon",value = "属性图标")
	private String icon;
	/**
	 * 可选值列表[用逗号分隔]
	 */
	@ApiModelProperty(name = "valueSelect",value = "可选值列表[用逗号分隔]")
	private String valueSelect;
	/**
	 * 属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]
	 */
	@ApiModelProperty(name = "type",value = "属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]")
	private Integer type;
	/**
	 * 启用状态[0 - 禁用，1 - 启用]
	 */
	@ApiModelProperty(name = "enable",value = "启用状态[0 - 禁用，1 - 启用]")
	private Long enable;
	/**
	 * 快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整
	 */
	@ApiModelProperty(name = "showDesc",value = "快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整")
	private Integer showDesc;
	/**
	 * 所属分类
	 */
	@ApiModelProperty(name = "categoryId",value = "所属分类")
	private Long categoryId;
	/**
	 * 规格分组id
	 */
	@ApiModelProperty(name = "groupId",value = "规格分组id")
	private Long groupId;

}

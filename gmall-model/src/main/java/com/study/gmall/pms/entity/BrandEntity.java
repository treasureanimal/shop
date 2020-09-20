package com.study.gmall.pms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 品牌
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@ApiModel
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "品牌id")
	private Long id;
	/**
	 * 品牌名
	 */
	@ApiModelProperty(name = "name",value = "品牌名")
	private String name;
	/**
	 * 品牌logo
	 */
	@ApiModelProperty(name = "logo",value = "品牌logo")
	private String logo;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@ApiModelProperty(name = "status",value = "显示状态[0-不显示；1-显示]")
	private Integer status;
	/**
	 * 检索首字母
	 */
	@ApiModelProperty(name = "firstLetter",value = "检索首字母")
	private String firstLetter;
	/**
	 * 排序
	 */
	@ApiModelProperty(name = "sort",value = "排序")
	private Integer sort;
	/**
	 * 备注
	 */
	@ApiModelProperty(name = "remark",value = "备注")
	private String remark;

}

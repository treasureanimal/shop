package com.study.gmall.pms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 属性分组
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@ApiModel
@Data
@TableName("pms_attr_group")
public class AttrGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分组id
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "分组id")
	private Long id;
	/**
	 * 组名
	 */
	@ApiModelProperty(name = "name",value = "组名")
	private String name;
	/**
	 * 排序
	 */
	@ApiModelProperty(name = "sort",value = "排序")
	private Integer sort;
	/**
	 * 组图标
	 */
	@ApiModelProperty(name = "icon",value = "组图标")
	private String icon;
	/**
	 * 所属分类id
	 */
	@ApiModelProperty(name = "categoryId",value = "所属分类id")
	private Long categoryId;
	/**
	 * 备注
	 */
	@ApiModelProperty(name = "remark",value = "备注")
	private String remark;

}

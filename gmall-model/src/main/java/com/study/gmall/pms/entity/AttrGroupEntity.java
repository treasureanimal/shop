package com.study.gmall.pms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-11-11 20:43:49
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
	@ApiModelProperty(name = "attrGroupId",value = "分组id")
	private Long attrGroupId;
	/**
	 * 组名
	 */
	@ApiModelProperty(name = "attrGroupName",value = "组名")
	private String attrGroupName;
	/**
	 * 排序
	 */
	@ApiModelProperty(name = "sort",value = "排序")
	private Integer sort;
	/**
	 * 描述
	 */
	@ApiModelProperty(name = "descript",value = "描述")
	private String descript;
	/**
	 * 组图标
	 */
	@ApiModelProperty(name = "icon",value = "组图标")
	private String icon;
	/**
	 * 所属分类id
	 */
	@ApiModelProperty(name = "catelogId",value = "所属分类id")
	private Long catelogId;

}

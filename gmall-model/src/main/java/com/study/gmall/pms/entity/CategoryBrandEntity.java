package com.study.gmall.pms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 品牌分类关联
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@ApiModel
@Data
@TableName("pms_category_brand")
public class CategoryBrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "")
	private Long id;
	/**
	 * 品牌id
	 */
	@ApiModelProperty(name = "brandId",value = "品牌id")
	private Long brandId;
	/**
	 * 分类id
	 */
	@ApiModelProperty(name = "categoryId",value = "分类id")
	private Long categoryId;
	/**
	 * 品牌名称
	 */
	@ApiModelProperty(name = "brandName",value = "品牌名称")
	private String brandName;
	/**
	 * 分类名称
	 */
	@ApiModelProperty(name = "categoryName",value = "分类名称")
	private String categoryName;

}

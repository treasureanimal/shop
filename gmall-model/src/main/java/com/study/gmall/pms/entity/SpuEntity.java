package com.study.gmall.pms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * spu信息
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@ApiModel
@Data
@TableName("pms_spu")
public class SpuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "商品id")
	private Long id;
	/**
	 * 商品名称
	 */
	@ApiModelProperty(name = "name",value = "商品名称")
	private String name;
	/**
	 * 所属分类id
	 */
	@ApiModelProperty(name = "categoryId",value = "所属分类id")
	private Long categoryId;
	/**
	 * 品牌id
	 */
	@ApiModelProperty(name = "brandId",value = "品牌id")
	private Long brandId;
	/**
	 * 上架状态[0 - 下架，1 - 上架]
	 */
	@ApiModelProperty(name = "publishStatus",value = "上架状态[0 - 下架，1 - 上架]")
	private Integer publishStatus;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(name = "createTime",value = "创建时间")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(name = "updateTime",value = "更新时间")
	private Date updateTime;

}

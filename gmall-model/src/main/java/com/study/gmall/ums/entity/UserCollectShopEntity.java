package com.study.gmall.ums.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 关注店铺表
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
@ApiModel
@Data
@TableName("ums_user_collect_shop")
public class UserCollectShopEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "")
	private Long id;
	/**
	 * 用户id
	 */
	@ApiModelProperty(name = "userId",value = "用户id")
	private Long userId;
	/**
	 * 店铺id
	 */
	@ApiModelProperty(name = "shopId",value = "店铺id")
	private Long shopId;
	/**
	 * 店铺名
	 */
	@ApiModelProperty(name = "shopName",value = "店铺名")
	private String shopName;
	/**
	 * 店铺logo
	 */
	@ApiModelProperty(name = "shopLogo",value = "店铺logo")
	private String shopLogo;
	/**
	 * 关注时间
	 */
	@ApiModelProperty(name = "createtime",value = "关注时间")
	private Date createtime;

}

package com.study.gmall.ums.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 收货地址表
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
@ApiModel
@Data
@TableName("ums_user_address")
public class UserAddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "id")
	private Long id;
	/**
	 * member_id
	 */
	@ApiModelProperty(name = "userId",value = "member_id")
	private Long userId;
	/**
	 * 收货人
	 */
	@ApiModelProperty(name = "name",value = "收货人")
	private String name;
	/**
	 * 电话
	 */
	@ApiModelProperty(name = "phone",value = "电话")
	private String phone;
	/**
	 * 右边
	 */
	@ApiModelProperty(name = "postCode",value = "右边")
	private String postCode;
	/**
	 * 省份
	 */
	@ApiModelProperty(name = "province",value = "省份")
	private String province;
	/**
	 * 城市
	 */
	@ApiModelProperty(name = "city",value = "城市")
	private String city;
	/**
	 * 区
	 */
	@ApiModelProperty(name = "region",value = "区")
	private String region;
	/**
	 * 详细地址
	 */
	@ApiModelProperty(name = "address",value = "详细地址")
	private String address;
	/**
	 * 是否默认地址
	 */
	@ApiModelProperty(name = "defaultStatus",value = "是否默认地址")
	private Integer defaultStatus;

}

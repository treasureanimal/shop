package com.study.gmall.ums.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
@ApiModel
@Data
@TableName("ums_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "id")
	private Long id;
	/**
	 * ��Ա�ȼ�id
	 */
	@ApiModelProperty(name = "levelId",value = "��Ա�ȼ�id")
	private Long levelId;
	/**
	 * 用户名
	 */
	@ApiModelProperty(name = "username",value = "用户名")
	private String username;
	/**
	 * 密码
	 */
	@ApiModelProperty(name = "password",value = "密码")
	private String password;
	/**
	 * 盐
	 */
	@ApiModelProperty(name = "salt",value = "盐")
	private String salt;
	/**
	 * 昵称
	 */
	@ApiModelProperty(name = "nickname",value = "昵称")
	private String nickname;
	/**
	 * 手机号
	 */
	@ApiModelProperty(name = "phone",value = "手机号")
	private String phone;
	/**
	 * 邮箱
	 */
	@ApiModelProperty(name = "email",value = "邮箱")
	private String email;
	/**
	 * 头像
	 */
	@ApiModelProperty(name = "header",value = "头像")
	private String header;
	/**
	 * 性别
	 */
	@ApiModelProperty(name = "gender",value = "性别")
	private Integer gender;
	/**
	 * 生日
	 */
	@ApiModelProperty(name = "birthday",value = "生日")
	private Date birthday;
	/**
	 * 城市
	 */
	@ApiModelProperty(name = "city",value = "城市")
	private String city;
	/**
	 * 职业
	 */
	@ApiModelProperty(name = "job",value = "职业")
	private String job;
	/**
	 * 个性签名
	 */
	@ApiModelProperty(name = "sign",value = "个性签名")
	private String sign;
	/**
	 * 来源
	 */
	@ApiModelProperty(name = "sourceType",value = "来源")
	private Integer sourceType;
	/**
	 * 购物积分
	 */
	@ApiModelProperty(name = "integration",value = "购物积分")
	private Integer integration;
	/**
	 * 赠送积分
	 */
	@ApiModelProperty(name = "growth",value = "赠送积分")
	private Integer growth;
	/**
	 * 状态
	 */
	@ApiModelProperty(name = "status",value = "状态")
	private Integer status;
	/**
	 * 注册时间
	 */
	@ApiModelProperty(name = "createTime",value = "注册时间")
	private Date createTime;

}

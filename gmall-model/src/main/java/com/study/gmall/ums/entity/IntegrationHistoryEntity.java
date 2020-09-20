package com.study.gmall.ums.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 购物积分记录表
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
@ApiModel
@Data
@TableName("ums_integration_history")
public class IntegrationHistoryEntity implements Serializable {
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
	 * 创建时间
	 */
	@ApiModelProperty(name = "createTime",value = "创建时间")
	private Date createTime;
	/**
	 * 变动数量
	 */
	@ApiModelProperty(name = "count",value = "变动数量")
	private Integer count;
	/**
	 * 备注
	 */
	@ApiModelProperty(name = "remark",value = "备注")
	private String remark;
	/**
	 * 来源
	 */
	@ApiModelProperty(name = "sourceType",value = "来源")
	private Integer sourceType;

}

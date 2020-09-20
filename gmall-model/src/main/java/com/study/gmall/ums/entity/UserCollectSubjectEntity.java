package com.study.gmall.ums.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 关注活动表
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-20 14:34:42
 */
@ApiModel
@Data
@TableName("ums_user_collect_subject")
public class UserCollectSubjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "id")
	private Long id;
	/**
	 * 用户id
	 */
	@ApiModelProperty(name = "userId",value = "用户id")
	private Integer userId;
	/**
	 * 活动id
	 */
	@ApiModelProperty(name = "subjectId",value = "活动id")
	private Long subjectId;
	/**
	 * 活动名称
	 */
	@ApiModelProperty(name = "subjectName",value = "活动名称")
	private String subjectName;
	/**
	 * 活动默认图片
	 */
	@ApiModelProperty(name = "subjectImage",value = "活动默认图片")
	private String subjectImage;
	/**
	 * 活动链接
	 */
	@ApiModelProperty(name = "subjectUrl",value = "活动链接")
	private String subjectUrl;
	/**
	 * 关注时间
	 */
	@ApiModelProperty(name = "createTime",value = "关注时间")
	private Date createTime;

}

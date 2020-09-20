package com.study.gmall.pms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品评价
 * 
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-09-18 00:31:59
 */
@ApiModel
@Data
@TableName("pms_comment")
public class CommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "id")
	private Long id;
	/**
	 * sku_id
	 */
	@ApiModelProperty(name = "skuId",value = "sku_id")
	private Long skuId;
	/**
	 * spu_id
	 */
	@ApiModelProperty(name = "spuId",value = "spu_id")
	private Long spuId;
	/**
	 * 商品名字
	 */
	@ApiModelProperty(name = "spuName",value = "商品名字")
	private String spuName;
	/**
	 * 会员昵称
	 */
	@ApiModelProperty(name = "nickName",value = "会员昵称")
	private String nickName;
	/**
	 * 星级
	 */
	@ApiModelProperty(name = "star",value = "星级")
	private Integer star;
	/**
	 * 会员ip
	 */
	@ApiModelProperty(name = "ip",value = "会员ip")
	private String ip;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(name = "createTime",value = "创建时间")
	private Date createTime;
	/**
	 * 显示状态[0-不显示，1-显示]
	 */
	@ApiModelProperty(name = "status",value = "显示状态[0-不显示，1-显示]")
	private Integer status;
	/**
	 * 购买时属性组合
	 */
	@ApiModelProperty(name = "spuAttributes",value = "购买时属性组合")
	private String spuAttributes;
	/**
	 * 点赞数
	 */
	@ApiModelProperty(name = "followCount",value = "点赞数")
	private Integer followCount;
	/**
	 * 回复数
	 */
	@ApiModelProperty(name = "replyCount",value = "回复数")
	private Integer replyCount;
	/**
	 * 评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]
	 */
	@ApiModelProperty(name = "resources",value = "评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]")
	private String resources;
	/**
	 * 内容
	 */
	@ApiModelProperty(name = "content",value = "内容")
	private String content;
	/**
	 * 用户头像
	 */
	@ApiModelProperty(name = "icon",value = "用户头像")
	private String icon;
	/**
	 * 评论类型[0 - 对商品的直接评论，1 - 对评论的回复]
	 */
	@ApiModelProperty(name = "type",value = "评论类型[0 - 对商品的直接评论，1 - 对评论的回复]")
	private Integer type;

}

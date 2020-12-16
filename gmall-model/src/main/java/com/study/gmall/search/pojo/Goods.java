package com.study.gmall.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;


@Data
@Document(indexName = "good", type = "info", shards = 3, replicas = 2)
public class Goods {

    @Id
    private Long skuId;
    @Field(type = FieldType.Keyword, index = false)
    private String pic;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(type = FieldType.Long)
    private Long sale; //销量
    @Field(type = FieldType.Long)
    private Long store;//是否有货
    @Field(type = FieldType.Date)
    private Date createTime; //新品牌序
    @Field(type = FieldType.Long)
    private Long brandId; //品牌id
    @Field(type = FieldType.Keyword)
    private String brandName;
    @Field(type = FieldType.Long)
    private Long categoryId; //分类id
    @Field(type = FieldType.Keyword)
    private String categoryName;
    @Field(type = FieldType.Nested) //嵌套
    private List<SearchAttr> attrs;
}

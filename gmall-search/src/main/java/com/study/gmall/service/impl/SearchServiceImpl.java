package com.study.gmall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.gmall.search.pojo.Goods;
import com.study.gmall.search.pojo.SearchParamVO;
import com.study.gmall.search.pojo.SearchResponseAttrVO;
import com.study.gmall.search.pojo.SearchResponseVO;
import com.study.gmall.service.SearchPmsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchPmsService {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public SearchResponseVO search(SearchParamVO searchParamVO) throws IOException {

        //构建DSL语句
        SearchRequest searchRequest = this.buildQueryDsl(searchParamVO);
        SearchResponse searchResponse = this.restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("response = " + searchResponse);

        SearchResponseVO searchResponseVO = this.paseSearchResult(searchResponse);
        searchResponseVO.setPageSize(searchParamVO.getPageSize());
        searchResponseVO.setPageNum(searchParamVO.getPageNum());
        return searchResponseVO;
    }

    private SearchResponseVO paseSearchResult(SearchResponse searchResponse) throws JsonProcessingException {
        SearchResponseVO searchResponseVO = new SearchResponseVO();
        //获取总命中的总记录数
        SearchHits hits = searchResponse.getHits();
        searchResponseVO.setTotal(hits.getTotalHits());

        //解析分类的聚合结果集
        SearchResponseAttrVO category = new SearchResponseAttrVO();
        category.setName("分类");
        Map<String, Aggregation> aggregationMap = searchResponse.getAggregations().asMap();
        ParsedLongTerms categoryIdAgg = (ParsedLongTerms) aggregationMap.get("categoryIdAgg");
        List<String> categoryValues = categoryIdAgg.getBuckets().stream().map(bucket -> {
            HashMap<String, String> map = new HashMap<>();
            //获取品牌Id
            map.put("id", bucket.getKeyAsString());
            //通过子聚合获取品牌名称
            Map<String, Aggregation> stringAggregationMap = bucket.getAggregations().asMap();
            ParsedStringTerms categoryNameAgg = (ParsedStringTerms) stringAggregationMap.get("categoryNameAgg");
            String categoryName = categoryNameAgg.getBuckets().get(0).getKeyAsString();
            map.put("name", categoryName);
            try {
                return objectMapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        category.setValue(categoryValues);
        searchResponseVO.setCatelog(category);

        searchResponseVO.setAttrs(null); //规格参数
        //解析品牌的聚合结果集
        SearchResponseAttrVO brand = new SearchResponseAttrVO();
        brand.setName("品牌");
        ParsedLongTerms brandIdAgg = (ParsedLongTerms) aggregationMap.get("brandIdAgg");
        List<String> brandValues = brandIdAgg.getBuckets().stream().map(bucket -> {
            HashMap<String, String> map = new HashMap<>();
            //获取品牌Id
            map.put("id", bucket.getKeyAsString());
            //通过子聚合获取品牌名称
            Map<String, Aggregation> stringAggregationMap = bucket.getAggregations().asMap();
            ParsedStringTerms brandNameAgg = (ParsedStringTerms) stringAggregationMap.get("brandNameAgg");
            String brandName = brandNameAgg.getBuckets().get(0).getKeyAsString();
            map.put("name", brandName);
            try {
                return objectMapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
        brand.setValue(brandValues);
        searchResponseVO.setBrand(brand);

        //规格参数
        //获取嵌套聚合对象
        ParsedNested attrAgg = (ParsedNested) aggregationMap.get("attrAgg");
        //获取规格参数聚合对象
        ParsedLongTerms attrIdAgg = attrAgg.getAggregations().get("attrIdAgg");
        List<Terms.Bucket> buckets = (List<Terms.Bucket>)attrIdAgg.getBuckets();
        if (!CollectionUtils.isEmpty(buckets)) {
            List<SearchResponseAttrVO> searchResponseAttrVOS = attrIdAgg.getBuckets().stream().map(bucket -> {
                SearchResponseAttrVO searchResponseAttrVO = new SearchResponseAttrVO();
                //设置规格参数的id
                searchResponseAttrVO.setProductAttributeId((bucket).getKeyAsNumber().longValue());
                //设置规格参数的名成
                List<? extends Terms.Bucket> nameBuckets = ((ParsedStringTerms) bucket.getAggregations().get("attrNameAgg")).getBuckets();
                searchResponseAttrVO.setName(nameBuckets.get(0).getKeyAsString());
                //设置规格参数值列表
                List<? extends Terms.Bucket> valueBuckets = ((ParsedStringTerms) bucket.getAggregations().get("attrValueAgg")).getBuckets();
                List<String> values = valueBuckets.stream().map(Terms.Bucket::getKeyAsString).collect(Collectors.toList());
                searchResponseAttrVO.setValue(values);
                return searchResponseAttrVO;
            }).collect(Collectors.toList());
            searchResponseVO.setAttrs(searchResponseAttrVOS);
        }
        //解析商品信息
        SearchHit[] subHits = hits.getHits();
        ArrayList<Goods> goodList = new ArrayList<>();
        for (SearchHit subHit : subHits) {
            Goods goods = objectMapper.readValue(subHit.getSourceAsString(), new TypeReference<Goods>() {
            }); //反序列化为Goods
            goods.setTitle(subHit.getHighlightFields().get("title").getFragments()[0].toString());
            goodList.add(goods);
        }
        searchResponseVO.setProducts(goodList);
        return searchResponseVO;
    }

    private SearchRequest buildQueryDsl(SearchParamVO searchParamVO) {
        //查询条件构建器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //1构建查询条件和过滤条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //1.1构建查询条件
        String keyword = searchParamVO.getKeyword(); //查询关键字
        if (StringUtils.isEmpty(keyword)) {
            return null;
        }
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", keyword).operator(Operator.AND));
        //1.2构建过滤条件
        //1.2.1构建品牌过滤
        String[] brand = searchParamVO.getBrand();
        if (brand != null && brand.length != 0) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("brandId", brand));
        }
        //1.2.2构建分类过滤
        String[] catelog3 = searchParamVO.getCatelog3();
        if (catelog3 != null && catelog3.length != 0) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("categoryId", catelog3));
        }
        //1.2.3构建规格属性的嵌套过滤
        String[] props = searchParamVO.getProps();
        if (props != null && props.length != 0) {
            for (String prop : props) {
                //以冒号进行分割，分割后应该有两个参数，1-attrId，2-attrValue
                String[] split = StringUtils.split(prop, ":");
                //校验参数合不合法
                if (split == null || split.length != 2) {
                    continue;
                }
                //以-分割处理attrValues
                String[] attrValues = StringUtils.split(split[1], "-");
                //构建嵌套查询
                BoolQueryBuilder builder = QueryBuilders.boolQuery();
                //构建嵌套查询中的子查询
                BoolQueryBuilder subBuilder = QueryBuilders.boolQuery();
                //构建子查询中的过滤条件
                subBuilder.must(QueryBuilders.termQuery("attrs.attrId", split[0]));
                subBuilder.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues));
                builder.must(QueryBuilders.nestedQuery("attrs", subBuilder, ScoreMode.None));
                boolQueryBuilder.filter(builder);
            }
        }
        //1.2.4价格区间过滤
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("price");
        Integer priceFrom = searchParamVO.getPriceFrom();
        Integer priceTo = searchParamVO.getPriceTo();
        if (priceFrom != null) {
            rangeQueryBuilder.gte(priceFrom);
        }
        if (priceFrom != null) {
            rangeQueryBuilder.lte(priceTo);
        }
        boolQueryBuilder.filter(rangeQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);

        //2构建分页
        Integer pageNum = searchParamVO.getPageNum();
        Integer pageSize = searchParamVO.getPageSize();
        searchSourceBuilder.from((pageNum - 1) * pageSize); //从第多少条数据开始
        searchSourceBuilder.size(pageSize);     //每页多少条数据

        //3构建排序
        String order = searchParamVO.getOrder();
        if (!StringUtils.isEmpty(order)) {
            String[] split = StringUtils.split(order, ":");
            if (split != null && split.length == 2) {
                String field = null;
                switch (split[0]) {
                    case "1":
                        field = "sale";
                        break;    //销量排序
                    case "2":
                        field = "price";
                        break;   //价格排序
                }
                assert field != null;
                searchSourceBuilder.sort(field, StringUtils.equals("asc", split[1]) ? SortOrder.ASC : SortOrder.DESC);
            }
        }

        //4构建高亮
        searchSourceBuilder.highlighter(new HighlightBuilder().field("title").preTags("<em>").postTags("</em>"));

        //5构建聚合条件
        //5.1品牌聚合
        searchSourceBuilder.aggregation(AggregationBuilders.terms("brandIdAgg").field("brandId")
                .subAggregation(AggregationBuilders.terms("brandNameAgg").field("brandName")));
        //5.2分类聚合
        searchSourceBuilder.aggregation(AggregationBuilders.terms("categoryIdAgg").field("categoryId")
                .subAggregation(AggregationBuilders.terms("categoryNameAgg").field("categoryName")));
        //5.3搜索的规格属性聚合
        searchSourceBuilder.aggregation(AggregationBuilders.nested("attrAgg", "attrs")
                .subAggregation(AggregationBuilders.terms("attrIdAgg").field("attrs.attrId")
                        .subAggregation(AggregationBuilders.terms("attrNameAgg").field("attrs.attrName"))
                        .subAggregation(AggregationBuilders.terms("attrValueAgg").field("attrs.attrValue"))));

        System.out.println("searchSourceBuilder.toString() = " + searchSourceBuilder.toString());

        searchSourceBuilder.fetchSource(new String[]{"skuId","pic","title","price"},null);

        SearchRequest goods = new SearchRequest("goods");
        goods.types("info");
        goods.source(searchSourceBuilder);
        return goods;
    }
}

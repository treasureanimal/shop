package com.study.gmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.study.core.bean.Resp;
import com.study.gmall.feign.GmallPmsClientApi;
import com.study.gmall.pms.entity.CategoryEntity;
import com.study.gmall.pms.vo.CategoryVO;
import com.study.gmall.service.IndexService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class IndexServiceImpl implements IndexService {

    /**
     * jdk序列化：速度最快，内存占用比较高，可读性差
     * json和jackson序列化：速度慢，因为有括号等字符所以占用内存高
     * xml序列化：速度慢，占用内存高
     * String序列化：比jdk慢，比json快占用内存就是字符大小，比较合适
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;//默认设置好的String序列化工具，RedisTemplate默认是jdk序列化工具，需要手动去设置
    @Autowired
    private GmallPmsClientApi gmallPmsClientApi;

    private static final String KEY_PREFIX = "index:cates:"; //加前缀为了区分后期购物车也有相同的key，而且这个前缀会成为树状文件夹
    @Override
    public List<CategoryEntity> queryLV1CateGories() {
        Resp<List<CategoryEntity>> listResp = this.gmallPmsClientApi.queryCategory(1, null);
        return listResp.getData();
    }

    @Override
    public List<CategoryVO> querySubCategories(Long pid) {
        //1.判断缓存中有没有
        String cateJson = this.stringRedisTemplate.opsForValue().get(KEY_PREFIX + pid);//key必须唯一
        //2.如果有直接返回，如果没有数据库中查询放入缓存
        if (!StringUtils.isEmpty(cateJson)) {
            List<CategoryVO> categoryVOS = JSON.parseArray(cateJson, CategoryVO.class);
        }
        //查询数据库
        Resp<List<CategoryVO>> listResp = gmallPmsClientApi.querySubCategories(pid);
        List<CategoryVO> categoryVOS = listResp.getData();
        //3.查询完成后放入缓存
        stringRedisTemplate.opsForValue().set(KEY_PREFIX + pid,JSON.toJSONString(categoryVOS),7, TimeUnit.DAYS);
        return listResp.getData();
    }
}

package com.study.gmall.controller;

import com.study.core.bean.PageVo;
import com.study.core.bean.QueryCondition;
import com.study.core.bean.Resp;
import com.study.gmall.item.ItemGroupVO;
import com.study.gmall.pms.entity.AttrGroupEntity;
import com.study.gmall.pms.vo.AttrGroupVO;
import com.study.gmall.service.AttrGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * @author 张晓雄
 * @email 824839090@qq.com
 * @date 2020-11-11 20:43:49
 */
@Api(tags = " 管理")
@RestController
@RequestMapping("pms/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:attrgroup:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = attrGroupService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{attrGroupId}")
    @PreAuthorize("hasAuthority('pms:attrgroup:info')")
    public Resp<AttrGroupEntity> info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        return Resp.ok(attrGroup);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:attrgroup:save')")
    public Resp<Object> save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:attrgroup:update')")
    public Resp<Object> update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:attrgroup:delete')")
    public Resp<Object> delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return Resp.ok(null);
    }

    @ApiOperation("根据三级分类id分页查询")
    @GetMapping("{cid}")
    public Resp<PageVo> queryByCidPage(@PathVariable("cid")Long cid, QueryCondition condition){

        PageVo pageVo = this.attrGroupService.queryByCidPage(cid, condition);
        return Resp.ok(pageVo);
    }

    @ApiOperation("根据分组id查询分组及组下的规格参数")
    @GetMapping("withattr/{gid}")
    public Resp<AttrGroupVO> queryById(@PathVariable("gid")Long gid){

        AttrGroupVO attrGroupVO = this.attrGroupService.queryById(gid);
        return Resp.ok(attrGroupVO);
    }

    @ApiOperation("根据分组id查询分组及组下的规格参数")
    @GetMapping("withattrs/cat/{catId}")
    public Resp<List<AttrGroupVO>> queryGroupByCid(@PathVariable("catId")Long catId){
        List<AttrGroupVO> groupVOS = attrGroupService.queryGroupByCid(catId);
        return Resp.ok(groupVOS);
    }

    @GetMapping("item/group/{cid}/{spuId}")
    public Resp<List<ItemGroupVO>> queryItemGroupVOByCidAndSpuId(@PathVariable("cid")Long cid, @PathVariable("spuId")Long spuId){

        List<ItemGroupVO> itemGroupVOS = this.attrGroupService.queryItemGroupVOByCidAndSpuId(cid, spuId);
        return Resp.ok(itemGroupVOS);
    }

}

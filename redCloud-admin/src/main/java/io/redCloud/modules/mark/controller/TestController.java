package io.redCloud.modules.mark.controller;

import java.util.Arrays;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.github.xiaoymin.knife4j.annotations.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.redCloud.modules.mark.excel.TestBean;
import io.redCloud.modules.mark.entity.TestEntity;
import io.redCloud.modules.mark.service.TestService;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.ExcelUtils;
import io.redCloud.common.utils.R;


/**
 *
 *
 * @author jihongyun
 * @email 15721460@qq.com
 * @date 2020-02-26 17:25:01
 */
@Api(tags = "测试knife4")
@RestController
@ApiSort
@RequestMapping("mark/test")
public class TestController extends AbstractController{
    @Autowired
    private TestService testService;


    @ApiOperation(value = "列表")
    @ApiOperationSupport(
            params = @DynamicParameters(properties = {
                        @DynamicParameter(value = "多少条",name = "limit",dataTypeClass = Integer.class),
                        @DynamicParameter(value = "第几页",name = "page",dataTypeClass = Integer.class,example = "1")
                    },name = "params"),
            author = "冀鸿运"
    )
    @PostMapping("/list")
    @RequiresPermissions("mark:test:list")
    public LayuiPage<TestEntity> list(@RequestBody @ApiParam(value = "分页参数及过滤条件") Map<String, Object> params){
        LayuiPage<TestEntity> page = testService.queryPage(params);

        return page;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("mark:test:info")
    public R<TestEntity> info(@PathVariable("id") Long id){
			TestEntity test = testService.getById(id);

        return R.ok(test);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("mark:test:save")
    public R save(@RequestBody TestEntity test){
			testService.save(test);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("mark:test:update")
    public R update(@RequestBody TestEntity test){
			testService.updateById(test);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("mark:test:delete")
    public R delete(@RequestBody Long[] ids){
			testService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 导出
    */
    @RequestMapping("/export")
    @RequiresPermissions("mark:test:export")
    public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        LayuiPage page = testService.queryPage(params);

        ExcelUtils.exportExcelToTarget(response, "", page.getData(), TestBean.class);
    }

}

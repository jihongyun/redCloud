

package io.redCloud.modules.activiti.controller;

import io.redCloud.common.annotation.SysLog;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.R;
import io.redCloud.modules.activiti.form.ModelForm;
import io.redCloud.modules.activiti.service.ActModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 模型管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2018-07-15
 */
@RestController
@RequestMapping("/act/model")
public class ActModelController {
    @Autowired
    private ActModelService actModelService;

    /**
     * 列表
     */
    @RequestMapping("list")
    public LayuiPage list(@RequestParam Map<String, Object> params){
        LayuiPage page = actModelService.queryPage(params);

        return page;
    }

    /**
     * 新增
     */
    @SysLog("新增模型")
    @RequestMapping("/save")
    public R save(@RequestBody ModelForm form) throws Exception{
        actModelService.save(form.getName(), form.getKey(), form.getDescription());

        return R.ok();
    }

    /**
     * 部署
     */
    @RequestMapping(value = "deploy")
    public R deploy(String id) {
        actModelService.deploy(id);
        return R.ok();
    }

    /**
     * 导出
     */
    @RequestMapping(value = "export")
    public void export(String id, HttpServletResponse response) {
        actModelService.export(id, response);
    }

    /**
     * 删除
     */
    @RequestMapping("delete")
    public R delete(@RequestBody String[] ids) {
        for(String id : ids) {
            actModelService.delete(id);
        }
        return R.ok();
    }
}

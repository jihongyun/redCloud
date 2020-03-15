

package io.redCloud.modules.activiti.controller;

import io.redCloud.common.exception.RRException;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.R;
import io.redCloud.modules.activiti.service.ActProcessService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 流程管理
 */
@RestController
@RequestMapping("/act/process")
public class ActProcessController {
    @Autowired
    private ActProcessService actProcessService;

    /**
     * 流程列表
     */
    @RequestMapping("list")
    public LayuiPage processList(@RequestParam Map<String, Object> params){
        LayuiPage page = actProcessService.queryProcessPage(params);

        return page;
    }

    /**
     * 运行中的流程列表
     */
    @RequestMapping("running")
    public LayuiPage runningList(@RequestParam Map<String, Object> params){
        LayuiPage page = actProcessService.queryRunningPage(params);

        return page;
    }

    /**
     * 删除实例
     * @param instanceId 实例ID
     */
    @RequestMapping(value = "deleteInstance")
    public R deleteInstance(String instanceId) {
        actProcessService.deleteInstance(instanceId);
        return R.ok();
    }

    /**
     * 部署流程文件
     */
    @RequestMapping("deploy")
    public R deploy(@RequestParam("processFile") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RRException("请上传部署流程文件");
        }

        actProcessService.deploy(file);

        return R.ok();
    }

    /**
     * 激活流程
     * @param id 流程ID
     */
    @RequestMapping("active")
    public R active(String id) {
        actProcessService.active(id);

        return R.ok();
    }

    /**
     * 挂起流程
     * @param id 流程ID
     */
    @RequestMapping("suspend")
    public R suspend(String id) {
        actProcessService.suspend(id);

        return R.ok();
    }

    /**
     * 将部署的流程转换为模型
     * @param id 流程ID
     */
    @RequestMapping("convertToModel")
    public R convertToModel(String id) throws Exception {
        actProcessService.convertToModel(id);

        return R.ok();
    }

    /**
     * 删除流程
     * @return
     */
    @RequestMapping("delete")
    public R delete(@RequestBody String[] deploymentIds) {
        for(String deploymentId : deploymentIds) {
            actProcessService.deleteDeployment(deploymentId);
        }
        return R.ok();
    }

    /**
     * 获取资源文件
     * @param deploymentId  部署ID
     * @param resourceName 资源名称
     */
    @GetMapping(value = "resource")
    public void resource(String deploymentId, String resourceName, HttpServletResponse response) throws Exception {
        InputStream resourceAsStream = actProcessService.getResourceAsStream(deploymentId, resourceName);

        IOUtils.copy(resourceAsStream, response.getOutputStream());
    }

}

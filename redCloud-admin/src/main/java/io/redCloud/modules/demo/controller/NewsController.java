

package io.redCloud.modules.demo.controller;

import io.redCloud.common.utils.DateUtils;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.R;
import io.redCloud.common.validator.Assert;
import io.redCloud.common.xss.XssHttpServletRequestWrapper;
import io.redCloud.modules.demo.entity.NewsEntity;
import io.redCloud.modules.demo.service.NewsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 新闻
 */
@RestController
@RequestMapping("demo/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("demo:news:list")
    public LayuiPage list(@RequestParam Map<String, Object> params){
        LayuiPage page = newsService.queryPage(params);

        return page;
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("demo:news:info")
    public R<NewsEntity> info(@PathVariable("id") Long id){
        NewsEntity news = newsService.getById(id);

        return R.ok(news);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("demo:news:save")
    public R save(HttpServletRequest request){
        HttpServletRequest orgRequest = XssHttpServletRequestWrapper.getOrgRequest(request);
        String title = orgRequest.getParameter("title");
        String content = orgRequest.getParameter("content");
        String pubTime = orgRequest.getParameter("pubTime");

        Assert.isBlank(title, "标题不能为空");
        Assert.isBlank(content, "内容不能为空");
        Assert.isBlank(pubTime, "发布时间不能为空");

        NewsEntity entity = new NewsEntity();
        entity.setTitle(title);
        entity.setContent(content);
        entity.setPubTime(DateUtils.parse(pubTime, DateUtils.DATE_TIME_PATTERN));
        entity.setCreateDate(new Date());

        newsService.save(entity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("demo:news:update")
    public R update(HttpServletRequest request){
        HttpServletRequest orgRequest = XssHttpServletRequestWrapper.getOrgRequest(request);
        String id = orgRequest.getParameter("id");
        String title = orgRequest.getParameter("title");
        String content = orgRequest.getParameter("content");
        String pubTime = orgRequest.getParameter("pubTime");

        Assert.isBlank(id, "ID不能为空");
        Assert.isBlank(title, "标题不能为空");
        Assert.isBlank(content, "内容不能为空");
        Assert.isBlank(pubTime, "发布时间不能为空");

        NewsEntity entity = new NewsEntity();
        entity.setId(Long.parseLong(id));
        entity.setTitle(title);
        entity.setContent(content);
        entity.setPubTime(DateUtils.parse(pubTime, DateUtils.DATE_TIME_PATTERN));

        newsService.updateById(entity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("demo:news:delete")
    public R delete(@RequestBody Long[] ids){
        newsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}



package io.redCloud.modules.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.Query;
import io.redCloud.modules.demo.dao.NewsDao;
import io.redCloud.modules.demo.entity.NewsEntity;
import io.redCloud.modules.demo.service.NewsService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("newsService")
public class NewsServiceImpl extends ServiceImpl<NewsDao, NewsEntity> implements NewsService {

    @Override
    public LayuiPage queryPage(Map<String, Object> params) {
        String title = (String)params.get("title");
        String startDate = (String)params.get("startDate");
        String endDate = (String)params.get("endDate");

        Page<NewsEntity> page = this.page(
                new Query<NewsEntity>(params).getPage(),
                new QueryWrapper<NewsEntity>()
                    .like(StrUtil.isNotBlank(title),"title", title)
                    .ge(StrUtil.isNotBlank(startDate),"pub_time", startDate)
                    .le(StrUtil.isNotBlank(endDate),"pub_time", endDate)
        );

        return new LayuiPage(page.getRecords(), page.getTotal());
    }

}

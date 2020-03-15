
package io.redCloud.modules.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.modules.demo.entity.NewsEntity;

import java.util.Map;

/**
 * 新闻
 */
public interface NewsService extends IService<NewsEntity> {

    LayuiPage queryPage(Map<String, Object> params);
}


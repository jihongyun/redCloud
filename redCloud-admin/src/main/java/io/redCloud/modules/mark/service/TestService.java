package io.redCloud.modules.mark.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.modules.mark.entity.TestEntity;

import java.util.Map;

/**
 * 
 *
 * @author jihongyun
 * @email 15721460@qq.com
 * @date 2020-02-26 17:25:01
 */
public interface TestService extends IService<TestEntity> {

    LayuiPage queryPage(Map<String, Object> params);
}


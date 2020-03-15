package io.redCloud.modules.mark.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.Query;

import io.redCloud.modules.mark.dao.TestDao;
import io.redCloud.modules.mark.entity.TestEntity;
import io.redCloud.modules.mark.service.TestService;


@Service("testService")
public class TestServiceImpl extends ServiceImpl<TestDao, TestEntity> implements TestService {

    @Override
    public LayuiPage queryPage(Map<String, Object> params) {
        Page<TestEntity> page = this.page(
                new Query<TestEntity>(params).getPage(),
                new QueryWrapper<TestEntity>()
        );

        return new LayuiPage(page.getRecords(), page.getTotal());
    }

}

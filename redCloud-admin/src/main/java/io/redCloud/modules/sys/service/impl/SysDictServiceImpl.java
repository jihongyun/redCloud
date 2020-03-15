

package io.redCloud.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.Query;
import io.redCloud.modules.sys.dao.SysDictDao;
import io.redCloud.modules.sys.entity.SysDictEntity;
import io.redCloud.modules.sys.service.SysDictService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, SysDictEntity> implements SysDictService {

    @Override
    public LayuiPage queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        Page<SysDictEntity> page = this.page(
                new Query<SysDictEntity>(params).getPage(),
                new QueryWrapper<SysDictEntity>()
                    .like(StrUtil.isNotBlank(name),"name", name)
        );

        return new LayuiPage(page.getRecords(), page.getTotal());
    }

}

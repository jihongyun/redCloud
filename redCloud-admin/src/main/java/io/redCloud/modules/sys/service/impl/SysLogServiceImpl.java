

package io.redCloud.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.Query;
import io.redCloud.modules.sys.dao.SysLogDao;
import io.redCloud.modules.sys.entity.SysLogEntity;
import io.redCloud.modules.sys.service.SysLogService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public LayuiPage queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");

        Page<SysLogEntity> page = this.page(
            new Query<SysLogEntity>(params).getPage(),
            new QueryWrapper<SysLogEntity>().like(StrUtil.isNotBlank(key),"username", key)
        );

        return new LayuiPage(page.getRecords(), page.getTotal());
    }
}

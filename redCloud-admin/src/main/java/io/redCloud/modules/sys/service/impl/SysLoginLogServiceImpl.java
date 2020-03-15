package io.redCloud.modules.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.Map;

import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.Query;

import io.redCloud.modules.sys.dao.SysLoginLogDao;
import io.redCloud.modules.sys.entity.SysLoginLogEntity;
import io.redCloud.modules.sys.service.SysLoginLogService;


@Service("sysLoginLogService")
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogDao, SysLoginLogEntity> implements SysLoginLogService {

    @Override
    public LayuiPage queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        String status = (String)params.get("status");

        Page<SysLoginLogEntity> page = this.page(
                new Query<SysLoginLogEntity>(params).getPage(),
                new QueryWrapper<SysLoginLogEntity>()
                    .like(StrUtil.isNotBlank(username),"username", username)
                    .eq(StrUtil.isNotBlank(status),"status", status).orderByDesc("create_date")
        );

        return new LayuiPage(page.getRecords(), page.getTotal());
    }

}

package io.redCloud.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.modules.sys.entity.SysLoginLogEntity;

import java.util.Map;

/**
 * 登录日志
 */
public interface SysLoginLogService extends IService<SysLoginLogEntity> {

    LayuiPage queryPage(Map<String, Object> params);
}


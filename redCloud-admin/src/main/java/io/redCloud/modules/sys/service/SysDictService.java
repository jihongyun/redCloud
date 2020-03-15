

package io.redCloud.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.modules.sys.entity.SysDictEntity;

import java.util.Map;

/**
 * 数据字典
 */
public interface SysDictService extends IService<SysDictEntity> {

    LayuiPage queryPage(Map<String, Object> params);
}


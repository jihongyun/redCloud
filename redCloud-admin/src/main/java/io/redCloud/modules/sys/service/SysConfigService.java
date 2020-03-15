
package io.redCloud.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.modules.sys.entity.SysConfigEntity;

import java.util.Map;

/**
 * 系统配置信息
 */
public interface SysConfigService extends IService<SysConfigEntity> {

	LayuiPage queryPage(Map<String, Object> params);



	/**
	 * 更新配置信息
	 */
    void update(SysConfigEntity config);

	/**
	 * 根据key，更新value
	 */
    void updateValueByKey(String key, String value);

	/**
	 * 删除配置信息
	 */
    void deleteBatch(Long[] ids);

	/**
	 * 根据key，获取配置的value值
	 *
	 * @param key           key
	 */
    String getValue(String key);

	/**
	 * 根据key，获取value的Object对象
	 * @param key    key
	 * @param clazz  Object对象
	 */
    <T> T getConfigObject(String key, Class<T> clazz);

	boolean insert(SysConfigEntity config);
}

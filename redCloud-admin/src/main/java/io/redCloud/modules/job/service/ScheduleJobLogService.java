

package io.redCloud.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.modules.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 *
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	LayuiPage queryPage(Map<String, Object> params);

}

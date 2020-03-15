

package io.redCloud.modules.job.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.Query;
import io.redCloud.modules.job.dao.ScheduleJobLogDao;
import io.redCloud.modules.job.entity.ScheduleJobLogEntity;
import io.redCloud.modules.job.service.ScheduleJobLogService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {

	@Override
	public LayuiPage queryPage(Map<String, Object> params) {
		String jobId = (String)params.get("jobId");

		Page<ScheduleJobLogEntity> page = this.page(
				new Query<ScheduleJobLogEntity>(params).getPage(),
				new QueryWrapper<ScheduleJobLogEntity>().like(StrUtil.isNotBlank(jobId),"job_id", jobId)
		);

		return new LayuiPage(page.getRecords(), page.getTotal());
	}

}


package io.redCloud.modules.oss.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.common.utils.Query;
import io.redCloud.modules.oss.dao.SysOssDao;
import io.redCloud.modules.oss.entity.SysOssEntity;
import io.redCloud.modules.oss.service.SysOssService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

	@Override
	public LayuiPage queryPage(Map<String, Object> params) {
		Page<SysOssEntity> page = this.page(
				new Query<SysOssEntity>(params).getPage()
		);

		return new LayuiPage(page.getRecords(), page.getTotal());
	}

}

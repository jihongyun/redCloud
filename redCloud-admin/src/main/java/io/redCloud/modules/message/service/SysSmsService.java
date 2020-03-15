package io.redCloud.modules.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.redCloud.common.utils.LayuiPage;
import io.redCloud.modules.message.entity.SysSmsEntity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 短信
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-07-22 16:01:07
 */
public interface SysSmsService extends IService<SysSmsEntity> {

    LayuiPage queryPage(Map<String, Object> params);

    /**
     * 发送短信
     * @param mobile   手机号
     * @param params   短信参数
     */
    void send(String mobile, String params);

    /**
     * 保存短信发送记录
     * @param platform   平台
     * @param mobile   手机号
     * @param params   短信参数
     * @param status   发送状态
     */
    void insert(Integer platform, String mobile, LinkedHashMap<String, String> params, Integer status);
}


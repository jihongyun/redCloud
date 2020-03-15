

package io.redCloud.common.utils;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Layui分页
 */
@Data
@ApiModel(value = "分页数据容器")
public class LayuiPage<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "响应码")
    private int code = 0;

    @ApiModelProperty(value = "总记录数")
    private long count;

    @ApiModelProperty(value = "列表数据")
    private List<T> data;

    public LayuiPage(List<T> data, long count) {
        this.data = data;
        this.count = count;
    }
}

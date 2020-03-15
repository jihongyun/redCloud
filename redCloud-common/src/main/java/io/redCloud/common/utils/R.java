

package io.redCloud.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 */
@Data
@ApiModel(value = "返回数据")
public class R<T>{

	@ApiModelProperty(value = "是否成功")
	private boolean success = true;
	@ApiModelProperty(value = "返回对象")
	private T data;
	@ApiModelProperty(value = "错误编号")
	private Integer code;
	@ApiModelProperty(value = "错误信息")
	private String msg;


	public R(boolean success, T data, Integer errCode, String message) {
		this.success = success;
		this.data = data;
		this.code = errCode;
		this.msg = message;
	}

	public R(boolean success, Integer errCode, String message) {
		this.success = success;
		this.code = errCode;
		this.msg = message;
	}

	public static R ok() {
		return new R(true, 0, "成功");
	}

	public static R ok(Object data) {
		return new R(true, data,0, "成功");
	}

	public static R error(Integer errCode,String message) {
		return new R(false, errCode, message);
	}

}

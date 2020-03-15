
package io.redCloud.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 导航菜单

 */
@Data
public class NavMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long pid;
	private String title;
	private String icon;
	private String url;
	/**
	 * 是否展开  true：展开
	 */
	private boolean spread;
	private List<?> children;

	/**
	 * 类型     0：目录   1：菜单   2：按钮
	 */
	@JsonIgnore
	private Integer type;
}

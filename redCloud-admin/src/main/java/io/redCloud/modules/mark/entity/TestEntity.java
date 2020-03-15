package io.redCloud.modules.mark.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.redCloud.common.validator.group.AddGroup;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author jihongyun
 * @email 15721460@qq.com
 * @date 2020-02-26 17:25:01
 */
@Data
@Accessors(chain = true)
@TableName("test" )
public class TestEntity extends Model<TestEntity>  {

    @ApiModelProperty(value = "" )
    @TableId(type = IdType.AUTO)
    private Long id;


    @ApiModelProperty(value = "名字" )
    @NotBlank(groups = AddGroup.class, message = "不能为空！" )
    private String name;


    @ApiModelProperty(value = "年龄" )
    @NotNull(groups = AddGroup.class, message = "不能为空！" )
    private Integer age;


    @ApiModelProperty(value = "昵称" )
    private String miniName;


    @ApiModelProperty(value = "创建时间" )
    private Date createTime;


    @ApiModelProperty(value = "更新时间" )
    private Date updateTime;

	@Override
	protected Serializable pkVal() {
		return id;
	}

}

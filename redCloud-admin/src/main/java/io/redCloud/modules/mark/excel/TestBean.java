package io.redCloud.modules.mark.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

/**
 * 
 *
 * @author jihongyun
 * @email 15721460@qq.com
 * @date 2020-02-26 17:25:01
 */
public class TestBean {
    @Excel(name = "")
    private Long id;
    @Excel(name = "")
    private String name;
    @Excel(name = "")
    private Integer age;
    @Excel(name = "")
    private String miniName;
    @Excel(name = "")
    private Date createTime;
    @Excel(name = "")
    private Date updateTime;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Integer getAge() {
        return age;
    }
    public void setMiniName(String miniName) {
        this.miniName = miniName;
    }
    public String getMiniName() {
        return miniName;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
}

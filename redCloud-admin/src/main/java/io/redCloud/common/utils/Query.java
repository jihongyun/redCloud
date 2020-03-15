
package io.redCloud.common.utils;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 */
@Data
public class Query<T> extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
    /**
     * mybatis-plus分页参数
     */
    private Page<T> page;
    /**
     * 当前页码
     */
    private long currPage = 1;
    /**
     * 开始位置
     */
    private long offset;
    /**
     * 每页条数
     */
    private long limit = 10;

    public Query(Map<String, Object> params){
        this.putAll(params);

        //分页参数
        if(params.get("page") != null){
            try {
                currPage = (long) params.get("page");
            } catch (Exception e) {
                currPage = Long.parseLong(params.get("page").toString());
            }
        }
        if(params.get("limit") != null){
            try {
                limit = (long) params.get("limit");
            } catch (Exception e) {
                limit = Long.parseLong(params.get("limit").toString());
            }
        }

        offset = (currPage - 1) * limit;
        this.put("offset", offset);
        this.put("page", currPage);
        this.put("limit", limit);

        //mybatis-plus分页
        this.page = new Page<>(currPage, limit);

    }

    public Page<T> getPage() {
        return page;
    }



}

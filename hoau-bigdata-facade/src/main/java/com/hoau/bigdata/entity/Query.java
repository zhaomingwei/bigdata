/**
 * renrenbx
 */
package com.hoau.bigdata.entity;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Joel.Li
 * @explain 说明：通用查询参数类
 * @date 2019年5月16日
 */
public class Query implements Serializable {
    private static final long serialVersionUID = -7057038237436920832L;

    /**
     * 数据偏移 (数据库中limit使用)
     */
    private Integer offset = 0;

    /**
     * 每页记录数
     */
    private Integer limit = 20;

    /**
     * 当前页
     */
    private Integer page = 1;

    /**
     * 总页数
     */
    private Integer totalPages = 1;

    /**
     * 总数
     */
    private Integer total = 0;

    /**
     * 查询结果返回
     */
    private Object response;

    /**
     * @return the offset
     */
    public Integer getOffset() {
        offset = (getPage() - 1) * limit;
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(Integer offset) {
        if (null == offset || offset < 0) {
            offset = 0;
        } else {
            this.offset = offset;
        }
    }

    /**
     * @return the limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(Integer limit) {
        if (null == limit || limit <= 0) {
            limit = 20;
        } else {
            this.limit = limit;
        }
    }

    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Integer page) {
        if (null == page || page <= 0) {
            page = 1;
        } else {
            this.page = page;
        }
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    /**
     * 返回查询参数
     *
     * @return
     */
    public Map<Object, Object> transformerMap() {
        // Map<Object, Object> map = BeanMapper.map(this, Map.class);
        // return map;
        return transformerMap1();
    }

    public Map<Object, Object> transformerMap1() {
        return copyMap(this);
    }

    private Map<Object, Object> copyMap(Object object) {

        Map<Object, Object> map = new HashMap<>();

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());

            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String propertyName = propertyDescriptor.getName();
                if (propertyName.toLowerCase().equals("class")) {
                    continue;
                }

                Method readMethod = propertyDescriptor.getReadMethod();

                map.put(propertyName, readMethod.invoke(object, new Object[0]));
            }

        } catch (Exception e) {
        }

        return map;
    }

}

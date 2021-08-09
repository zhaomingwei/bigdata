package com.hoau.bigdata.mapper.tm;

import com.hoau.bigdata.entity.tm.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020/11/3
 * @Time: 16:04
 */
@Mapper
public interface LabelDao {

    /**
     * 功能描述: <br>
     * 〈查询标签扫描信息明〉
     *
     * @Param: scanType 扫描类型：1，发车；2，到车
     * @Param: sec 秒
     * @Return: com.hoau.bigdata.entity.tm.Label
     */
    List<Label> queryLabelList(@Param("scanType") int scanType, @Param("sec") int sec);

}

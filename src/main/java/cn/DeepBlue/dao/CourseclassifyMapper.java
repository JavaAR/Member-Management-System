package cn.DeepBlue.dao;

import cn.DeepBlue.pojo.Courseclassify;
import cn.DeepBlue.utils.MyMapper;

import java.util.List;

public interface CourseclassifyMapper extends MyMapper<Courseclassify> {
    /**
     * 获取所有课程分类
     * @return
     */
    List<Courseclassify> getAllClassify();
}
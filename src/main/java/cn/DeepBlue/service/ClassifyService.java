package cn.DeepBlue.service;

import cn.DeepBlue.pojo.Courseclassify;

import java.util.List;

public interface ClassifyService {
    /**
     * 获取所有课程分类
      * @return
     */
    List<Courseclassify> getAllClassify();
}

package cn.DeepBlue.service.impl;

import cn.DeepBlue.dao.CourseclassifyMapper;
import cn.DeepBlue.pojo.Courseclassify;
import cn.DeepBlue.service.ClassifyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ClassifyServiceImpl implements ClassifyService {

    @Resource
    private CourseclassifyMapper courseclassifyMapper;


    @Override
    public List<Courseclassify> getAllClassify() {
        return courseclassifyMapper.getAllClassify();
    }
}

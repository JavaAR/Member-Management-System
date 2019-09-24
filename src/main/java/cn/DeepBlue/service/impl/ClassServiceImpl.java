package cn.DeepBlue.service.impl;

import cn.DeepBlue.dao.CourseMapper;
import cn.DeepBlue.pojo.Address;
import cn.DeepBlue.pojo.Course;
import cn.DeepBlue.pojo.User;
import cn.DeepBlue.service.ClassService;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
public class ClassServiceImpl implements ClassService {

    @Resource
    private CourseMapper courseMapper;


    @Override
    public List<Course> getAllClassInfo(HashMap<Object, Object> paramMap) throws Exception {
        return courseMapper.getAllClassInfo(paramMap);
    }

    @Override
    public List<User> getAllCourseInfo()  throws Exception{
        return courseMapper.getAllCourseInfo();
    }

    @Override
    public List<Address> getAllAddressInfo() throws Exception {
        return courseMapper.getAllAddressInfo();
    }

    @Override
    public Course getClassInfoById(Integer cid) throws Exception {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("cid",cid);
        List<Course> courseList = courseMapper.getAllClassInfo(paramMap);
        return courseList.get(0);
    }


    @Transactional
    @Override
    public Integer updateClassInfo(Course course, User currUser) throws Exception {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("cid",course.getCid());
        paramMap.put("coursename",course.getCoursename());
        paramMap.put("courseclassify",course.getCourseclassify());
        paramMap.put("addressid",course.getAddressid());
        paramMap.put("coursestarttime",course.getCoursestarttime());
        paramMap.put("courseendtime",course.getCourseendtime());
        paramMap.put("coursemoney",course.getCoursemoney());
        paramMap.put("updatedBy",currUser.getUid());
        paramMap.put("updatedTime",new Date());
        paramMap.put("coachid",course.getCoachid());
        return courseMapper.updateClassInfo(paramMap);
    }
    @Transactional
    @Override
    public Integer insertClassInfo(User currUser, Course course) throws Exception {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("cid",course.getCid());
        paramMap.put("coursename",course.getCoursename());
        paramMap.put("courseclassify",course.getCourseclassify());
        paramMap.put("addressid",course.getAddressid());
        paramMap.put("coursestarttime",course.getCoursestarttime());
        paramMap.put("courseendtime",course.getCourseendtime());
        paramMap.put("coursemoney",course.getCoursemoney());
        paramMap.put("createdBy",currUser.getUid());
        paramMap.put("createdTime",new Date());
        paramMap.put("coachid",course.getCoachid());
        return courseMapper.insertClassInfo(paramMap);
    }

    @Transactional
    @Override
    public boolean insertClass(Course course, JSONArray dateArry, User currUser, String startTime, String endTime) throws Exception {
        HashMap<Object, Object> paramMap = new HashMap<>();
        boolean flag = false;
        //拼接数据
        String coursestarttime;
        String courseendtime;
        int i = 0;
        for(;i<dateArry.size();i++){
            coursestarttime =  dateArry.get(i)+" "+startTime;
            courseendtime = dateArry.get(i)+" "+endTime;
            SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date startDate = sDateFormat.parse(coursestarttime);
            Date endDate = sDateFormat.parse(courseendtime);
            paramMap.put("coursename",course.getCoursename());
            paramMap.put("courseclassify",course.getCourseclassify());
            paramMap.put("addressid",course.getAddressid());
            paramMap.put("coursestarttime",startDate);
            paramMap.put("courseendtime",endDate);
            paramMap.put("coursemoney",course.getCoursemoney());
            paramMap.put("createdBy",currUser.getUid());
            paramMap.put("createdTime",new Date());
            paramMap.put("coachid",course.getCoachid());
            courseMapper.insertClassInfo(paramMap);
        }
        if(i==dateArry.size()){
            flag = true;
        }
        return flag;
    }

    @Override
    public List<Course> getAllClassInfoByParam(HashMap<Object, Object> paramMap) {
        return  courseMapper.getAllClassInfoByParam(paramMap);
    }

    @Override
    public Integer delClassInfo(String cid) {
        return courseMapper.delClassInfo(cid);
    }

    @Override
    public Integer delClassInfoByCaochId(Integer uid) {
        return courseMapper.delClassInfoByCoachId(uid);
    }

    @Override
    public List<Course> getCurrCoachAllClass(Integer uid, String startDate, String endDate) {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("coachid",uid);
        paramMap.put("startDate",startDate);
        paramMap.put("endDate",endDate);
        return courseMapper.getCurrCoachClass(paramMap);
    }
}

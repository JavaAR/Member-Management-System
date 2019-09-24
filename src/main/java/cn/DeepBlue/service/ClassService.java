package cn.DeepBlue.service;

import cn.DeepBlue.pojo.Address;
import cn.DeepBlue.pojo.Course;
import cn.DeepBlue.pojo.User;
import net.sf.json.JSONArray;

import java.util.HashMap;
import java.util.List;

public interface ClassService {
    /**
     * 查询所有课程
     * @param paramMap
     * @return
     */
    List<Course> getAllClassInfo(HashMap<Object, Object> paramMap) throws Exception;

    /**
     * 获取教练信息
     * @return
     */
    List<User> getAllCourseInfo() throws Exception;

    /**
     * 获取所有地址
     * @return
     */
    List<Address> getAllAddressInfo() throws Exception;

    /**
     * 查看课程信息
     * @param cid
     * @return
     */
    Course getClassInfoById(Integer cid) throws Exception;

    /**
     * 修改课程信息
     * @param course
     * @param currUser
     * @return
     */
    Integer updateClassInfo(Course course, User currUser) throws Exception;

    /**
     * 新增一条课程
     * @param currUser
     * @param course
     * @return
     * @throws Exception
     */
    Integer insertClassInfo(User currUser, Course course) throws Exception;

    /**
     * 安排课程
     * @param course
     * @param dateArry
     * @param currUser
     * @param startTime
     * @param endTime
     */
    boolean insertClass(Course course, JSONArray dateArry, User currUser, String startTime, String endTime) throws Exception;

    /**
     * 按条件查询课程
     * @param paramMap
     * @return
     */
    List<Course> getAllClassInfoByParam(HashMap<Object, Object> paramMap);

    /**
     * 删除
     * @param cid
     * @return
     */
    Integer delClassInfo(String cid);

    /**
     * 根据教练id删除课程
     * @param uid
     * @return
     */
    Integer delClassInfoByCaochId(Integer uid);

    /**
     * 获取当前教练的所有课程
     * @param uid
     * @param startDate
     * @param endDate
     * @return
     */
    List<Course> getCurrCoachAllClass(Integer uid, String startDate, String endDate);
}

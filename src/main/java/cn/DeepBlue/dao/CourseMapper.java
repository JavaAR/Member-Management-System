package cn.DeepBlue.dao;

import cn.DeepBlue.pojo.Address;
import cn.DeepBlue.pojo.Course;
import cn.DeepBlue.pojo.User;
import cn.DeepBlue.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface CourseMapper extends MyMapper<Course> {
    /**
     * 查询所有课程信息
     * @param paramMap
     * @return
     */
    List<Course> getAllClassInfo(HashMap<Object, Object> paramMap);
    /**
     * 获取所有教练信息
     * @return
     */
    List<User> getAllCourseInfo();
    /**
     * 获取所有地址信息
     * @return
     */
    List<Address> getAllAddressInfo();

    /**
     * 修改课程信息
     * @param paramMap
     * @return
     */
    Integer updateClassInfo(HashMap<Object, Object> paramMap);
    /**
     * 新增课程信息
     * @param paramMap
     * @return
     */
    Integer insertClassInfo(HashMap<Object, Object> paramMap);

    /**
     *
     * @param paramMap
     * @return
     */
    List<Course> getAllClassInfoByParam(HashMap<Object, Object> paramMap);

    /**
     * 删除
     * @param cid
     * @return
     */
    Integer delClassInfo(@Param("cid") String cid);

    /**
     * 根据教练id删除
     * @param uid
     * @return
     */
    Integer delClassInfoByCoachId(@Param("uid") Integer uid);

    /**
     * 教练客户端获取所有教练的课程
     * @param paramMap
     * @return
     */
    List<Course> getCurrCoachClass(HashMap<Object, Object> paramMap);
}
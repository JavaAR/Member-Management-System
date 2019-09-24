package cn.DeepBlue.dao;

import cn.DeepBlue.pojo.User;
import cn.DeepBlue.pojo.Userlever;
import cn.DeepBlue.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface UserMapper extends MyMapper<User> {
    /**
     * 登录
     * @param paramMap
     * @return
     */
    User getUserByMap(HashMap<String, Object> paramMap);

    /**
     * 修改admin密码
      * @param paramMap
     * @return
     */
    Integer upDateUserByMap(HashMap<Object, Object> paramMap);

    /**
     * 获取所有用户信息
     * @param paramMap
     * @return
     */
    List<User> getAllUserInfoByMap(HashMap<String, Object> paramMap);

    /**
     * 添加一个用户
     * @param paramMap
     * @return
     */
    Integer insertUser(HashMap<Object, Object> paramMap);

    /**
     * 删除
     * @param paramMap
     * @return
     */
    Integer delUserById(HashMap<Object, Object> paramMap);

    /**
     * 获取单个用户
     * @param paramMap
     * @return
     */
    User getUserById(HashMap<String, Object> paramMap);

    /**
     * 获取所有用户级别
     * @return
     */
     List<Userlever> getAllUserLever();

    /**
     * 获取所有教练
     * @param paramMap
     * @return
     */
    List<User> getCoachByMap(HashMap<Object, Object> paramMap);

    /**
     * 删除
     * @param uid
     * @return
     */
    Integer delCoachInfo(@Param("uid") Integer uid);

    /**
     * 获取所有用户总数，（教练加用户）
     * @param paramMap
     * @return
     */
    Integer getCountByMap(HashMap<Object, Object> paramMap);
}
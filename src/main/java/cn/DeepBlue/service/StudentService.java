package cn.DeepBlue.service;

import cn.DeepBlue.pojo.User;
import cn.DeepBlue.pojo.Userlever;

import java.util.HashMap;
import java.util.List;

public interface StudentService {
    /**
     * 查询所有用户
     * @return
     */
     List<User> getAllUserInfo(HashMap<String,Object> paramMap) throws Exception;

    /**
     * 查询单个用户
     * @param paramMap
     * @return
     */
    User getUserInfoById(HashMap<String, Object> paramMap) throws Exception;

    /**
     * 修改用户信息
     * @param currUser
     * @param user
     * @return
     */
    Integer updateStudentInfo(User currUser, User user);

    /**
     * 验证手机号是否存在
     * @param userPhone
     * @return
     */
    boolean userPhoneIsExit(String userPhone) throws Exception;

    /**
     * 新增学员
     * @param user
     * @param currUser
     * @return
     */
    Integer insertUser(User user, User currUser);

    /**
     * 删除学员
     * @param uid
     * @return
     */
    Integer delUserById(Integer uid);

    /**
     * 获取所有用户级别
     * @return
     */
    List<Userlever> getAllUserLever();

}

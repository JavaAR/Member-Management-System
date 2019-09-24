package cn.DeepBlue.service;

import cn.DeepBlue.pojo.User;

import java.util.HashMap;

public interface UserLoginService {

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    User doUserLogin(String username, String password) throws Exception;

    /**
     * admin
     * 查询用户信息
     * @param uid
     * @return
     */
    User getUserInfoById(Integer uid) throws Exception;

    /**
     * 修改密码
     * @param newPassword
     * @param uid
     * @return
     */
    Integer upDateUserPassword(String newPassword, Integer uid) throws Exception;

    /**
     * 修改用户信息
     * @param user
     * @param currUser
     * @return
     */
    int upDateUserInfo(User user, User currUser) throws Exception;

    /**
     * 查询所有用户
     * @param paramMap
     * @return
     */
    Integer getCountByMap(HashMap<Object, Object> paramMap);
}

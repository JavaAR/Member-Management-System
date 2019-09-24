package cn.DeepBlue.service;

import cn.DeepBlue.pojo.User;

import java.util.HashMap;
import java.util.List;

public interface CoachService {
    /**
     * 获取所有教练信息
     * @param paramMap
     * @return
     * @throws Exception
     */
     List<User> getAllCoachInfo(HashMap<Object, Object> paramMap) throws Exception;

    /**
     * 获取单个教练信息
     * @param uid
     * @return
     */
    User getCoachInfoById(String uid);

    /**
     * 修改教练信息
     * @param user
     * @param currUser
     * @return
     */
    Integer updateUserInfo(User user, User currUser);

    /**
     * 添加教练
     * @param user
     * @param currUser
     * @return
     */
    Integer insertCoach(User user, User currUser);

    /**
     * 删除教练信息
      * @param uid
     * @return
     */
    Integer delCoachById(Integer uid);

}

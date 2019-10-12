package cn.DeepBlue.service;

import cn.DeepBlue.pojo.Subscribe;
import cn.DeepBlue.pojo.Subscribestatus;
import cn.DeepBlue.pojo.User;

import java.util.HashMap;
import java.util.List;

/**
 * 预约接口
 */
public interface SubscribeService {
    /**
     * 查询学员是否已经预约过
     * @param classId
     * @param userId
     * @return
     */
    boolean getSubscribe(Integer classId, Integer userId) throws Exception;

    /**
     * 预约操作
     * @param subscribe
     * @return
     */
    Integer subscribeClass(Subscribe subscribe, User currUser)throws Exception ;

    /**
     * 查看当前教练所有学员预约以及考勤
     * @param paramMap
     * @return
     */
    List<Subscribe> getSubscribeByUserforMap(HashMap<Object, Object> paramMap)throws Exception;

    /**
     *查看当前用户的所有考勤
     * @param paramMap
     * @return
     */
    List<Subscribe> getSubscribeByUser(HashMap<Object, Object> paramMap)throws Exception;

    /**
     * 教练打卡学员操作
     * @param sid
     * @param statusid
     * @param currCoach
     * @return
     */
    int updateSubscribe(Integer sid, String statusid, User currCoach)throws Exception;

    /**
     * 学员段取消预约操作
     * @param cid
     * @param uid
     * @return
     */
    int deleSubscribeInfo(Integer cid, Integer uid)throws Exception;

    /**
     * 查询当前教练的所有预约
     * @param coachId
     * @return
     */
    List<Subscribe> getCurrCoachSubscribeClass(Integer coachId)throws Exception;

    /**
     * 教练段迟到自动打卡
     * @param subscribeId
     * @return
     */
    int doclassOverPunchCard(Integer subscribeId)throws Exception;

    /**
     * 管理员端查询所有的预约考勤
     * @param paramMap
     * @return
     */
    List<Subscribe> getAllSubscribeInfo(HashMap<Object, Object> paramMap)throws Exception;

    /**
     * 获取所有预约状态
     * @return
     */
    List<Subscribestatus> getAllSubscribeidStatus() throws Exception;

    /**
     * 获取所有预约
     * @param paramMap
     * @return
     */
    Integer getCountByMap(HashMap<Object, Object> paramMap)throws Exception;

    /**
     * 查询预约信息是否为以打卡或未打卡
     * @param cid
     * @param uid
     * @return
     */
    Subscribe getCancelClass(Integer cid, Integer uid)throws Exception;

    /**
     * 获取所有课程与预约信息
     * @param paramMap
     * @return
     */
    List<Subscribe> getCurrClassSubscribeInfo(HashMap<Object, Object> paramMap);
}

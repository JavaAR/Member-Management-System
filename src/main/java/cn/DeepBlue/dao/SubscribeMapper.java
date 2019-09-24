package cn.DeepBlue.dao;

import cn.DeepBlue.pojo.Subscribe;
import cn.DeepBlue.utils.MyMapper;

import java.util.HashMap;
import java.util.List;

public interface SubscribeMapper extends MyMapper<Subscribe> {
    /**
     * 查询
     * @param paramMap
     * @return
     */
    Subscribe getSbuscribeByMap(HashMap<Object, Object> paramMap);

    /**
     * 新增
     * @param paramMap
     * @return
     */
    Integer insertSubscribe(HashMap<Object, Object> paramMap);

    /**
     * 学员端查询当前用户的所有预约与考勤
     * @param paramMap
     * @return
     */
    List<Subscribe> getSubscribeForUser(HashMap<Object, Object> paramMap);

    /**
     * 教练段查询当前课程所有预约
     * @param paramMap
     * @return
     */
    List<Subscribe> getSubscribeForUserforMap(HashMap<Object, Object> paramMap);

    /**
     * 修改预约表信息
     * @param paramMap
     * @return
     */
    int updateSubscribeInfo(HashMap<Object, Object> paramMap);

    /**
     * 删除
     * @param paramMap
     * @return
     */
    int deleSubscribeInfo(HashMap<Object, Object> paramMap);

    /**
     * 管理员端获取所有订单信息
     * @param paramMap
     * @return
     */
    List<Subscribe> getAllSubscribe(HashMap<Object, Object> paramMap);

    /**
     * 获取预约总数
     * @param paramMap
     * @return
     */
    Integer getCountByMap(HashMap<Object, Object> paramMap);
}
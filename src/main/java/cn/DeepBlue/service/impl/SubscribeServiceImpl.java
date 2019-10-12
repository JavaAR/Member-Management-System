package cn.DeepBlue.service.impl;

import cn.DeepBlue.dao.SubscribeMapper;
import cn.DeepBlue.dao.SubscribestatusMapper;
import cn.DeepBlue.pojo.Subscribe;
import cn.DeepBlue.pojo.Subscribestatus;
import cn.DeepBlue.pojo.User;
import cn.DeepBlue.service.SubscribeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
public class SubscribeServiceImpl implements SubscribeService {

    @Resource
    private SubscribeMapper subscribeMapper;

    @Resource
    private SubscribestatusMapper subscribestatusMapper;

    @Override
    public boolean getSubscribe(Integer classId, Integer userId) throws Exception {
        boolean flag = false;
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("courseid",classId);
        paramMap.put("userid",userId);
        Subscribe subscribe = subscribeMapper.getSbuscribeByMap(paramMap);
        if(subscribe==null){
            flag = true;
        }
        return flag;
    }

    /**
     * 预约
     * @param subscribe
     * @param currUser
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public Integer subscribeClass(Subscribe subscribe, User currUser) throws Exception {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("courseid",subscribe.getCourseid());
        paramMap.put("userid",subscribe.getUserid());
        paramMap.put("ucoachid",subscribe.getUcoachid());
        paramMap.put("subscribeiddate",new Date());
        paramMap.put("createdBy",currUser.getUid());
        paramMap.put("createdTime", new Date());
        paramMap.put("status","1");
        return subscribeMapper.insertSubscribe(paramMap);
    }

    @Override
    public List<Subscribe> getSubscribeByUserforMap(HashMap<Object, Object> paramMap) {
        return subscribeMapper.getSubscribeForUserforMap(paramMap) ;
    }

    @Override
    public List<Subscribe> getSubscribeByUser(HashMap<Object, Object> paramMap) {
        return subscribeMapper.getSubscribeForUser(paramMap);
    }

    @Transactional
    @Override
    public int updateSubscribe(Integer sid, String statusid, User currCoach) {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("subscribeid",sid);
        paramMap.put("status",statusid);
        paramMap.put("updatedBy",currCoach.getUid());
        paramMap.put("updatedTime",new Date());
        return subscribeMapper.updateSubscribeInfo(paramMap);
    }

    @Override
    public int deleSubscribeInfo(Integer cid, Integer uid) {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("userid",uid);
        paramMap.put("courseid",cid);
        return subscribeMapper.deleSubscribeInfo(paramMap);
    }

    @Override
    public List<Subscribe> getCurrCoachSubscribeClass(Integer coachId) {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("ucoachid",coachId);
        return subscribeMapper.getSubscribeForUserforMap(paramMap);
    }

    @Override
    public int doclassOverPunchCard(Integer subscribeId) {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("subscribeid",subscribeId);
        paramMap.put("status","3");
        paramMap.put("updatedTime",new Date());
        return subscribeMapper.updateSubscribeInfo(paramMap);
    }

    @Override
    public List<Subscribe> getAllSubscribeInfo(HashMap<Object, Object> paramMap) {
        return subscribeMapper.getAllSubscribe(paramMap);
    }

    @Override
    public List<Subscribestatus> getAllSubscribeidStatus() {
        return subscribestatusMapper.subscribestatusMapper();
    }

    @Override
    public Integer getCountByMap(HashMap<Object, Object> paramMap) {
        return subscribeMapper.getCountByMap(paramMap);
    }

    @Override
    public Subscribe getCancelClass(Integer cid, Integer uid) throws Exception {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("courseid",cid);
        paramMap.put("userid",uid);
        return subscribeMapper.getSbuscribeByMap(paramMap);
    }

    @Override
    public List<Subscribe> getCurrClassSubscribeInfo(HashMap<Object, Object> paramMap) {
        return subscribeMapper.getAllSubscribe(paramMap);
    }
}

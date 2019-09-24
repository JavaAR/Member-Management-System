package cn.DeepBlue.service.impl;

import cn.DeepBlue.dao.UserMapper;
import cn.DeepBlue.pojo.User;
import cn.DeepBlue.service.CoachService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class CoachServiceImpl implements CoachService {

    @Resource
    private UserMapper userMapper;


    @Override
    public List<User> getAllCoachInfo(HashMap<Object, Object> paramMap) throws Exception {
        return userMapper.getCoachByMap(paramMap);
    }

    @Override
    public User getCoachInfoById(String uid) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid",uid);
        return userMapper.getUserByMap(paramMap);
    }

    @Override
    public Integer updateUserInfo(User user, User currUser) {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("uid",user.getUid());
        paramMap.put("username",user.getUsername());
        paramMap.put("usercode",user.getUsercode());
        paramMap.put("userphone",user.getUserphone());
        paramMap.put("forbidden",user.getForbidden());
        paramMap.put("updatedBy",currUser.getUid());
        paramMap.put("updatedTime",new Date());
        paramMap.put("age",user.getAge());
        paramMap.put("birthday",user.getBirthday());
        paramMap.put("userbackground",user.getUserbackground());
        paramMap.put("userlever",user.getUserlever());
        paramMap.put("gender", user.getGender());
        return userMapper.upDateUserByMap(paramMap);
    }

    @Override
    public Integer insertCoach(User user, User currUser) {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("username",user.getUsername());
        paramMap.put("usercode",user.getUsercode());
        paramMap.put("userrole",2);
        paramMap.put("userphone",user.getUserphone());
        paramMap.put("userpassword",user.getUserpassword());
        paramMap.put("forbidden",user.getForbidden());
        paramMap.put("createdBy",currUser.getUid());
        paramMap.put("createdTime",new Date());
        paramMap.put("age",user.getAge());
        paramMap.put("gender",user.getGender());
        paramMap.put("birthday",user.getBirthday());
        paramMap.put("userbackground",user.getUserbackground());
        paramMap.put("userlever",user.getUserlever());
        return userMapper.insertUser(paramMap);
    }

    @Override
    public Integer delCoachById(Integer uid) {
        return userMapper.delCoachInfo(uid);
    }
}

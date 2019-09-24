package cn.DeepBlue.service.impl;

import cn.DeepBlue.dao.UserMapper;
import cn.DeepBlue.pojo.User;
import cn.DeepBlue.service.UserLoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;


@Service
public class UserLoginServiceImpl  implements UserLoginService {

    @Resource
    private UserMapper userMapper;


    @Override
    public User doUserLogin(String username, String password) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userphone",username);
        User resUser =  userMapper.getUserByMap(paramMap);
        if(resUser!=null){
            if(resUser.getUserpassword().equals(password)){
                return resUser;
            }
        }
        return null;
    }

    @Override
    public User getUserInfoById(Integer uid) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid",uid);
        return userMapper.getUserByMap(paramMap);
    }

    @Override
    public Integer upDateUserPassword(String newPassword, Integer uid) {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("uid",uid);
        paramMap.put("userpassword",newPassword);
        paramMap.put("updatedBy",uid);
        paramMap.put("updatedTime",new Date());
        return userMapper.upDateUserByMap(paramMap);
    }

    @Override
    public int upDateUserInfo(User user, User currUser) throws Exception {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("uid",currUser.getUid());
        paramMap.put("usercode",user.getUsercode());
        paramMap.put("username",user.getUsername());
        paramMap.put("gender",user.getGender());
        paramMap.put("userphone",user.getUserphone());
        paramMap.put("birthday",user.getBirthday());
        paramMap.put("age",user.getAge());
        paramMap.put("userbackground",user.getUserbackground());
        paramMap.put("updatedBy",currUser.getUid());
        paramMap.put("updatedTime",new Date());
        return userMapper.upDateUserByMap(paramMap);
    }

    @Override
    public Integer getCountByMap(HashMap<Object, Object> paramMap) {
        return userMapper.getCountByMap(paramMap);
    }
}

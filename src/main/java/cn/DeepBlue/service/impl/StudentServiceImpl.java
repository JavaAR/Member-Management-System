package cn.DeepBlue.service.impl;

import cn.DeepBlue.dao.UserMapper;
import cn.DeepBlue.pojo.User;
import cn.DeepBlue.pojo.Userlever;
import cn.DeepBlue.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private UserMapper userMapper;


    @Override
    public List<User> getAllUserInfo(HashMap<String,Object> paramMap) throws Exception {
       List<User> userList = userMapper.getAllUserInfoByMap(paramMap);
        return userList;
    }

    @Override
    public User getUserInfoById(HashMap<String, Object> paramMap)throws Exception {
        return userMapper.getUserById(paramMap);
    }

    @Override
    public Integer updateStudentInfo(User currUser, User user) {
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
        System.out.println(user.getBirthday());
        paramMap.put("userbackground",user.getUserbackground());
        paramMap.put("gender", user.getGender());
        paramMap.put("userlever",user.getUserlever());
        return userMapper.upDateUserByMap(paramMap);
    }

    @Override
    public boolean userPhoneIsExit(String userPhone) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userphone",userPhone);
        User resUser = userMapper.getUserByMap(paramMap);
        boolean flag = false;
        if(resUser!=null){
            flag = true;
        }
        return flag;
    }

    @Override
    public Integer insertUser(User user, User currUser) {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("username",user.getUsername());
        paramMap.put("usercode",user.getUsercode());
        paramMap.put("userrole",1);
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
    public Integer delUserById(Integer uid) {
        HashMap<Object, Object> paramMap = new HashMap<>();
        paramMap.put("uid",uid);

        return userMapper.delUserById(paramMap);
    }

    @Override
    public List<Userlever> getAllUserLever() {
        return userMapper.getAllUserLever();
    }
}

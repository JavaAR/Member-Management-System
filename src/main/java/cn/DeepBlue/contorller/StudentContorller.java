package cn.DeepBlue.contorller;


import cn.DeepBlue.pojo.User;
import cn.DeepBlue.pojo.Userlever;
import cn.DeepBlue.pojo.dto.Dto;
import cn.DeepBlue.service.StudentService;
import cn.DeepBlue.utils.DtoUtil;
import cn.DeepBlue.utils.EmptyUtils;
import cn.DeepBlue.utils.ErrorCode;
import cn.DeepBlue.utils.RedisAPI;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * 学员控制器
 */
@RequestMapping(value = "api")
@Controller
public class StudentContorller {
    @Resource
    private StudentService studentService;

    @Resource
    private RedisAPI redisAPI;

    /**
     * 查询所有学员信息
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/allUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Dto doGetAllUserInfo(@RequestParam(required = false) String username) {
        try {
            HashMap<String, Object> paramMap = new HashMap<>();
            if (EmptyUtils.isNotEmpty(username)) {
                paramMap.put("username", username);
            }
            List<User> userList = studentService.getAllUserInfo(paramMap);
            if (userList.size() > 0) {
                return DtoUtil.returnDataSuccess(userList);
            }
            return DtoUtil.returnFail("暂无数据", ErrorCode.BIZ_QUERY_FILED);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", ErrorCode.BIZ_UNKNOWN);
        }
    }


    /**
     * 学员详细信息展示
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "getUserInfo/{uid}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto doGetUserInfoById(@PathVariable(value = "uid") Integer uid) {
        try {
            if (EmptyUtils.isNotEmpty(uid)) {
                HashMap<String, Object> paramMap = new HashMap<>();
                paramMap.put("uid", uid);
                User user = studentService.getUserInfoById(paramMap);
                if (user != null) {
                    return DtoUtil.returnDataSuccess(user);
                }
                return DtoUtil.returnFail("查询失败", ErrorCode.BIZ_QUERY_FILED);
            } else {
                return DtoUtil.returnFail("请刷新页面", ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 修改学员信息
     *
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateStudentInfo", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto doupdateStudentInfo(@RequestBody User user, HttpServletRequest request) {
        try {
            if (EmptyUtils.isNotEmpty(user) && EmptyUtils.isNotEmpty(user.getUid())) {
                User currUser = JSON.parseObject(redisAPI.get(request.getHeader("token")), User.class);
                Integer result = studentService.updateStudentInfo(currUser, user);
                if (result > 0) {
                    return DtoUtil.returnSuccess();
                }
                return DtoUtil.returnFail("修改失败,请重试", ErrorCode.BIZ_UPDATE_FILED);
            } else {
                return DtoUtil.returnFail("请刷新页面", ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 验证手机号是否存在
     * @param userPhone
     * @return
     */
    @RequestMapping(value = "/userPhoneIsExit/{userPhone}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto douserPhoneIsExit(@PathVariable String userPhone) {
        try {
            if (EmptyUtils.isNotEmpty(userPhone)) {
                boolean flag = studentService.userPhoneIsExit(userPhone);
                if (flag) {
                    return DtoUtil.returnFail("该手机号已存在", ErrorCode.BIZ_QUERY_FILED);
                }
                   return DtoUtil.returnSuccess("可以使用的手机号");
            } else {
                return DtoUtil.returnFail("必填项不能为空", ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 新增一条学员
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertStudent",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto doinsertStudent(@RequestBody User user,HttpServletRequest request){
        try {
            if(EmptyUtils.isNotEmpty(user)){
                User currUser = JSON.parseObject(redisAPI.get(request.getHeader("token")), User.class);
                Integer result = studentService.insertUser(user,currUser);
                if(result>0){
                    return DtoUtil.returnSuccess();
                }
                return DtoUtil.returnFail("添加学员失败",ErrorCode.BIZ_INSERT_FILED);
            }else{
                return DtoUtil.returnFail("必填项不能为空",ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 删除学员
     * @param uid
     * @param request
     * @return
     */
    @RequestMapping(value = "/delStudent/{uid}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto dodelStudent(@PathVariable("uid") Integer uid,HttpServletRequest request){
        try {
            if(EmptyUtils.isNotEmpty(uid)){
             Integer result = studentService.delUserById(uid);
             if(result>0){
                return DtoUtil.returnSuccess();
             }
             return DtoUtil.returnFail("删除失败，请重试",ErrorCode.BIZ_DELETE_FILED);
            }else{
                return DtoUtil.returnFail("请刷新页面",ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,请联系开发人员",ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 获取所有等级 渲染下拉框
      * @return
     */
    @RequestMapping(value = "getAllUserLever",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto dogetAllUserLever(){

        try {
            List<Userlever> userleverList = studentService.getAllUserLever();
            if(userleverList.size()>0){
                return DtoUtil.returnDataSuccess(userleverList);
            }
            return DtoUtil.returnFail("获取用户等级失败",ErrorCode.BIZ_QUERY_FILED);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }
    }


}

package cn.DeepBlue.contorller;


import cn.DeepBlue.Exception.ReplaceTokenExceprion;
import cn.DeepBlue.pojo.User;
import cn.DeepBlue.pojo.dto.Dto;
import cn.DeepBlue.pojo.vo.TokenVO;
import cn.DeepBlue.service.TokenService;
import cn.DeepBlue.service.UserLoginService;
import cn.DeepBlue.utils.DtoUtil;
import cn.DeepBlue.utils.EmptyUtils;
import cn.DeepBlue.utils.ErrorCode;
import cn.DeepBlue.utils.RedisAPI;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;

/**
 * admin用户登录以及个人信息操作
 */
@RequestMapping(value = "/api")
@Controller
public class UserLoginController {

    @Resource
    private UserLoginService loginService;

    @Resource
    private TokenService tokenService;

    @Resource
    private RedisAPI redisAPI;


    /**
     * 用户登录方法
     * 判断用户名 生成token
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/userLogin",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto doUserLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request){
        try {
            if(EmptyUtils.isNotEmpty(username)&&EmptyUtils.isNotEmpty(password)){
               User user = loginService.doUserLogin(username,password);
                if(user!=null){
                    String token = tokenService.generateToken(request.getHeader("user-agent"),user);
                    TokenVO tokenVO = new TokenVO();
                    tokenVO.setToken(token);
                    tokenVO.setGenTime(Calendar.getInstance().getTimeInMillis());
                    //设置过期时间
                    tokenVO.setExpTime(Calendar.getInstance().getTimeInMillis()+tokenService.TOKEN_TIMEOUT*1000);
                    tokenVO.setUser(user);
                    //5.返回数据
                    return DtoUtil.returnDataSuccess(tokenVO);
                }
               return DtoUtil.returnFail("用户名密码错误",ErrorCode.AUTH_AUTHENTICATION_FAILED);

            }else{
                return DtoUtil.returnFail("用户名密码不能为空", ErrorCode.AUTH_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常，请联系开发人员",ErrorCode.AUTH_UNKNOWN);
        }
    }

    /**
     * 获取当前用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto doGetUserInfo(HttpServletRequest request){
        try {
            String token = request.getHeader("token");

                String currUserInfo = redisAPI.get(token);

                User currUser = JSON.parseObject(currUserInfo, User.class);

                User resUser =  loginService.getUserInfoById(currUser.getUid());

                if(EmptyUtils.isNotEmpty(resUser)){
                    return DtoUtil.returnDataSuccess(resUser);
                }
                return DtoUtil.returnFail("查询失败",ErrorCode.AUTH_AUTHENTICATION_FAILED);

        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常，请联系开发人员",ErrorCode.AUTH_UNKNOWN);
        }
    }

    /**
     * 修改用户密码admin
     * @param newPassword
     * @param request
     * @return
     */
    @RequestMapping(value = "/changeUserPwd",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto doChangeUserPassword(@RequestParam(value = "newPassword") String newPassword,HttpServletRequest request){
        try {
            User currUser = JSON.parseObject(redisAPI.get(request.getHeader("token")), User.class);
            if(EmptyUtils.isNotEmpty(newPassword)){
             Integer result = loginService.upDateUserPassword(newPassword,currUser.getUid());
                 if(result>0){
                  return DtoUtil.returnSuccess();
                 }
                 return DtoUtil.returnFail("修改新密码失败",ErrorCode.AUTH_AUTHENTICATION_FAILED);
            }else{
                return DtoUtil.returnFail("新密码不能为空",ErrorCode.AUTH_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常，请联系开发人员",ErrorCode.AUTH_UNKNOWN);
        }
    }

    /**
     * 修改用户信息 admin
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto doUpdateUserInfo(@RequestBody User user,HttpServletRequest request){
        try {
            User currUser = JSON.parseObject(redisAPI.get(request.getHeader("token")), User.class);
            if(EmptyUtils.isNotEmpty(user)){
              int result = loginService.upDateUserInfo(user,currUser);
              if(result>0){
                  return DtoUtil.returnSuccess();
              }
              return DtoUtil.returnFail("修改失败",ErrorCode.AUTH_AUTHENTICATION_FAILED);
            }else{
                return DtoUtil.returnFail("必填项不能为空",ErrorCode.AUTH_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统错误，请联系开发人员",ErrorCode.AUTH_UNKNOWN);
        }
    }

    /**
     * 退出
     * @param request
     * @return
     */
    @RequestMapping(value = "/userLoginOut",produces = "application/json",method = RequestMethod.GET)
    @ResponseBody
    public Dto douserLoginOut(HttpServletRequest request){
        try {
            String token = request.getHeader("token");
            if(tokenService.verificationToken(token)){
                return DtoUtil.returnSuccess();
            }
            return DtoUtil.returnFail("退出失败，请重试",ErrorCode.BIZ_UNKNOWN);
        } catch (ReplaceTokenExceprion replaceTokenExceprion) {
            replaceTokenExceprion.printStackTrace();
            return DtoUtil.returnFail(replaceTokenExceprion.getMessage(),ErrorCode.BIZ_TOKEN_EXPIRE);
        } catch (Exception e){
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }

    }

    /**
     * 获取总数
      * @param userRole
     * @return
     */
    @RequestMapping(value = "getAllUserCount",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto  dogetAllUserCount(@RequestParam(value = "userRole",required = false)Integer userRole){
        try {
            HashMap<Object, Object> paramMap = new HashMap<>();
            if(EmptyUtils.isNotEmpty(userRole)){
                paramMap.put("userrole",userRole);
            }
            Integer res = loginService.getCountByMap(paramMap);
            if(res>=0){
                return DtoUtil.returnDataSuccess(res);
            }
            return DtoUtil.returnFail("请刷新页面重试",ErrorCode.BIZ_QUERY_FILED);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }

    }

}

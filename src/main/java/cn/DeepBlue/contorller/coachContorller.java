package cn.DeepBlue.contorller;

import cn.DeepBlue.pojo.Course;
import cn.DeepBlue.pojo.User;
import cn.DeepBlue.pojo.dto.Dto;
import cn.DeepBlue.service.ClassService;
import cn.DeepBlue.service.CoachService;
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

@RequestMapping(value = "api")
@Controller
public class coachContorller {

    @Resource
    private CoachService coachService;

    @Resource
    private ClassService classService;

    @Resource
    private RedisAPI redisAPI;

    /**
     * 获取所有教练信息
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "allCoachListInfo", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto doallCoachListInfo(@RequestParam(required = false) String username) {
        try {
            HashMap<Object, Object> paramMap = new HashMap<>();
            if (EmptyUtils.isNotEmpty(username)) {
                paramMap.put("username", username);
            }
            List<User> userList = coachService.getAllCoachInfo(paramMap);
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
     * 获取教练个人信息
     *
     * @param uid
     * @return
     */
    @RequestMapping(value = "getCoachInfo/{uid}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto dogetCoachInfo(@PathVariable(value = "uid") String uid) {
        try {
            if (EmptyUtils.isNotEmpty(uid)) {
                User user = coachService.getCoachInfoById(uid);
                if (user != null) {
                    return DtoUtil.returnDataSuccess(user);
                }
                return DtoUtil.returnFail("获取数据失败", ErrorCode.BIZ_QUERY_FILED);
            } else {
                return DtoUtil.returnFail("请刷新页面重试", ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常,请先联系开发人员", ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 提交教练信息
     *
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "updateCoachInfo", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto doupdateCoachInfo(@RequestBody User user, HttpServletRequest request) {
        try {
            if (EmptyUtils.isNotEmpty(user) && EmptyUtils.isNotEmpty(user.getUid())) {
                User currUser = JSON.parseObject(redisAPI.get(request.getHeader("token")), User.class);
                Integer result = coachService.updateUserInfo(user, currUser);
                if (result > 0) {
                    return DtoUtil.returnSuccess();
                }
                return DtoUtil.returnFail("修改失败,请重试", ErrorCode.BIZ_UPDATE_FILED);
            } else {
                return DtoUtil.returnFail("请刷新页面", ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 新增教练信息
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertCoach",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto doinsertStudent(@RequestBody User user,HttpServletRequest request){
        try {
            if(EmptyUtils.isNotEmpty(user)){
                User currUser = JSON.parseObject(redisAPI.get(request.getHeader("token")), User.class);
                Integer result = coachService.insertCoach(user,currUser);
                if(result>0){
                    return DtoUtil.returnSuccess();
                }
                return DtoUtil.returnFail("添加教练失败",ErrorCode.BIZ_INSERT_FILED);
            }else{
                return DtoUtil.returnFail("必填项不能为空",ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 删除教练以及其课程
     * @param uid
     * @param request
     * @return
     */
    @RequestMapping(value = "/delCoach/{uid}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto dodelStudent(@PathVariable("uid") Integer uid,HttpServletRequest request){
        try {
            if(EmptyUtils.isNotEmpty(uid)){
                Integer result = coachService.delCoachById(uid);
                Integer res = classService.delClassInfoByCaochId(uid);
                if(result>0&&res>0){
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
     * 教练客户端查询当前教练的所有课程
      * @param uid
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "getCurrCoachClass",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto dogetCurrCoachClass(@RequestParam(value = "uid")Integer uid,
                                   @RequestParam(value = "startDate")String startDate,
                                   @RequestParam(value = "endDate")String endDate){
        try {
            if(EmptyUtils.isNotEmpty(uid)){
             List<Course> courseList =classService.getCurrCoachAllClass(uid,startDate,endDate);
             if(courseList.size()>0){
                 return DtoUtil.returnDataSuccess(courseList);
             }
                return DtoUtil.returnFail("暂无课程",ErrorCode.BIZ_QUERY_FILED);
            }else{
                return DtoUtil.returnFail("请刷新页面",ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }
    }
}

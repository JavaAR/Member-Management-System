package cn.DeepBlue.contorller;

import cn.DeepBlue.pojo.Subscribe;
import cn.DeepBlue.pojo.Subscribestatus;
import cn.DeepBlue.pojo.User;
import cn.DeepBlue.pojo.dto.Dto;
import cn.DeepBlue.service.SubscribeService;
import cn.DeepBlue.utils.DtoUtil;
import cn.DeepBlue.utils.EmptyUtils;
import cn.DeepBlue.utils.ErrorCode;
import cn.DeepBlue.utils.RedisAPI;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 预约控制器
 */
@RequestMapping("api")
@Controller
public class SubscribeContorller {

    @Resource
    private SubscribeService subscribeService;

    @Resource
    private RedisAPI redisAPI;
    /**
     * 查看是否重复预约
     * @param classId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getSubscribe",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto dosubscribe(@RequestParam(value = "classId") Integer classId,
                           @RequestParam(value = "userId") Integer userId){
        try {
            if(EmptyUtils.isNotEmpty(classId)&&EmptyUtils.isNotEmpty(userId)){
             boolean flag = subscribeService.getSubscribe(classId,userId);
             if(flag){
                 return DtoUtil.returnSuccess();
             }
             return DtoUtil.returnFail("已经预约过啦",ErrorCode.BIZ_QUERY_FILED);
            }else{
                return DtoUtil.returnFail("请刷新页面", ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统错误",ErrorCode.BIZ_UNKNOWN);
        }
    }
    /**
     *预约操作
     * @param subscribe
     * @return
     */
    @RequestMapping(value = "/subscribeClass",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto dosubscribeClass( Subscribe subscribe, HttpServletRequest request){
        try {
            if(EmptyUtils.isNotEmpty(subscribe)){
                User currUser = JSON.parseObject(redisAPI.get(request.getHeader("token")), User.class);
                Integer result = subscribeService.subscribeClass(subscribe,currUser);
                if(result>0){
                    return DtoUtil.returnSuccess();
                }
                return DtoUtil.returnFail("预约失败，请重试",ErrorCode.BIZ_INSERT_FILED);
            }else{
                return DtoUtil.returnFail("请刷新页面",ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 当前用户预约的课程以及考勤
     * @param uid
     * @return
     */
    @RequestMapping(value = "getSubscribeByUser",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public  Dto dogetSubscribeByUser(@RequestParam(value = "uid") Integer uid,
                                     @RequestParam(required = false, value = "startDate") String startDate,
                                     @RequestParam(required = false,value = "endDate")String endDate){
        try {
            if(EmptyUtils.isNotEmpty(uid)){
                HashMap<Object, Object> paramMap = new HashMap<>();
                paramMap.put("userid",uid);
                if(EmptyUtils.isNotEmpty(startDate)&&EmptyUtils.isNotEmpty(endDate)){
                    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
                    Date sdate = sdf.parse(startDate);
                    Calendar c = Calendar.getInstance();
                    Date edate = sdf.parse(endDate);
                    c.setTime(edate);
                    int day=c.get(Calendar.DATE);
                    c.set(Calendar.DATE,day+1);
                    paramMap.put("coursestarttime",sdate);
                    paramMap.put("courseendtime",c.getTime());
                }
               List<Subscribe> subscribeList = subscribeService.getSubscribeByUser(paramMap);
                if(subscribeList.size()==0){
                    return DtoUtil.returnFail("暂无数据",ErrorCode.BIZ_QUERY_FILED);
                }
                return DtoUtil.returnDataSuccess(subscribeList);
            }else{
                return DtoUtil.returnFail("请刷新页面后重试",ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 教练段获取当前课程所有预约学员
      * @param cid
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "getSubscribeStudent",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto dogetSubscribeStudent(@RequestParam(value = "cid") Integer cid,
                                     @RequestParam(value = "startTime",required = false)String startTime,
                                     @RequestParam(value = "endTime",required = false)String endTime){
        try {
            if(EmptyUtils.isNotEmpty(cid)){
                HashMap<Object, Object> paramMap = new HashMap<>();
                paramMap.put("courseid",cid);
                if(EmptyUtils.isNotEmpty(startTime)&&EmptyUtils.isNotEmpty(endTime)){
                    paramMap.put("coursestarttime",startTime);
                    paramMap.put("courseendtime",endTime);
                }
                List<Subscribe> subscribeList = subscribeService.getSubscribeByUserforMap(paramMap);
                if(subscribeList.size()>0){
                    return DtoUtil.returnDataSuccess(subscribeList);
                }
                return DtoUtil.returnFail("暂无预约",ErrorCode.BIZ_QUERY_FILED);
            }else{
                return DtoUtil.returnFail("请刷新页面后重试",ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统错误",ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 教练段学员打卡考勤操作
     * @param sid
     * @param request
     * @return
     */
    @RequestMapping(value = "punchCard",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto dopunchCard(@RequestParam(value = "sid")Integer sid,
                           @RequestParam(value = "statusid")String statusid,
                           HttpServletRequest request){
        try {
            if(EmptyUtils.isNotEmpty(sid)){
                User currCoach = JSON.parseObject(redisAPI.get(request.getHeader("token")), User.class);
                int result = subscribeService.updateSubscribe(sid,statusid,currCoach);
                if(result>0){
                    return DtoUtil.returnSuccess();
                }
                return DtoUtil.returnFail("打卡失败，请重试",ErrorCode.BIZ_UPDATE_FILED);
            }else{
                return DtoUtil.returnFail("请刷新页面后重试",ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 学员端取消预约
     * @param cid
     * @param uid
     * @return
     */
    @RequestMapping(value = "cancelsubscribe",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto docancelsubscribe(@RequestParam(value = "cid")Integer cid,
                                 @RequestParam(value = "uid") Integer uid){
        try {
            if(EmptyUtils.isNotEmpty(cid)&&EmptyUtils.isNotEmpty(uid)){
               int result = subscribeService.deleSubscribeInfo(cid,uid);
                if(result>0){
                    return DtoUtil.returnSuccess();
                }
                return DtoUtil.returnFail("取消预约失败",ErrorCode.BIZ_DELETE_FILED);
            }else{
                return DtoUtil.returnFail("请刷新页面",ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统错误",ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 查询当前教练的所有预约课程
     * @param coachId
     * @return
     */
    @RequestMapping(value = "getCurrCoachSubscribeClass",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto dogetCurrCoachSubscribeClass(@RequestParam(value = "coachId") Integer coachId){
        try {
            if(EmptyUtils.isNotEmpty(coachId)){
             List<Subscribe> subscribeList = subscribeService.getCurrCoachSubscribeClass(coachId);
                if(subscribeList.size()>0){
                    return DtoUtil.returnDataSuccess(subscribeList);
                }
                return DtoUtil.returnFail("暂无预约",ErrorCode.BIZ_QUERY_FILED);
            }else{
                return DtoUtil.returnFail("请刷新页面后重试",ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 教练段迟到自动打卡
     * @param SubscribeId
     * @return
     */
    @RequestMapping(value = "classOverPunchCard",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto doclassOverPunchCard(@RequestParam(value = "SubscribeId") Integer SubscribeId){
        try {
            if(EmptyUtils.isNotEmpty(SubscribeId)){
              int result = subscribeService.doclassOverPunchCard(SubscribeId);
              if(result>0){
                return DtoUtil.returnSuccess();
              }
              return DtoUtil.returnFail("自动打卡失败",ErrorCode.BIZ_UPDATE_FILED);
            }else{
                return DtoUtil.returnFail("请刷新页面后重试",ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 管理员端查询所有的考勤记录
     *
     * @param status
     * @return
     */
    @RequestMapping(value = "getAllSubscribeInfo",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto dogetAllSubscribeInfo(@RequestParam(value = "status",required = false)Integer status){
        try {
            HashMap<Object, Object> paramMap = new HashMap<>();
            if(EmptyUtils.isNotEmpty(status)){
                paramMap.put("status",status);
            }
            List<Subscribe>  subscribeList = subscribeService.getAllSubscribeInfo(paramMap);
            if(subscribeList.size()>0){
                return DtoUtil.returnDataSuccess(subscribeList);
            }
            return DtoUtil.returnFail("暂无数据",ErrorCode.BIZ_QUERY_FILED);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统错误",ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 管理员 获取当前预约信息
     * @param subscribeid
     * @return
     */
    @RequestMapping(value = "getsubscribeInfo",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto dogetsubscribeInfo(@RequestParam("subscribeid")Integer subscribeid){
        if(EmptyUtils.isNotEmpty(subscribeid)){
            HashMap<Object, Object> paramMap = new HashMap<>();
            paramMap.put("subscribeid",subscribeid);
            List<Subscribe> info = subscribeService.getAllSubscribeInfo(paramMap);
            if(info.size()>0){
                return DtoUtil.returnDataSuccess(info.get(0));
            }
            return DtoUtil.returnFail("查询失败，请重试",ErrorCode.BIZ_QUERY_FILED);
        }else{
            return DtoUtil.returnFail("获取信息失败，请刷新页面",ErrorCode.BIZ_PARAMETER_ERROR);
        }
    }

    /**
     * 获取所有预约状态
     * @return
     */
    @RequestMapping(value = "getAllSubscribeidStatus",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto dogetAllSubscribeid(){
        try {
            List<Subscribestatus> subscribeList =  subscribeService.getAllSubscribeidStatus();
            if(subscribeList.size()>0){
                return DtoUtil.returnDataSuccess(subscribeList);
            }else{
                return DtoUtil.returnFail("查询预约状态失败",ErrorCode.BIZ_QUERY_FILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 查询预约总数
     * @param uid
     * @param cid
     * @param coid
     * @return
     */
    @RequestMapping(value = "getAllSubscribeCount",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto dogetAllSubscribeCount(@RequestParam(value = "status")Integer status,@RequestParam(value = "uid",required = false) Integer uid,@RequestParam(value = "cid",required = false) Integer cid,@RequestParam(value = "coid",required = false) Integer coid){
        try {
            HashMap<Object, Object> paramMap = new HashMap<>();
            if(EmptyUtils.isNotEmpty(uid)){
                paramMap.put("userid",uid);
            }else if(EmptyUtils.isNotEmpty(cid)){
                paramMap.put("courseid",cid);
            }else if(EmptyUtils.isNotEmpty(coid)){
                paramMap.put("ucoachid",coid);
            }else if(EmptyUtils.isNotEmpty(status)){
                paramMap.put("status",status);
            }
            Integer result =  subscribeService.getCountByMap(paramMap);
            if(result>0){
                return DtoUtil.returnDataSuccess(result);
            }
            return DtoUtil.returnFail("查询失败",ErrorCode.BIZ_QUERY_FILED);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }
    }


}

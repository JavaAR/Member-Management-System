package cn.DeepBlue.contorller;


import cn.DeepBlue.pojo.*;
import cn.DeepBlue.pojo.dto.Dto;
import cn.DeepBlue.pojo.vo.ClassInfoVo;
import cn.DeepBlue.service.ClassService;
import cn.DeepBlue.service.ClassifyService;
import cn.DeepBlue.service.SubscribeService;
import cn.DeepBlue.utils.DtoUtil;
import cn.DeepBlue.utils.EmptyUtils;
import cn.DeepBlue.utils.ErrorCode;
import cn.DeepBlue.utils.RedisAPI;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * 课程控制器
 */
@Controller
@RequestMapping("/api")
public class ClassContorller {


    @Resource
    private ClassService classService;

    @Resource
    private ClassifyService classifyService;

    @Resource
    private SubscribeService SubscribeService;

    @Resource
    private RedisAPI redisAPI;


    /**
     * 获取所有课程信息
     *
     * @param classname
     * @return
     */
    @RequestMapping(value = "/allClassInfo", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto doallClassInfo(@RequestParam(value = "classname", required = false) String classname,
                              @RequestParam(value = "startDate",required = false)String startDate,
                              @RequestParam(value = "endDate",required = false) String endDate) {
        try {
            HashMap<Object, Object> paramMap = new HashMap<>();
            if (EmptyUtils.isNotEmpty(classname)||EmptyUtils.isNotEmpty(startDate)||EmptyUtils.isNotEmpty(endDate)) {
                paramMap.put("classname", classname);
                paramMap.put("startDate",startDate);
                paramMap.put("endDate",endDate);
                List<Course> courseList = classService.getAllClassInfoByParam(paramMap);
                if(courseList!=null){
                    return DtoUtil.returnDataSuccess(courseList);
                }
                return DtoUtil.returnFail("暂无数据",ErrorCode.BIZ_QUERY_FILED);
            }else{
                List<Course> courseList = classService.getAllClassInfo(paramMap);
                if (courseList.size() > 0) {
                    return DtoUtil.returnDataSuccess(courseList);
                }
                return DtoUtil.returnFail("暂无课程", ErrorCode.BIZ_QUERY_FILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", ErrorCode.BIZ_UNKNOWN);
        }
    }
    /**
     * 获取所有课程分类信息
     *
     * @return
     */
    @RequestMapping(value = "/getAllCourseClassify", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto dogetAllCourseClassify() {
        try {
            List<Courseclassify> courseclassifyList = classifyService.getAllClassify();
            if (courseclassifyList.size() > 0) {
                return DtoUtil.returnDataSuccess(courseclassifyList);
            }
            return DtoUtil.returnFail("获取课程等级失败", ErrorCode.BIZ_QUERY_FILED);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 查询所有教练
     *
     * @return
     */
    @RequestMapping(value = "getallCourse", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto dogetallCourse() {
        try {
            List<User> userList = classService.getAllCourseInfo();
            if (userList.size() > 0) {
                return DtoUtil.returnDataSuccess(userList);
            }
            return DtoUtil.returnFail("获取教练信息失败", ErrorCode.BIZ_UNKNOWN);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 获取所有地址
     *
     * @return
     */
    @RequestMapping(value = "getAllAddress", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto dogetAllAddress() {
        try {
            List<Address> addressList = classService.getAllAddressInfo();
            if (addressList.size() > 0) {
                return DtoUtil.returnDataSuccess(addressList);
            }
            return DtoUtil.returnFail("获取地址失败", ErrorCode.BIZ_QUERY_FILED);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 查看单个课程的详细信息
     *
     * @param cid
     * @return
     */
    @RequestMapping(value = "getClassInfo/{cid}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto dogetClassInfo(@PathVariable("cid") Integer cid) {
        try {
            if (EmptyUtils.isNotEmpty(cid)) {
                Course course = classService.getClassInfoById(cid);
                if (course != null) {
                    return DtoUtil.returnDataSuccess(course);
                }
                return DtoUtil.returnFail("查询失败，请重试", ErrorCode.BIZ_QUERY_FILED);
            }
            return DtoUtil.returnFail("请刷新页面后重试", ErrorCode.BIZ_PARAMETER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     *
     * 修改课程信息
     *
     * @param course
     * @param request
     * @return
     */
    @RequestMapping(value = "updateClassInfo", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public Dto doupdateClassInfo(@RequestBody Course course, HttpServletRequest request) {
        try {
            if (EmptyUtils.isNotEmpty(course) && EmptyUtils.isNotEmpty(course.getCid())) {
                User currUser = JSON.parseObject(redisAPI.get(request.getHeader("token")), User.class);
                Integer result = classService.updateClassInfo(course, currUser);
                if (result > 0) {
                    return DtoUtil.returnSuccess();
                }
                return DtoUtil.returnFail("修改失败，请重试", ErrorCode.BIZ_UPDATE_FILED);
            }
            return DtoUtil.returnFail("请刷新页面", ErrorCode.BIZ_PARAMETER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常，请联系开发人员", ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 新增课程信息
     *
     * @param
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertClassInfo", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto doinsertClassInfo(ClassInfoVo classInfoVo, HttpServletRequest request) {
        try {
            if(EmptyUtils.isNotEmpty(classInfoVo.getArrDates())){
                //获取当前用户
                User currUser = JSON.parseObject(redisAPI.get(request.getHeader("token")), User.class);
                //字符转数组
                JSONArray dateArry = JSONArray.fromObject(classInfoVo.getArrDates());
                //组织参数
                Course course = new Course();
                BeanUtils.copyProperties(classInfoVo,course);
                //执行操作
               boolean flag = classService.insertClass(course,dateArry,currUser,classInfoVo.getStartTime(),classInfoVo.getEndTime());
               if(flag){
                   return DtoUtil.returnSuccess();
               }
               return DtoUtil.returnFail("添加课程失败",ErrorCode.BIZ_INSERT_FILED);
            }else{
                return DtoUtil.returnFail("必填项不能为空",ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }
    }

    /**
     * 删除课程
     * @param cid
     * @return
     */
    @RequestMapping(value = "delClassInfo/{cid}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto dodelClassInfo(@PathVariable(value = "cid")String cid){
        try {
            if(EmptyUtils.isNotEmpty(cid)){
              Integer result = classService.delClassInfo(cid);
              if(result>0){
                  return DtoUtil.returnSuccess();
              }
              return DtoUtil.returnFail("删除失败,请重试",ErrorCode.BIZ_DELETE_FILED);
            }
            return DtoUtil.returnFail("请刷新页面",ErrorCode.BIZ_PARAMETER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_QUERY_FILED);
        }

    }

    /**
     * 获取当前课程的所有预约信息
     * @param cid
     * @param statusid
     * @return
     */
    @RequestMapping(value = "getCurrClassSubscribeInfo",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto getCurrClassSubscribeInfo(@RequestParam(value = "cid")Integer cid,
                                          @RequestParam(value = "statusid",required = false) Integer statusid){
        try {
            HashMap<Object, Object> paramMap = new HashMap<>();
            if(EmptyUtils.isNotEmpty(cid)){
                paramMap.put("courseid",cid);
                if(EmptyUtils.isNotEmpty(statusid)){
                    paramMap.put("status",statusid);
                }
                List<Subscribe> subscribeList = SubscribeService.getCurrClassSubscribeInfo(paramMap);
                if(subscribeList.size()>0){
                    return DtoUtil.returnDataSuccess(subscribeList);
                }else {
                    return DtoUtil.returnFail("暂无数据",ErrorCode.BIZ_QUERY_FILED);
                }
            }else{
                return DtoUtil.returnFail("请刷新页面后重试",ErrorCode.BIZ_PARAMETER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常",ErrorCode.BIZ_UNKNOWN);
        }
    }

}

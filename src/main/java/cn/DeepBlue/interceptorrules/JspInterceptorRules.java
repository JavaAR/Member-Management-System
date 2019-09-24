package cn.DeepBlue.interceptorrules;

import cn.DeepBlue.Exception.UserNoLoginException;
import cn.DeepBlue.pojo.User;
import cn.DeepBlue.utils.RedisAPI;
import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局请求拦截类
 */
public class JspInterceptorRules  extends HandlerInterceptorAdapter {

    @Resource
    private RedisAPI redisAPI;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String token = request.getHeader("token");
        String currUser = redisAPI.get(token);
        User user = JSON.parseObject(currUser, User.class);
        if(user==null){
              throw new UserNoLoginException("用户还未登录");
        }
        return true;
    }
}

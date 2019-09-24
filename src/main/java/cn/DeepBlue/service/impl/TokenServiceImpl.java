package cn.DeepBlue.service.impl;

import cn.DeepBlue.Exception.ReplaceTokenExceprion;
import cn.DeepBlue.pojo.User;
import cn.DeepBlue.service.TokenService;
import cn.DeepBlue.utils.MD5;
import cn.DeepBlue.utils.RedisAPI;
import cn.DeepBlue.utils.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 用于生成token 置换token 删除token 并根据客户端类型判断token存入redis中的时间
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private RedisAPI redisAPI;

    /**
     * 生成token
     * @param header
     * @param user
     * @return
     * @throws IOException
     * @throws Exception
     */
    @Override
    public String generateToken(String header, User user) throws IOException, Exception {
        UASparser uasParser = UserAgentUtil.getUasParser();
        //解析客户端请求头的User-Agent属性
        UserAgentInfo userAgentInfo = uasParser.parse(header);
        //如果解析类型为未知
        String clentType="";
        if (userAgentInfo.getDeviceType().equals(UserAgentInfo.UNKNOWN)){
            if (UserAgentUtil.CheckAgent(header)){
                //为移动端
                clentType="MOBILE-";
            }else{
                //为pc端
                clentType="PC-";
            }
        }else if (userAgentInfo.getDeviceType().equals("Personal computer")){
            //客户端为pc端
            clentType="PC-";
        }else{
            //客户端为移动端
            clentType="MOBILE-";
        }
        //准备存入redis的数据（key）
        StringBuffer buffer = new StringBuffer("token:");
        buffer.append(clentType);
        buffer.append(MD5.getMd5(user.getUserphone(),32)+"-");
        buffer.append(user.getUid()+"-");
        buffer.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"-" );
        buffer.append(MD5.getMd5(header,6));
        //判断存入redis的有效时间
        if (clentType.equals("PC-")){
            //pc 设置两小时过期
            redisAPI.set(buffer.toString(),TOKEN_TIMEOUT, JSON.toJSONString(user));
        }else{
            //移动端 永久有效
            redisAPI.set(buffer.toString(),JSON.toJSONString(user));
        }
        return buffer.toString();
    }
    /**
     * 重置token
     * @param oldToken
     * @param userAgent
     * @return
     *    1、首先要判断token是否有效
     * 	  2、生成token后的1个小时内不允许置换
     * 	  3、置换token时，需要生成新token，并且旧token不能立即失效，应设置为置换后的时间延长2分钟
     * 	  4、兼容手机端和PC端
     */
    @Override
    public String replaceToken(String oldToken, String userAgent) throws Exception {
        //1.判断token是否有效
        if (!redisAPI.exist(oldToken)){
            throw new ReplaceTokenExceprion("token已失效或者token不存在");
        }
        //2.生成token后的一个小时内不允许置换
        //截取旧token
        String[] split = oldToken.split("-");
        Date oldTokenGenTime = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        oldTokenGenTime = dateFormat.parse(split[3]);
        //时间差
        long timeDifference = Calendar.getInstance().getTimeInMillis() - oldTokenGenTime.getTime();
        System.out.println(timeDifference);
        if (timeDifference<this.TOKEN_PORTECT_TIME*1000){
            throw new ReplaceTokenExceprion("token保护时间未到,剩余"+(this.TOKEN_PORTECT_TIME*1000-timeDifference)/1000+"s");
        }
        //3.置换token
        //从redis中取出旧token对应的对象信息
        User user = JSON.parseObject(redisAPI.get(oldToken),User.class);
        Long ttl = redisAPI.ttl(oldToken);
        if (ttl>0||ttl==-1) {
            //生成新的token
            String newToken = this.generateToken(userAgent,user);
            //设置旧token两分钟后过期
            redisAPI.set(oldToken,this.OLDTOKEN_PROLONG_TIME, JSON.toJSONString(user));
            return newToken;
        } else {
            throw new ReplaceTokenExceprion("tonke置换失败,旧token已失效");
        }
    }

    @Override
    public boolean verificationToken(String token) throws ReplaceTokenExceprion {
        boolean flag = false;
        if(redisAPI.exist(token)){
            redisAPI.delete(token);
            flag = true;
        }
        return flag;
    }
}

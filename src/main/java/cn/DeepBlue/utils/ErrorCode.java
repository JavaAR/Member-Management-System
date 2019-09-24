package cn.DeepBlue.utils;

/**
 * 系统错误编码，根据业务定义如下
 * <br/>
 * 酒店主业务biz：1开头（10000）<br/>
 * 评论：10001 ——10100<br/>
 * 酒店详情：10101 ——10200<br/>
 * 订单：10201 ——10400<br/>
 * 搜索search：2开头（20000）<br/>
 * 认证auth：3开头（30000）<br/>
 * 支付trade：4开头（40000）<br/>
 * @author hduser
 *
 */
public class ErrorCode {

	/*认证模块错误码-start*/
	public final static String AUTH_UNKNOWN="30000";
	/**
	 * 用户已存在
	 */
	public final static String AUTH_USER_ALREADY_EXISTS="30001";
	/**
	 * 认证失败
	 */
	public final static String AUTH_AUTHENTICATION_FAILED="30002";
	/**
	 * 用户名密码参数错误，为空
	 */
	public final static String AUTH_PARAMETER_ERROR="30003";
	/**
	 * 邮件注册，激活失败
	 */
	public final static String AUTH_ACTIVATE_FAILED="30004";
	/**
	 * 置换token失败
	 */
	public final static String AUTH_REPLACEMENT_FAILED="30005";
	/**
	 * token无效
	 */
	public final static String AUTH_TOKEN_INVALID="30006";
	/**
	 * 非法的用户名
	 */
	public static final String AUTH_USERISONT_LOGIN = "30007";
	/*认证模块错误码-end*/

	//业务模块错误码
	/**
	 * 参数错误
	 */
	public final static String BIZ_PARAMETER_ERROR="10101";
	/**
	 * 获取酒店总评论数失败
	 */
	public final static String BIZ_FAIL_ALLCOMMENTCOUNT="10102";
	/**
	 * 获取酒店有图片评论数失败
	 */
	public final static String BIZ_FAIL_IMGCOMMENTCOUNT="10103";
	/**
	 * 获取酒店有待改善评论数失败
	 */
	public final static String BIZ_FAIL_HAVECHANGECOMMENTCOUNT="10104";
	/**
	 * 获取酒店值得推荐评论数失败
	 */
	public final static String BIZ_FAIL_RECOMMENDCOMMENTCOUNT="10105";
	/**
	 * 系统错误
	 */
	public final static String BIZ_UNKNOWN="10200";
	/**
	 * 查询失败
	 */
	public final static  String BIZ_QUERY_FILED="10106";
	/**
	 * 新增联系人失败
	 */
	public final static  String BIZ_INSERT_FILED="10107";
	/**
	 * 修改联系人失败
	 */
	public final static  String BIZ_UPDATE_FILED="10108";
	/**
	 *联系人订单未支付
	 */
	public final static  String BIZ_LINKUSER_ORDER_NOPAY="10109";
	/**
	 * 删除常用联系人失败
	 */
	public final static  String BIZ_DELETE_FILED="10110";
	/**
	 * TOKEN 过期
	 */
	public final static  String BIZ_TOKEN_EXPIRE="10000";

	//搜索模块错误码
	/**
	 * 参数错误
	 */
	public final static String SEARCH_PARAMETER_ERROR="20001";
	/**
	 * 系统错误
	 */
	public final static String SEARCH_UNKNOWN="20000";
	/**
	 * 查询失败
	 */
	public final static  String SEARCH_QUERY_FILED="20002";
	//支付模块错误码
	/**
	 * 参数错误
	 */
	public final static  String TRADE_PARAMETER_ERROR="40001";
	/**
	 * 服务器异常
	 */
	public final static String TRADE_UNKNOWN="40000";
	/**
	 * 获取预支付连接失败
	 */
	public final static String TRADE_GETURL_FILE="40002";




}

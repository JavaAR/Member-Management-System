package cn.DeepBlue.pojo.vo;

import cn.DeepBlue.pojo.User;

import java.io.Serializable;

/**
 * 返回前端-Token相关VO
 */
public class TokenVO implements Serializable {
	/**
	 * 用户认证凭据
	 */
	private String token;
	/**
	 * 过期时间
	 */
	private long expTime;
	/**
	 * 生成时间
	 */
	private long genTime;
	/**
	 * 用户信息
	 */
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getExpTime() {
		return expTime;
	}
	public void setExpTime(long expTime) {
		this.expTime = expTime;
	}
	public long getGenTime() {
		return genTime;
	}
	public void setGenTime(long genTime) {
		this.genTime = genTime;
	}
	
	public TokenVO() {
		super();
	}
	public TokenVO(String token, long expTime, long genTime) {
		super();
		this.token = token;
		this.expTime = expTime;
		this.genTime = genTime;
	}
	
	
}

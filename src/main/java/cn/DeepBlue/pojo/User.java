package cn.DeepBlue.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class User {
    /**
     * 用户id
     */
    @Id
    @Column(name = "uID")
    private Integer uid;

    /**
     * 用户姓名
     */
    @Column(name = "UserName")
    private String username;

    /**
     * 用户昵称 用于登录的用户名
     */
    @Column(name = "UserCode")
    private String usercode;

    /**
     * 用户密码 用户登陆的密码
     */
    @Column(name = "UserPassword")
    private String userpassword;

    /**
     * 用户角色 是角色表的id用户的角色（1超级管理员 ，2教练，3学员）
     */
    @Column(name = "UserRole")
    private Integer userrole;

    /**
     * 用户联系方式
     */
    @Column(name = "UserPhone")
    private String userphone;

    /**
     * 是否限制登录
     */
    @Column(name = "Forbidden")
    private String forbidden;

    /**
     * 创建人
     */
    @Column(name = "CREATED_BY")
    private String createdBy;

    /**
     * 创建时间
     */
    @Column(name = "CREATED_TIME")
    private Date createdTime;

    /**
     * 更新人
     */
    @Column(name = "UPDATED_BY")
    private String updatedBy;

    /**
     * 更新时间
     */
    @Column(name = "UPDATED_TIME")
    private Date updatedTime;

    @Column(name = "Age")
    private String age;

    @Column(name = "Gender")
    private Integer gender;
    /**
     * 学院级别
     */
    @Column(name = "UserLever")
    private Integer userlever;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Birthday")
    private Date birthday;
    /**
     * 用户背景 用户背景
     */
    @Column(name = "UserBackground")
    private String userbackground;
    /**
     * 用户等级
     */
    private String leverName;

    public String getLeverName() {
        return leverName;
    }

    public void setLeverName(String leverName) {
        this.leverName = leverName;
    }

    public Integer getUserlever() {
        return userlever;
    }

    public void setUserlever(Integer userlever) {
        this.userlever = userlever;
    }

    /**
     * 获取用户id
     *
     * @return uID - 用户id
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 设置用户id
     *
     * @param uid 用户id
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 获取用户姓名
     *
     * @return UserName - 用户姓名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户姓名
     *
     * @param username 用户姓名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取用户昵称 用于登录的用户名
     *
     * @return UserCode - 用户昵称 用于登录的用户名
     */
    public String getUsercode() {
        return usercode;
    }

    /**
     * 设置用户昵称
     *
     * @param usercode 用户昵称 用于登录的用户名
     */
    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    /**
     * 获取用户密码 用户登陆的密码
     *
     * @return UserPassword - 用户密码 用户登陆的密码
     */
    public String getUserpassword() {
        return userpassword;
    }

    /**
     * 设置用户密码 用户登陆的密码
     *
     * @param userpassword 用户密码 用户登陆的密码
     */
    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    /**
     * 获取用户角色 是角色表的id用户的角色（1超级管理员 ，2教练，3学员）
     *
     * @return UserRole - 用户角色 是角色表的id用户的角色（1超级管理员 ，2教练，3学员）
     */
    public Integer getUserrole() {
        return userrole;
    }

    /**
     * 设置用户角色 是角色表的id用户的角色（1超级管理员 ，2教练，3学员）
     *
     * @param userrole 用户角色 是角色表的id用户的角色（1超级管理员 ，2教练，3学员）
     */
    public void setUserrole(Integer userrole) {
        this.userrole = userrole;
    }

    /**
     * 获取用户联系方式
     *
     * @return UserPhone - 用户联系方式
     */
    public String getUserphone() {
        return userphone;
    }

    /**
     * 设置用户联系方式
     *
     * @param userphone 用户联系方式
     */
    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    /**
     * 获取是否限制登录
     *
     * @return Forbidden - 是否限制登录
     */
    public String getForbidden() {
        return forbidden;
    }

    /**
     * 设置是否限制登录
     *
     * @param forbidden 是否限制登录
     */
    public void setForbidden(String forbidden) {
        this.forbidden = forbidden;
    }

    /**
     * 获取创建人
     *
     * @return CREATED_BY - 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 设置创建人
     *
     * @param createdBy 创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取创建时间
     *
     * @return CREATED_TIME - 创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置创建时间
     *
     * @param createdTime 创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 获取更新人
     *
     * @return UPDATED_BY - 更新人
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * 设置更新人
     *
     * @param updatedBy 更新人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * 获取更新时间
     *
     * @return UPDATED_TIME - 更新时间
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 设置更新时间
     *
     * @param updatedTime 更新时间
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * @return Age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return Gender
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * @return Birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取用户背景 用户背景
     *
     * @return UserBackground - 用户背景 用户背景
     */
    public String getUserbackground() {
        return userbackground;
    }

    /**
     * 设置用户背景 用户背景
     *
     * @param userbackground 用户背景 用户背景
     */
    public void setUserbackground(String userbackground) {
        this.userbackground = userbackground;
    }
}
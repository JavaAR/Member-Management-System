package cn.DeepBlue.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Subscribe {
    /**
     * 预约表id
     */
    @Id
    @Column(name = "Subscribeid")
    private Integer subscribeid;

    /**
     * 预约课程 课程表的id
     */
    @Column(name = "CourseId")
    private Integer courseid;

    /**
     * 预约学员 用户表里的学员用户id
     */
    @Column(name = "UserId")
    private Integer userid;

    /**
     * 被预约的教练 用户表里的教练用户id
     */
    @Column(name = "UCoachId")
    private Integer ucoachid;

    /**
     * 预约时间
     */
    @Column(name = "SubscribeidDate")
    private Date subscribeiddate;

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
    /**
     * 状态
     */
    @Column(name = "Status")
    private Integer status;


    /**
     * 预约学员手机
     */
    private String studentPhone;

    /**
     * 预约的学员姓名
     */
    private String studentname;

    /**
     * 教练名称
     */
    private String coachname;
    /**
     * 课程名称
     */
    private String coursename;
    /**
     * 开始日期
     */
    private Date coursestarttime;
    /**
     * 结束日期
     */
    private Date courseendtime;

    /**
     * 课程等级表id
     * @return
     */
    private Integer courseclassify;

    /**
     * 地址表id
     * @return
     */
    private Integer addressid;

    public Integer getAddressid() {
        return addressid;
    }

    public void setAddressid(Integer addressid) {
        this.addressid = addressid;
    }

    public Integer getCourseclassify() {
        return courseclassify;
    }

    public void setCourseclassify(Integer courseclassify) {
        this.courseclassify = courseclassify;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getCoachname() {
        return coachname;
    }

    public void setCoachname(String coachname) {
        this.coachname = coachname;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public Date getCoursestarttime() {
        return coursestarttime;
    }

    public void setCoursestarttime(Date coursestarttime) {
        this.coursestarttime = coursestarttime;
    }

    public Date getCourseendtime() {
        return courseendtime;
    }

    public void setCourseendtime(Date courseendtime) {
        this.courseendtime = courseendtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取预约表id
     *
     * @return Subscribeid - 预约表id
     */
    public Integer getSubscribeid() {
        return subscribeid;
    }

    /**
     * 设置预约表id
     *
     * @param subscribeid 预约表id
     */
    public void setSubscribeid(Integer subscribeid) {
        this.subscribeid = subscribeid;
    }

    /**
     * 获取预约课程 课程表的id
     *
     * @return CourseId - 预约课程 课程表的id
     */
    public Integer getCourseid() {
        return courseid;
    }

    /**
     * 设置预约课程 课程表的id
     *
     * @param courseid 预约课程 课程表的id
     */
    public void setCourseid(Integer courseid) {
        this.courseid = courseid;
    }

    /**
     * 获取预约学员 用户表里的学员用户id
     *
     * @return UserId - 预约学员 用户表里的学员用户id
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * 设置预约学员 用户表里的学员用户id
     *
     * @param userid 预约学员 用户表里的学员用户id
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * 获取被预约的教练 用户表里的教练用户id
     *
     * @return UCoachId - 被预约的教练 用户表里的教练用户id
     */
    public Integer getUcoachid() {
        return ucoachid;
    }

    /**
     * 设置被预约的教练 用户表里的教练用户id
     *
     * @param ucoachid 被预约的教练 用户表里的教练用户id
     */
    public void setUcoachid(Integer ucoachid) {
        this.ucoachid = ucoachid;
    }

    /**
     * 获取预约时间
     *
     * @return SubscribeidDate - 预约时间
     */
    public Date getSubscribeiddate() {
        return subscribeiddate;
    }

    /**
     * 设置预约时间
     *
     * @param subscribeiddate 预约时间
     */
    public void setSubscribeiddate(Date subscribeiddate) {
        this.subscribeiddate = subscribeiddate;
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

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }
}
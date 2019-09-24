package cn.DeepBlue.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Course {
    /**
     * 课程Id
     */
    @Id
    @Column(name = "CId")
    private Integer cid;

    /**
     * 课程名称
     */
    @Column(name = "CourseName")
    private String coursename;

    /**
     * 课程分类 课程分类表的主键
     */
    @Column(name = "CourseClassify")
    private Integer courseclassify;

    /**
     * 上课地址 地址表的主键
     */
    @Column(name = "AddressId")
    private Integer addressid;

    /**
     * 开课时间
     */
    @DateTimeFormat(pattern = "YYYY-MM-DD hh:mm")
    @Column(name = "CourseStartTime")
    private Date coursestarttime;

    /**
     * 下课时间
     */
    @DateTimeFormat(pattern = "YYYY-MM-DD hh:mm")
    @Column(name = "CourseEndTime")
    private Date courseendtime;

    /**
     * 课时费用
     */
    @Column(name = "CourseMoney")
    private String coursemoney;

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
     * 教练 用户表教练id
     */
    @Column(name = "CoachId")
    private Integer coachid;

    /**
     * 课程强度
     */
    private String courseclassfyname;

    public String getCourseclassfyname() {
        return courseclassfyname;
    }

    public void setCourseclassfyname(String courseclassfyname) {
        this.courseclassfyname = courseclassfyname;
    }

    public String getAddname() {
        return addname;
    }

    public void setAddname(String addname) {
        this.addname = addname;
    }

    public String getCoachname() {
        return coachname;
    }

    public void setCoachname(String coachname) {
        this.coachname = coachname;
    }

    /**
     * 训练地址
     */
    private String addname;
    /**
     * 教练姓名
     */
    private String coachname;



    /**
     * 获取课程Id
     *
     * @return CId - 课程Id
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * 设置课程Id
     *
     * @param cid 课程Id
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * 获取课程名称
     *
     * @return CourseName - 课程名称
     */
    public String getCoursename() {
        return coursename;
    }

    /**
     * 设置课程名称
     *
     * @param coursename 课程名称
     */
    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    /**
     * 获取课程分类 课程分类表的主键
     *
     * @return CourseClassify - 课程分类 课程分类表的主键
     */
    public Integer getCourseclassify() {
        return courseclassify;
    }

    /**
     * 设置课程分类 课程分类表的主键
     *
     * @param courseclassify 课程分类 课程分类表的主键
     */
    public void setCourseclassify(Integer courseclassify) {
        this.courseclassify = courseclassify;
    }

    /**
     * 获取上课地址 地址表的主键
     *
     * @return AddressId - 上课地址 地址表的主键
     */
    public Integer getAddressid() {
        return addressid;
    }

    /**
     * 设置上课地址 地址表的主键
     *
     * @param addressid 上课地址 地址表的主键
     */
    public void setAddressid(Integer addressid) {
        this.addressid = addressid;
    }

    /**
     * 获取开课时间
     *
     * @return CourseStartTime - 开课时间
     */
    public Date getCoursestarttime() {
        return coursestarttime;
    }

    /**
     * 设置开课时间
     *
     * @param coursestarttime 开课时间
     */
    public void setCoursestarttime(Date coursestarttime) {
        this.coursestarttime = coursestarttime;
    }

    /**
     * 获取下课时间
     *
     * @return CourseEndTime - 下课时间
     */
    public Date getCourseendtime() {
        return courseendtime;
    }

    /**
     * 设置下课时间
     *
     * @param courseendtime 下课时间
     */
    public void setCourseendtime(Date courseendtime) {
        this.courseendtime = courseendtime;
    }

    /**
     * 获取课时费用
     *
     * @return CourseMoney - 课时费用
     */
    public String getCoursemoney() {
        return coursemoney;
    }

    /**
     * 设置课时费用
     *
     * @param coursemoney 课时费用
     */
    public void setCoursemoney(String coursemoney) {
        this.coursemoney = coursemoney;
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
     * 获取教练 用户表教练id
     *
     * @return CoachId - 教练 用户表教练id
     */
    public Integer getCoachid() {
        return coachid;
    }

    /**
     * 设置教练 用户表教练id
     *
     * @param coachid 教练 用户表教练id
     */
    public void setCoachid(Integer coachid) {
        this.coachid = coachid;
    }
}
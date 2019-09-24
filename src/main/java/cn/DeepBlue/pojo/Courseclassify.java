package cn.DeepBlue.pojo;

import java.util.Date;
import javax.persistence.*;

public class Courseclassify {
    /**
     * 课程分类表的id
     */
    @Id
    @Column(name = "CourseClassfyId")
    private Integer courseclassfyid;

    /**
     * 课程分类名称 
     */
    @Column(name = "CourseClassfyName")
    private String courseclassfyname;

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
     * 获取课程分类表的id
     *
     * @return CourseClassfyId - 课程分类表的id
     */
    public Integer getCourseclassfyid() {
        return courseclassfyid;
    }

    /**
     * 设置课程分类表的id
     *
     * @param courseclassfyid 课程分类表的id
     */
    public void setCourseclassfyid(Integer courseclassfyid) {
        this.courseclassfyid = courseclassfyid;
    }

    /**
     * 获取课程分类名称 
     *
     * @return CourseClassfyName - 课程分类名称 
     */
    public String getCourseclassfyname() {
        return courseclassfyname;
    }

    /**
     * 设置课程分类名称 
     *
     * @param courseclassfyname 课程分类名称 
     */
    public void setCourseclassfyname(String courseclassfyname) {
        this.courseclassfyname = courseclassfyname;
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
}
package cn.DeepBlue.pojo;

import java.util.Date;
import javax.persistence.*;

public class Subscribestatus {
    /**
     * 考勤表的id
     */
    @Id
    @Column(name = "StatusId")
    private Integer statusid;

    /**
     * 预约表的id 预约表的id
     */
    @Column(name = "StatusName")
    private String statusname;

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
     * 获取考勤表的id
     *
     * @return StatusId - 考勤表的id
     */
    public Integer getStatusid() {
        return statusid;
    }

    /**
     * 设置考勤表的id
     *
     * @param statusid 考勤表的id
     */
    public void setStatusid(Integer statusid) {
        this.statusid = statusid;
    }

    /**
     * 获取预约表的id 预约表的id
     *
     * @return StatusName - 预约表的id 预约表的id
     */
    public String getStatusname() {
        return statusname;
    }

    /**
     * 设置预约表的id 预约表的id
     *
     * @param statusname 预约表的id 预约表的id
     */
    public void setStatusname(String statusname) {
        this.statusname = statusname;
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
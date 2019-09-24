package cn.DeepBlue.pojo;

import java.util.Date;
import javax.persistence.*;

public class Attendance {
    /**
     * 考勤表的id
     */
    @Id
    @Column(name = "AttendanceId")
    private Integer attendanceid;

    /**
     * 预约表的id 预约表的id
     */
    @Column(name = "Subscribeid")
    private Integer subscribeid;

    /**
     * 是否按时到达 1代表准时到达2代表没有准时到达
     */
    @Column(name = "IsPunctuality")
    private String ispunctuality;

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
     * @return AttendanceId - 考勤表的id
     */
    public Integer getAttendanceid() {
        return attendanceid;
    }

    /**
     * 设置考勤表的id
     *
     * @param attendanceid 考勤表的id
     */
    public void setAttendanceid(Integer attendanceid) {
        this.attendanceid = attendanceid;
    }

    /**
     * 获取预约表的id 预约表的id
     *
     * @return Subscribeid - 预约表的id 预约表的id
     */
    public Integer getSubscribeid() {
        return subscribeid;
    }

    /**
     * 设置预约表的id 预约表的id
     *
     * @param subscribeid 预约表的id 预约表的id
     */
    public void setSubscribeid(Integer subscribeid) {
        this.subscribeid = subscribeid;
    }

    /**
     * 获取是否按时到达 1代表准时到达2代表没有准时到达
     *
     * @return IsPunctuality - 是否按时到达 1代表准时到达2代表没有准时到达
     */
    public String getIspunctuality() {
        return ispunctuality;
    }

    /**
     * 设置是否按时到达 1代表准时到达2代表没有准时到达
     *
     * @param ispunctuality 是否按时到达 1代表准时到达2代表没有准时到达
     */
    public void setIspunctuality(String ispunctuality) {
        this.ispunctuality = ispunctuality;
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
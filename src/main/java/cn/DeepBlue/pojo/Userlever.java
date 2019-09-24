package cn.DeepBlue.pojo;

import java.util.Date;
import javax.persistence.*;

public class Userlever {
    @Id
    @Column(name = "leverId")
    private Integer leverid;

    @Column(name = "leverName")
    private String levername;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_TIME")
    private Date createdTime;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_TIME")
    private Date updatedTime;

    /**
     * @return leverId
     */
    public Integer getLeverid() {
        return leverid;
    }

    /**
     * @param leverid
     */
    public void setLeverid(Integer leverid) {
        this.leverid = leverid;
    }

    /**
     * @return leverName
     */
    public String getLevername() {
        return levername;
    }

    /**
     * @param levername
     */
    public void setLevername(String levername) {
        this.levername = levername;
    }

    /**
     * @return CREATED_BY
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return CREATED_TIME
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * @return UPDATED_BY
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return UPDATED_TIME
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * @param updatedTime
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
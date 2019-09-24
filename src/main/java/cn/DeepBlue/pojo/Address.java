package cn.DeepBlue.pojo;

import java.util.Date;
import javax.persistence.*;

public class Address {
    /**
     * 地址id
     */
    @Id
    @Column(name = "AddID")
    private Integer addid;

    /**
     * 地址名称
     */
    @Column(name = "AddName")
    private String addname;

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
     * 获取地址id
     *
     * @return AddID - 地址id
     */
    public Integer getAddid() {
        return addid;
    }

    /**
     * 设置地址id
     *
     * @param addid 地址id
     */
    public void setAddid(Integer addid) {
        this.addid = addid;
    }

    /**
     * 获取地址名称
     *
     * @return AddName - 地址名称
     */
    public String getAddname() {
        return addname;
    }

    /**
     * 设置地址名称
     *
     * @param addname 地址名称
     */
    public void setAddname(String addname) {
        this.addname = addname;
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
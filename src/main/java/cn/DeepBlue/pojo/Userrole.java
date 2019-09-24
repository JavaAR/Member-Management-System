package cn.DeepBlue.pojo;

import java.util.Date;
import javax.persistence.*;

public class Userrole {
    /**
     * 角色表Id
     */
    @Id
    @Column(name = "Rid")
    private Integer rid;

    /**
     * 角色名称 角色名称（用户 教练 超级管理员）
     */
    @Column(name = "Rname")
    private String rname;

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
     * 获取角色表Id
     *
     * @return Rid - 角色表Id
     */
    public Integer getRid() {
        return rid;
    }

    /**
     * 设置角色表Id
     *
     * @param rid 角色表Id
     */
    public void setRid(Integer rid) {
        this.rid = rid;
    }

    /**
     * 获取角色名称 角色名称（用户 教练 超级管理员）
     *
     * @return Rname - 角色名称 角色名称（用户 教练 超级管理员）
     */
    public String getRname() {
        return rname;
    }

    /**
     * 设置角色名称 角色名称（用户 教练 超级管理员）
     *
     * @param rname 角色名称 角色名称（用户 教练 超级管理员）
     */
    public void setRname(String rname) {
        this.rname = rname;
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
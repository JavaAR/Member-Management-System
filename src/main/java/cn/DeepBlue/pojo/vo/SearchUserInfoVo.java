package cn.DeepBlue.pojo.vo;

public class SearchUserInfoVo {
    /**
     * 查询条件
     */
    private String userName;
    /**
     * 叶容量
     */
    private Integer pageSize;
    /**
     * 当前页
     */
    private Integer pageNo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}

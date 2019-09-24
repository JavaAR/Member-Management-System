package cn.DeepBlue.pojo.vo;

/**
 * 前端传给后端
 */
public class  ClassInfoVo {

    private String coursename;

    private Integer courseclassify;

    private Integer coachid;

    private Integer addressid;

    private String coursemoney;

    private String arrDates;

    public String getArrDates() {
        return arrDates;
    }

    public void setArrDates(String arrDates) {
        this.arrDates = arrDates;
    }

   private String startTime;

   private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public Integer getCourseclassify() {
        return courseclassify;
    }

    public void setCourseclassify(Integer courseclassify) {
        this.courseclassify = courseclassify;
    }

    public Integer getCoachid() {
        return coachid;
    }

    public void setCoachid(Integer coachid) {
        this.coachid = coachid;
    }

    public Integer getAddressid() {
        return addressid;
    }

    public void setAddressid(Integer addressid) {
        this.addressid = addressid;
    }

    public String getCoursemoney() {
        return coursemoney;
    }

    public void setCoursemoney(String coursemoney) {
        this.coursemoney = coursemoney;
    }
}

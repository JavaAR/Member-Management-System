layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'laypage','laydate'], function () {
    var form = layui.form(),
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        laydate = layui.laydate,
        $ = layui.jquery;
    var loc = location.href;
    var n1 = loc.length;
    var n2 = loc.indexOf("=");
    var sid = decodeURI(loc.substr(n2+1,n1-n2));

    //课程级别下拉框渲染
    var CourseClassify ="";
    $.ajax({
        url:getRootPath()+"/api/getAllCourseClassify",
        type:"get",
        headers:{
            'token':$.cookie("token")
        },
        success:function (res) {
            if(res.success=="true"){
                CourseClassify = res.data;
                form.render();
            }else{
                layer.msg(res.msg);
            }
        }
    })


    //教练下拉框渲染
    var  Course = "";
    $.ajax({
        url:getRootPath()+"/api/getallCourse",
        headers:{
            'token':$.cookie("token")
        },
        type:'get',
        success:function (res) {
            if(res.success=="true"){
                Course = res.data;
            }else{
                layer.msg(res.msg);
            }
        }
    })

    //地址下拉框渲染
    var address = "";
    $.ajax({
        url:getRootPath()+"/api/getAllAddress",
        headers:{
            'token':$.cookie("token")
        },
        type:"get",
        success:function (res) {
            if(res.success=="true"){
                address = res.data;
            }else{
                layer.msg(res.msg);
            }
        }
    });
    //状态渲染
    $.ajax({
        url:getRootPath()+"/api/getAllSubscribeidStatus",
        headers:{
            'token':$.cookie("token")
        },
        type:"get",
        success:function (res) {
            if(res.success=="true"){
                var option = '<option value="">请选择预约状态</option>';
                for (var i = 0; i < res.data.length;i++){
                    option+="<option value='"+res.data[i].statusid+"'>"+ res.data[i].statusname+"</option>";
                }
                $("#status").html("");
                $("#status").append(option);
                form.render();
            }else{
                layer.msg(res.msg);
            }
        }
    });
    //获取当前预约所有信息并且下拉框选中
    $.ajax({
        url:getRootPath()+'/api/getsubscribeInfo',
        data:{
            "subscribeid":sid
        },
        type:'get',
        headers:{
            'token':$.cookie("token")
        },
        success:function (res) {
            if(res.success=="true"){
                $(".className").val(res.data.coursename);
                for(var i = 0;i<CourseClassify.length;i++){//课程等级渲染
                    if(CourseClassify[i].courseclassfyid =res.data.courseclassify){
                        $("#selectClasslever").val(CourseClassify[i].courseclassfyname)
                    }
                }
                for(var i = 0;i<Course.length;i++){
                    if(Course[i].uid =res.data.ucoachid){
                        $("#selectcoach").val(Course[i].username)
                    }
                }
                for(var i = 0;i<address.length;i++){
                    if(address[i].addid =res.data.addressid){
                        $("#selectaddress").val(address[i].addname)
                    }
                }
                var subscribeiddate = formatDate(res.data.subscribeiddate);
                $("#subscribeTime").val(subscribeiddate);//预约时间渲染
                var coursestarttime = formatDateWithTime(res.data.coursestarttime)  //开课时间渲染
                $("#startTime").val(coursestarttime);
                var courseendtime = formatDateWithTime(res.data.courseendtime)   //结束时间渲染；
                $("#endTime").val(courseendtime);
                var status = 'dd[lay-value=' + res.data.status + ']';//状态渲染
                $('#status').siblings("div.layui-form-select").find('dl').find(status).click();
            }else{
            }
        }
    })

    //提交按钮
    form.on("submit(changeSubscribe)", function (data) {
        $.ajax({
            url:getRootPath()+"/api/punchCard",
            data:{
                "sid":sid,
                "statusid":$("#status").val()
            },
            type:"get",
            headers:{
                "token":$.cookie("token")
            },
            success:function (res) {
                if(res.success=="true"){
                    layer.msg("修改成功");
                    window.location.reload();
                }else{
                    layer.msg(res.msg);
                }
            }
        })
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })
    //格式话到具体时间
    function formatDateWithTime(date) {
        var dateStr = new Date(date);
        var myyear = dateStr.getFullYear();
        var mymonth = dateStr.getMonth() + 1;
        var myweekday = dateStr.getDate();
        var myHours = dateStr.getHours();
        var myMill = dateStr.getMinutes();
         if(myMill < 10){
             myMill = "0" + myMill;
         }
         if(myMill = 0){
             myMill = "0" + myMill;
         }
        return (myyear + "-" + mymonth + "-" + myweekday+" "+myHours+":"+myMill);
    }
    //格式化日期
    function formatDate(date) {
        var dateStr = new Date(date);
        var myyear = dateStr.getFullYear();
        var mymonth = dateStr.getMonth() + 1;
        var myweekday = dateStr.getDate();
        /* if(mymonth < 10){
             mymonth = "0" + mymonth;
         }
         if(myweekday < 10){
             myweekday = "0" + myweekday;
         }*/
        return (myyear + "-" + mymonth + "-" + myweekday);
    }

    //获取项目根目录
    function getRootPath() {
        //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
        var curWwwPath = window.document.location.href;
        //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
        var pathName = window.document.location.pathname;
        var pos = curWwwPath.indexOf(pathName);
        //获取主机地址，如： http://localhost:8083
        var localhostPaht = curWwwPath.substring(0, pos);
        //获取带"/"的项目名，如：/uimcardprj
        var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        return (localhostPaht+projectName);
    }
});
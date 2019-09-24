var $form;
var form;
var $;
var laydate;
layui.config({
    base: "../../js/"
}).use(['form', 'layer', 'upload', 'laydate'], function () {
    form = layui.form();
    var layer = parent.layer === undefined ? layui.layer : parent.layer;
    $ = layui.jquery;
    $form = $('form');
    laydate = layui.laydate;


    layer.msg("如果数据未加载，请点击页面操作，刷新当前");

    var token = $.cookie("token");
    var loc = location.href;
    var n1 = loc.length;
    var n2 = loc.indexOf("=");
    var cid = decodeURI(loc.substr(n2+1,n1-n2));

    //开始时间设置
    var starttime;
    var startTime = {
        elem:$("#startTime")[0],
        event:'click',
        format:'YYYY-MM-DD hh:mm',
        istime:true,
        min:laydate.now(),
        max:"2099-12-31 23:59:59",
        fixed: false, //是否固定在可视区域
        zIndex: 99999999, //css z-index
        choose: function(dates){ //选择好日期的回调
            starttime = dates;
        }
    };
    //结束时间设置
    var endtime;
    var endTime = {
        elem:$("#endTime")[0],
        event:'click',
        format:'YYYY-MM-DD hh:mm',
        istime:true,
        min:laydate.now(),
        max:"2099-12-31 23:59:59",
        fixed: false, //是否固定在可视区域
        zIndex: 99999999, //css z-index
        choose: function(dates){ //选择好日期的回调
            endtime = dates;
        }
    };
    //课程强度下拉框渲染
    $.ajax({
        url:getRootPath()+"/api/getAllCourseClassify",
        type:"get",
        headers:{
            'token':token
        },
        success:function (res) {
            if(res.success=="true"){
                var option = '<option value="">请选择</option>';
                for(var i = 0;i<res.data.length;i++){
                    option+="<option value='"+res.data[i].courseclassfyid+"'>"+ res.data[i].courseclassfyname+"</option>";
                }
                $("#selectclassify").html("");
                $("#selectclassify").append(option);
                form.render();
            }else{
                layer.msg(res.msg);
            }
        }
    })
    //教练下拉框渲染
    $.ajax({
        url:getRootPath()+"/api/getallCourse",
        headers:{
            'token':token
        },
        type:'get',
        success:function (res) {
            if(res.success=="true"){
                var option = '<option value="">请选择</option>';
                for (var i = 0; i < res.data.length;i++){
                    option+="<option value='"+res.data[i].uid+"'>"+ res.data[i].username+"</option>";
                }
                $("#selectcoach").html("");
                $("#selectcoach").append(option);
                form.render();
            }else{
                layer.msg(res.msg);
            }
        }
    })
    //地址下拉框渲染
    $.ajax({
        url:getRootPath()+"/api/getAllAddress",
        headers:{
            'token':token
        },
        type:"get",
        success:function (res) {
            if(res.success=="true"){
                var option = '<option value="">请选择</option>';
                for (var i = 0; i < res.data.length;i++){
                    option+="<option value='"+res.data[i].addid+"'>"+ res.data[i].addname+"</option>";
                }
                $("#selectaddress").html("");
                $("#selectaddress").append(option);
                form.render();
            }else{
                layer.msg(res.msg);
            }
        }
    });
    //验证开始日期 判断结束日期与开始日期
    form.verify({
        startTimeSame : function(value,item){
             var startTime = $("#startTime").val();
             var starttime = new Date(startTime).getTime();
            var endtime = new Date(value).getTime();
            if (starttime==endtime) {
                return "开始日期与结束日期不能一致";
            }else if(starttime>endtime){
                return"结束日期不能小于开始日期";
            }
        }
    });
    //提交课程
    form.on("submit(changeClass)", function (data) {
        var index = layer.msg('提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        //获取输入框数据
        var coursename = $(".coursename").val();//课程名
        var classify = $("#selectclassify").val();//课程分类
        var coach = $("#selectcoach").val();//教练选择
        var address = $("#selectaddress").val();//地址
        var classMoney =$(".classmoney").val();//课程费用
        var startTime =$("#startTime").val();//开始时间
        var endTime = $("#endTime").val();//结束时间
        strStart = startTime.replace(/-/g, '/');//格式化数据
        var startdate = new Date(strStart);
        strEnd = endTime.replace(/-/g,'/');
        var endtdate = new Date(strEnd);
        //发送请求
        $.ajax({
            url:getRootPath()+'/api/updateClassInfo',
            headers: {
                "token": token
            },
            type: 'post',
            contentType: "application/json",
            data: JSON.stringify({
                "cid":cid,
                'coursename': coursename,
                'courseclassify': classify,
                'addressid': address,
                'coursestarttime': startdate,
                'courseendtime': endtdate,
                'coursemoney': classMoney,
                'coachid': coach
            }),
            success: function (res) {
                if (res.success == "true") {
                    setTimeout(function () {
                        layer.close(index);
                        layer.msg("提交成功！");
                    }, 2000);
                } else {
                    layer.msg(res.msg);
                }
            }
        })
        for (key in data.field) {
            if (key.indexOf("like") != -1) {
                userInfoHtml[key] = "on";
            }
        }
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })
    //渲染所有的信息
    $.ajax({
        url: getRootPath()+'/api/getClassInfo/' + cid,
        headers:{
            'token':token
        },
        type: 'get',
        success: function (res) {
            if (res.success == "true") {
                var coursename = res.data.coursename;//课程名
                var courseclassify = res.data.courseclassify;//课程强度
                var addressid = res.data.addressid;//地址
                var coursestarttime = res.data.coursestarttime;//开始时间
                var courseendtime = res.data.courseendtime;//结束时间
                var coursemoney = res.data.coursemoney;//费用
                var coachid = res.data.coachid;//教练
                //格式化起始时间日期
                dat = new Date(coursestarttime);
                var stryear = dat.getFullYear();
                var strmonth = dat.getMonth() + 1;
                var strdate = dat.getDate();
                var strhouse = dat.getHours();
                var strMill = dat.getMinutes();
                //格式化结束时间日期
                dat = new Date(courseendtime);
                var endyear = dat.getFullYear();
                var endmonth = dat.getMonth() + 1;
                var enddate = dat.getDate();
                var endhouse = dat.getHours();
                var endMill = dat.getMinutes();
                //显示到输入框
                $(".coursename").val(coursename);//课程名
                $(".classmoney").val(coursemoney); //课程费用
                $("#startTime").val([[stryear, strmonth, strdate].join('-')+" "+[strhouse,strMill].join(":")]);//开始时间
                $("#endTime").val([endyear, endmonth, enddate].join('-')+" "+[endhouse,endMill].join(":"));//结束时间
               /* $('dd[lay-value=' + courseclassify + ']').attr('class','layui-this');*/
                var classifyselect = 'dd[lay-value=' + courseclassify + ']';//选中课程强度
                $('#selectclassify').siblings("div.layui-form-select").find('dl').find(classifyselect).click();
                var addressSelect ='dd[lay-value=' + addressid + ']';//地址选中
                $('#selectaddress').siblings("div.layui-form-select").find('dl').find(addressSelect).click();
                var coachSelect = 'dd[lay-value= '+coachid+']';
                $('#selectcoach').siblings("div.layui-form-select").find('dl').find(coachSelect).click();
               form.render('checkbox');
               form.render('select');
            } else {
                layer.msg(res.msg);
            }
        }
    })
    //开始时间点击事件
    $("#startTime").click(function () {
        laydate(startTime);
    });
    //结束日期点击事件
    $("#endTime").click(function () {
        laydate(endTime);
    });
})
//获取项目根路径
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
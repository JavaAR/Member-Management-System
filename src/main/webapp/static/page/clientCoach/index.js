layui.use(['element', 'laydate', 'layer', 'form', 'table','util'], function () {
    var element = layui.element,
        laydate = layui.laydate,
        layer = layui.layer,
        form = layui.form,
        table = layui.table,
        $ = layui.jquery,
        util = layui.util;

    /**
     * 渲染日历组件并获取当前教练的信息
     */
    var currCoachInfo = "";
    laydate.render({
        elem: '#laydateArea',
        show: true,
        theme: 'myDateCss',
        showBottom: false,
        position: 'static',
        calendar: true,
        ready: function (data) {//日历组件初始化之后操作
            var month = data.month;//显示在日期显示区域 月
            var date = data.date;//日
            var week = getYearWeek(date);//周数
            //格式化日期 将初始日期显示到日期显示栏中
            $("#selectDate").html(month + "月" + date + "日" + "周" + week);
            $.ajax({
                url: getRootPath() + "/api/getUserInfo",
                headers: {
                    'token': $.cookie("token")
                },
                type: 'get',
                success: function (res) {
                    if (res.success == "true") {
                        currCoachInfo = res.data;
                        layer.msg("欢迎您："+currCoachInfo.username);
                        //获取当前教练的课程
                        getCurrCoachClass(currCoachInfo.uid,LastMonthStartDate, NextMonthEndDate);
                        //过时自动打卡
                        getCurrCoachSubscribe(currCoachInfo.uid);
                        var currTime=new Date().getTime();
                        for(var i = 0;i<CurrCoachSubscribeClass.length;i++){
                            if(CurrCoachSubscribeClass[i].courseendtime<currTime){
                                if(CurrCoachSubscribeClass[i].status==1){
                                    automationPunchCard(CurrCoachSubscribeClass[i]);
                                }
                            }
                        }
                    } else if (res.success == "false") {
                        if (res.errorCode == "30002") {
                            layer.msg(res.msg + "，即将跳转到登录页面");
                            setTimeout(function () {
                                location.href = getRootPath() + "/static/page/clientCoach/login.html";
                            }, 2000);
                        } else {
                            layui.msg(res.msg);
                        }
                    }
                }
            });

        },
        change: function (value, date, endDate) { //日历组件改变完成之后执行的操作
            //移除本月的css样式
            $('#distance').html('');
            $("#usersubscribeclassContent").html("");
            $("#classNameShow").html("");
            $("#laydateArea .layui-laydate-content td").removeAttr("style");
            //重新加载日历组件
            $('#laydateArea').change();
            var month = date.month;//显示在日期显示区域 月
            var date = date.date;//日
            var week = getYearWeek(value);        //周数
            $("#selectDate").html(month + "月" + date + "日" + "周" + week);
            getCurrCoachClass(currCoachInfo.uid, LastMonthStartDate, NextMonthEndDate);//重新渲染日历
        },
        done: function (value, date, endDate) {//选择完日期之后进行的操作
            $("#classTimeShow li").remove()

            var selectDate = formatDate(value);
            var dateArr = [];
            for (var i = 0; i < classInfo.length; i++) {
                if (selectDate == formatDate(classInfo[i].coursestarttime)) {
                    dateArr.push(classInfo[i]);
                    var currTime = new Date().getTime();
                    var end = classInfo[i].courseendtime;
                    var dateStr = new Date(classInfo[i].courseendtime);
                    var myyear = dateStr.getFullYear();
                    var mymonth = dateStr.getMonth() + 1;
                    var myweekday = dateStr.getDate();
                    var myHours = dateStr.getHours();
                    var myMill= dateStr.getMinutes();
                    var r = end-currTime;
                    var str;
                    if(r<600000 && r>0){//10分钟倒计时
                        util.countdown(end, currTime, function(date, serverTime, timer){
                           str = date[0] + '天' + date[1] + '时' +  date[2] + '分' + date[3] + '秒';
                        })
                        layer.confirm('距离'+mymonth+'月'+myweekday+'日'+' '+myHours+':'+myMill+'的'+classInfo[i].coursename+'课程结束还有:'+ str +',未打卡的学员将被记为迟到',{
                            btn:['我知道了']
                        },function (index, layero) {
                            layer.close(index);
                        })
                    }
                }
            }
            renderer(dateArr);
        }
    });
    /**
     * 选项卡切换
     */
    element.on('tab(demo)', function (data) {
        if (data.index == 0) {


        } else if (data.index == 1) {
            getCurrCoachSubscribe(currCoachInfo.uid);
            $("#currUserclassContent").html(renderCoachAllSubscribe(CurrCoachSubscribeClass));
        }
    });
    /**
     * 日期处理
     * @type {Date}
     */
    var now = new Date();                    //当前日期
    var nowDayOfWeek = now.getDay();         //今天本周的第几天
    var nowDay = now.getDate();              //当前日
    var nowMonth = now.getMonth();           //当前月
    var nowYear = now.getFullYear();             //当前年
    nowYear += (nowYear < 2000) ? 1900 : 0;  //
    var lastMonthDate = new Date();  //上月日期
    lastMonthDate.setDate(1);
    lastMonthDate.setMonth(lastMonthDate.getMonth() - 1);
    var lastYear = lastMonthDate.getFullYear();
    var lastMonth = lastMonthDate.getMonth();
    var nextMonthDate = new Date();   //下月日期
    nextMonthDate.setDate(1);
    nextMonthDate.setMonth(nextMonthDate.getMonth() + 1);
    var nextMonth = nextMonthDate.getMonth();
    //获得某月的天数
    function getMonthDays(myMonth) {
        var monthStartDate = new Date(nowYear, myMonth, 1);
        var monthEndDate = new Date(nowYear, myMonth + 1, 1);
        var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
        return days;
    }
    //获得本季度的开始月份
    function getQuarterStartMonth() {
        var quarterStartMonth = 0;
        if (nowMonth < 3) {
            quarterStartMonth = 0;
        }
        if (2 < 6) {
            quarterStartMonth = 3;
        }
        if (5 < 9) {
            quarterStartMonth = 6;
        }
        if (nowMonth > 8) {
            quarterStartMonth = 9;
        }
        return quarterStartMonth;
    }
    //获取上个月的开始时间
    var getLastMonthStartDate = new Date(nowYear, lastMonth, 1);
    var LastMonthStartDate = formatDate(getLastMonthStartDate);
    //获取下个月的结束时间
    var getNextMonthEndDate = new Date(nowYear, nextMonth, getMonthDays(nextMonth));
    var NextMonthEndDate = formatDate(getNextMonthEndDate);
    /**
     * 渲染数据当天课程时间段按钮
     */
    function renderer(data) {
        if(data.length>0){
            for(var i = 0;i<data.length;i++){
                //开始时间
                var s =data[i].coursestarttime;
                var e =data[i].courseendtime;
                var startTime = new Date(data[i].coursestarttime);
                var sthours = startTime.getHours();
                var starMill =startTime.getMinutes();
                if(starMill>0 && starMill<10){
                    starMill = "0"+  starMill;
                }else if(starMill==0){
                    starMill = "0"+  starMill;
                }
                var stMonth = startTime.getMonth()+1;
                var stDate=  startTime.getDate();
                //结束时间
                var endtTime = new Date(data[i].courseendtime);
                var endHours = endtTime.getHours();
                var endMill = endtTime.getMinutes();
                if(endMill>0 && endMill<10){
                    endMill = "0"+  endMill;
                }else if(endMill==0 ){
                    endMill = "0"+  endMill;
                }
                var cid = data[i].cid;
                $("#classTimeShow").append(
                    ' <li><button  id='+cid +' startDate='+s+ ' endDate='+ e +' type="button" class="layui-btn layui-btn-radius layui-btn-primary" style="border-color: #3a74d3">'+[sthours, starMill].join(':')+"~"+[endHours, endMill].join(':')+'</button></li>'
                )
            }
        }else{
            layer.msg("暂无数据");

        }
    };
    /**
     * 时间段点击事件 获取当前课程预约的学员信息以及考勤信息并渲染
     */
    $(document).on('click','#classTimeShow li button',function () {
        $("#usersubscribeclassContent").html("")
        $('#classTimeShow li button').css({"background-color":"","color":"black"});
        /*$(this).css("background-color:blue;color:white");*/
        $(this).css({"background-color":"#3a74d3","color":"white"})
        //获取当前元素的文本值(获取时间段，组织参数)
        var Timescope = $(this).html();
        var start = $(this).attr("startDate");
        var end = $(this).attr("endDate");
        var arr =  subTimescope(Timescope);
        var startT =start+":"+ arr[0];
        var endT = end + ":" + arr[1];
        //获取课程id
        var cid = $(this).attr("id");
        $.ajax({
            url:getRootPath()+"/api/getSubscribeStudent",
            data:{
                "cid":cid,
            },
            type:"post",
            async: false,
            headers:{
                "token":$.cookie("token")
            },
            success:function (res) {
                if(res.success=="true"){
                    var dataArr = res.data;
                    var SubscribeStudent =[];
                    for(var i = 0;i<dataArr.length;i++){
                        SubscribeStudent.push(dataArr[i]);
                    }
                    $("#usersubscribeclassContent").html(rendererSubscribeData(SubscribeStudent))
                }else{
                    layer.msg(res.msg);
                }
            }
        })
    });
    /**
     * 获取当前教练的课程
     */
    var classInfo = "";
    function getCurrCoachClass(uid, startDate, endDate) {
        $.ajax({
            url: getRootPath() + "/api/getCurrCoachClass",
            type: "post",
            data: {
                'uid': uid,
                'startDate': startDate,
                'endDate': endDate
            },
            headers: {
                'token': $.cookie('token')
            },
            success: function (res) {
                if (res.success == "true") {
                    classInfo = res.data;
                    //渲染日历
                    for (var i = 0; i < res.data.length; i++) {
                        $("#laydateArea .layui-laydate-content td[lay-ymd=" + formatDate(res.data[i].coursestarttime) + "]").css("background", "#ffb632");
                    }
                } else {
                    layer.msg(res.msg);
                }
            }
        })
    };
    /**
     * 签到页面渲染预约数据
     * @param data
     */
    function rendererSubscribeData(data) {
        $("#classNameShow").html(data[0].coursename);
        var dataHtml = "";
        for(var i = 0;i<data.length;i++){
            dataHtml+='<tr>';
            //预约状态小圆点
            switch (data[i].status) {
                case 1:{
                    dataHtml+='<td><span class="layui-badge-dot" style="background-color: #ed8b22"></span></td>';
                    break;
                }
                case 2:{
                    dataHtml+='<td><span class="layui-badge-dot" style="background-color: #1da5f3"></span></td>';
                    break;
                }
                case 3:{
                    dataHtml+='<td><span class="layui-badge-dot" style="background-color: #8D8D8D"></span></td>';
                    break;
                }
            };
            dataHtml+='<td>'+ data[i].studentname+'</td>'
                +'<td>'+data[i].studentPhone +'</td>';
            //预约状态按钮
            switch (data[i].status) {
                case 1:{
                    dataHtml+='<td><button id='+data[i].subscribeid +' status=1 type="button" startDate='+data[i].coursestarttime+' endDate='+data[i].courseendtime+' class="layui-btn layui-btn-radius layui-btn-warm">已预约</button></td>';
                    break;
                }
                case 2:{
                    dataHtml+='<td><button id='+data[i].subscribeid +' status=2 type="button" startDate='+data[i].coursestarttime+' endDate='+data[i].courseendtime+' class="layui-btn layui-btn-radius layui-btn-normal">已签到</button></td>';
                    break;
                }
                case 3:{
                    dataHtml+='<td><button id='+data[i].subscribeid +' status=3 type="button" startDate='+data[i].coursestarttime+' endDate='+data[i].courseendtime+' class="layui-btn layui-btn-radius layui-btn-disabled">未到</button></td>';
                    break;
                }
            };
            dataHtml+='</tr>';
        }
        return dataHtml;
    }
    /**
     * 表格考勤按钮绑定点击事件
     */
    $(document).on('click','#usersubscribeclassContent tr td button',function () {
        //获取当前预约状态
        var status = $(this).attr("status");
        var sid = $(this).attr("id");
        var startDate = $(this).attr("startDate");//开课时间
        var endDate = $(this).attr("endDate");//结束时间
        var currDate =  new Date().getTime();//当前时间
        var th =$(this);
        if(status == 1){
            if (startDate<currDate) {
                if (currDate<endDate) {
                    layer.confirm('该学员是否到达训练场地？', {
                        btn: ['已到', '未到'],
                    }, function (index, layero) {
                        $.ajax({//打卡操作
                            url: getRootPath() + "/api/punchCard",
                            data: {
                                "sid": sid,
                                "statusid": 2
                            },
                            type: "get",
                            headers: {
                                token: $.cookie("token")
                            },
                            success: function (res) {
                                if (res.success == "true") {
                                    layer.msg("打卡成功");
                                    th.html("已签到");
                                    th.attr("class", "layui-btn layui-btn-radius layui-btn-normal");
                                    th.attr("status", "2");
                                } else {
                                    layer.msg(res.msg);
                                }
                            }
                        })
                    }, function (index) {
                        layer.close(index);
                    })
                } else {
                    layer.msg("课程已结束，不允打卡");
                }
            } else {
                layer.msg("课程还没开始，不允许打卡");
            }
        }else if(status==2){
            if (endDate>currDate) {
                layer.confirm('是否取消该学员的签到？', {
                    btn: ['确认', '取消'],
                }, function (index, layero) {
                    $.ajax({//打卡操作
                        url: getRootPath() + "/api/punchCard",
                        data: {
                            "sid": sid,
                            "statusid": 1
                        },
                        type: "get",
                        headers: {
                            token: $.cookie("token")
                        },
                        success: function (res) {
                            if (res.success == "true") {
                                layer.msg("取消成功");
                                th.html("已预约");
                                th.attr("class", "layui-btn layui-btn-radius layui-btn-warm");
                                th.attr("status", "1");
                            } else {
                                layer.msg(res.msg);
                            }
                        }
                    })
                }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.msg("很抱歉，当前课程已经结束，请联系管理员")
            }
        }

    });

    /**
     *  获取当前教练的所有预约课程
     */
    var CurrCoachSubscribeClass='';
    function getCurrCoachSubscribe(Coachid) {
        $.ajax({
            url:getRootPath()+"/api/getCurrCoachSubscribeClass",
            type:'get',
            data:{
                "coachId":Coachid
            },
            async:false,
            headers:{
                "token":$.cookie("token")
            },
            success:function(res){
                if(res.success=="true"){
                    CurrCoachSubscribeClass=res.data;
                }else{
                    if(res.errorCode == "30002"){
                        layer.msg(res.msg + "，即将跳转到登录页面");
                        setTimeout(function () {
                            location.href = getRootPath() + "/static/page/clientCoach/login.html";
                        }, 2000);
                    }else{
                        layer.msg(res.msg);
                    }
                }
            }
        })
    }


    /**
     * 我的预约页面渲染数据
     * @param rcas
     */
    function renderCoachAllSubscribe(rcas) {
        var dataHtml = "";
        if(rcas.length>0){
            var dataArr =[];
            for(var i = 0;i<rcas.length;i++){
                dataArr.push(rcas[i]);
                var dateStr = new Date(dataArr[i].coursestarttime);
                var myyear = dateStr.getFullYear();
                var mymonth = dateStr.getMonth() + 1;
                var myweekday = dateStr.getDate();
                var myHours = dateStr.getHours();
                var myMill= dateStr.getMinutes();
                var stDate = myyear+"-"+mymonth+"-"+myweekday+" "+myHours+":"+myMill;
                dataHtml+='<tr>'
                + '<td>'+ stDate+'</td>'
                + '<td>'+ dataArr[i].coursename+'</td>'
                + '<td>'+ dataArr[i].studentname+'</td>'
                + '<td>'+ dataArr[i].studentPhone+'</td>'
                + '</tr>';
            }
        }else{
            dataHtml += '<tr><td colspan="8%">暂无预约</td></tr>';
        }
        return dataHtml;
    }

    /**
     * 课程结束打卡操作
     */
    function automationPunchCard(data) {
        $.ajax({
            url:getRootPath()+"/api/classOverPunchCard",
            type:'get',
            headers:{
                "token":$.cookie("token")
            },
            data:{
                'SubscribeId':data.subscribeid
            },
            success:function (res) {
               if(res.success=="true"){
                   layer.msg("自动打卡已生效");
               }else{
                   layer.msg("自动打卡失败");
               }
            }
        })
    }

    //退出按钮
    $("#loginout").click(function () {
        layer.open({
            content: '确认退出吗？',
            btn: ['确认', '取消'],
            yes: function (index, layero) {
                $.ajax({
                    url: getRootPath() + "/api/userLoginOut",
                    type: 'get',
                    headers: {
                        'token': $.cookie("token")
                    },
                    success: function (res) {
                        if (res.success == "true") {
                            $.removeCookie("token");
                            $.removeCookie("genTime");
                            $.removeCookie("expTime");
                            layer.msg("谢谢使用！");
                            setTimeout(function () {
                                location.href = getRootPath() + "/static/page/clientCoach/login.html";
                            }, 2000);
                        } else {
                            layer.msg("退出失败，请重试");
                        }
                    }
                })
            },
            btn2: function (index, layero) {
                layer.close(index);
            }
        })
    })
});
/*--------------------------------------------------------------------------*/
/**
 * 截取字符串
 */
function subTimescope(a) {
  var arr =  a.split("~");
    return arr;
}
/**
 *获取指定日期的周数第几周
 */
function getYearWeek(param) {
    var a = new Array("日", "一", "二", "三", "四", "五", "六");
    var date = new Date(param);
    var week = date.getDay();
    var str = a[week];
    return str;
};
/**
 * 格式化日期
 */
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
};


/**
 * 获取项目根路径
 */
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
    return (localhostPaht + projectName);
}

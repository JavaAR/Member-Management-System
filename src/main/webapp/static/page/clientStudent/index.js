layui.use(['element', 'laydate', 'layer', 'form', 'table'], function () {
    var element = layui.element,
        laydate = layui.laydate,
        layer = layui.layer,
        form = layui.form,
        table = layui.table,
        $ = layui.jquery;
    /**
     * 获取用户信息
     */
    var userInfo = "";
    $.ajax({
        url: getRootPath() + "/api/getUserInfo",
        headers: {
            'token': $.cookie("token")
        },
        type: 'get',
        success: function (res) {
            if (res.success == "true") {
                userInfo = res.data;
            } else if (res.success == "false") {
                if (res.errorCode == "30002") {
                    layer.msg(res.msg + "，即将跳转到登录页面");
                    setTimeout(function () {
                        location.href = getRootPath() + "/static/page/clientStudent/login.html";
                    }, 2000);
                } else {
                    layui.msg(res.msg);
                }
            }
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
    var getLastMonthStartDate = formatDate(getLastMonthStartDate);
    //获取下个月的结束时间
    var getNextMonthEndDate = new Date(nowYear, nextMonth, getMonthDays(nextMonth));
    var getNextMonthEndDate = formatDate(getNextMonthEndDate);
    //获取上个月开始至下个月结束所有日期
    var timeQuantumDate = getAll(getLastMonthStartDate, getNextMonthEndDate);
    //显示信息
    layer.msg("本程序只显示" + (lastMonth + 1) + "月，" + (nowMonth + 1) + "月，" + (nextMonth + 1) + "月的课程,如有其他需求请联系深蓝探索");
    //转换项
    element.on('tab(demo)', function (data) {
        if (data.index == 1) {
            getCurrUserClass(userInfo.uid);
        }
    });
    element.init();
    //预约页面日历
    var dateIns = laydate.render({
        elem: '#laydateArae'
        , show: true
        , theme: "myDateCss"
        , showBottom: false
        , position: 'static'
        , calendar: true //是否开启公历重要节日
        , ready: function (data) {//日历组件初始化之后执行的操作
            var month = data.month;//显示在日期显示区域 月
            var date = data.date;//日
            var week = getYearWeek(date);//周数
            //格式化日期 将初始日期显示到日期显示栏中
            $("#selectDate").html(month + "月" + date + "日" + "周" + week);
            getAllClassInfo(getLastMonthStartDate, getNextMonthEndDate);
        }
        , done: function (value, date, endDate) {//选择日期之后的操作
            //得到选中的日期
            var year = date.year;
            var month = date.month;// 月
            var date = date.date;//日
            var selectDate = year + "-" + month + "-" + date;
            var classes = [];
            //判断得到的日期是否在所查询的课程中
            for (var i = 0; i < classInfo.length; i++) {
                if (selectDate == formatDate(classInfo[i].coursestarttime)) {
                    classes.push(classInfo[i]);
                }
            }
            //渲染到表格
            $("#classContent").html(renderer(classes, userInfo));
        }
        , change: function (value, date, endDate) { //日历组件改变完成之后执行的操作
            //移除本月的css样式
            $("#laydateArae .layui-laydate-content td").removeAttr("style");
            //重新加载日历组件
            $('#laydateArae').change();
            var month = date.month;//显示在日期显示区域 月
            var date = date.date;//日
            var week = getYearWeek(value);        //周数
            $("#selectDate").html(month + "月" + date + "日" + "周" + week);
            getAllClassInfo(getLastMonthStartDate, getNextMonthEndDate);//重新渲染日历
        }
    });


//预约操作
    /*function subscribeClass(UserInfo,ClassInfo) {
     var startTime = ClassInfo.coursestarttime;
        var date = new Date(startTime);
        var sthours = date.getHours();
        var stMill = date.getMinutes();
        $.ajax({
            url:getRootPath()+"/api/subscribeClass",
            headers:{
                'token':$.cookie('token')
            },
            type:"post",
            data:{
              "courseid":ClassInfo.cid,
              "userid":UserInfo.uid,
              "ucoachid":ClassInfo.coachid
            },
            success:function (res) {
                if(res.success=="true"){
                    /!*$("#laydateArae .layui-laydate-content td[lay-ymd=" + formatDate(startTime) + "]").css("background", "black");*!/
                   /!* $("#laydateArae").change();*!/
                    layer.open({
                        title:"预约成功！",
                        content:"请于"+ formatDate(startTime)+" "+[sthours,stMill].join(":")+"前到达"+ ClassInfo.addname,
                    });
                }else{
                    layer.msg(res.msg);
                }
            }
        })
      }*/
    //考勤页面选择框日历
    //初始化两个日历并规定时间范围
    //考勤开始日历选择
    var startDate = laydate.render({
        elem: '#startDate', //指定元素
        format: 'yyyy-MM-dd',
        trigger: 'click',
        showBottom: false,
        done: function (value, date) {
            endDate.config.min = {
                year: date.year,
                month: date.month - 1,
                date: date.date
            }
        }
    });
    //结束日期日历选择
    var endDate = laydate.render({
        elem: '#endDate', //指定元素
        format: 'yyyy-MM-dd',
        trigger: 'click',
        showBottom: false,
        done: function (value, date) {
            startDate.config.max = {
                year: date.year,
                month: date.month - 1,
                date: date.date
            }
        }
    });
    //提交按钮
    form.on("submit(selectBut)", function (data) {
        var strDate = $("#startDate").val();
        var enDate = $("#endDate").val();
        getCurrUserClass(userInfo.uid, strDate, enDate);
    });
    /**
     * 查看个人预约所有的预约课程
     */
    var currUserClass = "";

    function getCurrUserClass(uid, startDate, endDate) {
        $.ajax({
            url: getRootPath() + "/api/getSubscribeByUser",
            type: "post",
            data: {
                "uid": uid,
                "startDate": startDate,
                "endDate": endDate
            },
            async: false,
            headers: {
                token: $.cookie("token")
            },
            success: function (res) {
                if (res.success == "true") {
                    $("#currUserclassContent").html(renderercurrUserClass(res.data));
                } else {
                    layer.msg(res.msg);
                }
            }
        });
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
                                location.href = getRootPath() + "/static/page/clientStudent/login.html";
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

    /**
     * 渲染预约页面表格数据
     * @param classes
     */
    function renderer(classes, userInfo) {
        var dataHtml = '';
        if (classes.length != 0) {
            for (var i = 0; i < classes.length; i++) {
                subscribe(classes[i].cid, userInfo.uid);
                //格式化课程开始时间 和 格式化课程结束日期
                var startTime = new Date(classes[i].coursestarttime);
                var sthours = startTime.getHours();
                var stMill = startTime.getMinutes();
                var endTime = new Date(classes[i].courseendtime);
                var enhours = endTime.getHours();
                var enMill = endTime.getMinutes();
                //定义课程等级颜色
                dataHtml += '<tr>';
                switch (classes[i].courseclassify) {
                    case 1: {
                        dataHtml += '<td><span class="layui-badge-dot" style="background-color: #ed8b22"></span></td>';
                        break;
                    }
                    case 2: {
                        dataHtml += '<td><span class="layui-badge-dot" style="background-color: #1da5f3"></span></td>';
                        break;
                    }
                    case 3: {
                        dataHtml += '<td><span class="layui-badge-dot" style="background-color: #a222e4"></span></td>';
                        break;
                    }
                }
                dataHtml += '<td>' + classes[i].coursename + '</td>'//课程名
                    + '<td>' + [sthours, stMill].join(':') + "-" + [enhours, enMill].join(':') + '</td>'
                    /*+ '<td>' + classes[i].coachname + '</td>'*/
                    + '<td>' + classes[i].coursemoney + '</td>';
                if (flag) {
                    dataHtml += '<td> <button type="button" class="layui-btn layui-btn-radius layui-btn-warm subscribeClass" status=1 id=' + classes[i].cid + '>预约</button></td>'
                } else {
                    dataHtml += '<td> <button type="button" class="layui-btn layui-btn-radius layui-btn-normal subscribeClass" status=2  id=' + classes[i].cid + '>已预约</button></td>'
                }
                dataHtml += '</tr>';
            }
        } else {
            dataHtml = '<tr><td colspan="8%">暂无课程</td></tr>';
        }
        return dataHtml;
    }

    //查看所选课程以及当前学员的预约状态
    var flag;
    function subscribe(classId, userId) {
        $.ajax({
            url: getRootPath() + "/api/getSubscribe",
            type: "post",
            headers: {
                'token': $.cookie("token")
            },
            async: false,
            data: {
                "classId": classId,
                "userId": userId
            },
            success: function (res) {
                if (res.success == "true") {
                    flag = true;
                } else if (res.success == "false") {
                    flag = false;
                }
            }
        });
    }

    //预约
    $("body").on("click", ".subscribeClass", function () {
        var cid = $(this).attr('id');
        var status = $(this).attr('status');
        var classIn;
        $.ajax({
            url: getRootPath() + "/api/getClassInfo/" + cid,
            headers: {
                "token": $.cookie("token")
            },
            type: "get",
            async:false,
            success: function (res) {
                if (res.success == "true") {
                    classIn = res.data;
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        if (status == 1) {//如果当前用户未预约所选课程
            if (userInfo.userlever >= classIn.courseclassify) {
                layer.confirm("确定预约该课程吗？", {
                    btn: ['别废话了', '我在想想'],
                    yes: function (index, layero) {
                        var classStartTime = classIn.coursestarttime;//获取课程开始时间
                        var classendtTime = classIn.courseendtime;
                        var currTime = new Date();//获取当前时间
                        var mistiming = classStartTime - currTime.getTime();
                        if(classendtTime < currTime.getTime()){
                            layer.msg("该课程已结束");
                            return;
                        }
                        if (mistiming > 86400000) {
                            subscribeClass(userInfo,classIn);
                        } else if(mistiming < 86400000){
                            layer.msg("对不起，开课前24小时之内不能约课");
                        }
                    },
                    btn2: function (index) {
                        layer.close(index);
                    }
                });
            } else {
                layer.msg("您的体能可能不适应这门课程，快去看看其他课程吧");
            }
        } else if (status == 2) {//如果当前用户已预约所选课程
            layer.confirm("确定取消预约该课程吗？", {
                btn: ['别废话了', '我在想想'],
                yes: function (index, layero) {
                    var classStartTime = classIn.coursestarttime;//获取课程开始时间
                    var currTime = new Date();//获取当前时间
                    var classendtTime = classIn.courseendtime;
                    var mistiming = classStartTime - currTime.getTime();
                    if(classendtTime < currTime.getTime()){
                        layer.msg("该课程已结束");
                        return;
                    }
                    if (mistiming > 3600000) {
                        cancelsubscribeClass(userInfo,classIn);
                    } else {
                        layer.msg("对不起，开课1小时之内不能取消约课");
                    }
                },
                btn2: function (index) {
                    layer.close(index);
                }
            });
        }
    });
    /**
     * 预约
     */
    function subscribeClass(u,c) {
        var startTime = c.coursestarttime;
        var date = new Date(startTime);
        var sthours = date.getHours();
        var stMill = date.getMinutes();
        $.ajax({
            url:getRootPath()+"/api/subscribeClass",
            headers:{
                'token':$.cookie('token')
            },
            type:"post",
            data:{
                "courseid":c.cid,
                "userid":u.uid,
                "ucoachid":c.coachid
            },
            success:function (res) {
                if(res.success=="true"){
                    $("#"+c.cid+"").html("已预约");
                    $("#"+c.cid+"").attr("status","2");
                    $("#"+c.cid+"").attr("class","layui-btn layui-btn-radius layui-btn-normal subscribeClass");
                    layer.open({
                        title:"预约成功！",
                        content:"请于"+ formatDate(startTime)+" "+[sthours,stMill].join(":")+"前到达"+ c.addname,
                    });
                }else{
                    layer.msg(res.msg);
                }
            }
        })
    }
    /**
     * 取消预约
     */
    function cancelsubscribeClass(u,c) {
        $.ajax({
            url:getRootPath()+"/api/cancelsubscribe",
            data:{
                "cid":c.cid,
                "uid":u.uid
            },
            type:"post",
            headers:{
                token:$.cookie("token")
            },
            success:function (res) {
                if(res.success=="true"){
                    layer.msg("取消预约成功");
                    $("#"+c.cid+"").html("预约");
                    $("#"+c.cid+"").attr("status","1");
                    $("#"+c.cid+"").attr("class","layui-btn layui-btn-radius layui-btn-warm subscribeClass");
                }else{
                    layer.msg(res.msg);
                }
            }
        });
    }
});
/*________________________________________________________*/
/**
 * 格式化日期
 * @param date
 * @returns {string}
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
}

/**
 * 获取指定日期所有课程
 * @param startDate
 * @param endDate
 */
var classInfo = "";
function getAllClassInfo(startDate, endDate) {
    $.ajax({
        url: getRootPath() + "/api/allClassInfo",
        type: 'post',
        headers: {
            'token': $.cookie('token')
        },
        data: {
            "startDate": startDate,
            "endDate": endDate
        },
        success: function (res) {
            if (res.success == "true") {//查询之成功之后的操作
                classInfo = res.data;
                //渲染日历
                for (var i = 0; i < res.data.length; i++) {
                    var date = formatDate(res.data[i].coursestarttime);
                    $("#laydateArae .layui-laydate-content td[lay-ymd=" + formatDate(res.data[i].coursestarttime) + "]").css("background", "#ffb632");
                }
            } else if (res.success == "false") {
                if (res.errorCode == "30002") {
                    layer.msg(res.msg + "，即将跳转到登录页面");
                    setTimeout(function () {
                        location.href = getRootPath() + "/static/page/clientStudent/login.html";
                    }, 2000);
                } else {
                    layer.msg(res.msg);
                }
            }
        }
    })
}

/**
 * 获取时间段内所有日期
 * @param value1
 * @param value2
 * @returns {Array}
 */
function getAll(value1, value2) {
    var arr = [];
    var getDate = function (str) {
        var tempDate = new Date();
        var list = str.split("-");
        tempDate.setFullYear(list[0]);
        tempDate.setMonth(list[1] - 1);
        tempDate.setDate(list[2]);
        return tempDate;
    };
    var date1 = getDate(value1);
    var date2 = getDate(value2);
    if (date1 > date2) {
        var tempDate = date1;
        date1 = date2;
        date2 = tempDate;
    }
    date1.setDate(date1.getDate());
    var dateArr = [];
    var i = 0;
    while (!(date1.getFullYear() == date2.getFullYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate())) {
        var dayStr = date1.getDate().toString();
        if (dayStr.length == 1) {
            dayStr = "0" + dayStr;
        }
        dateArr[i] = date1.getFullYear() + "-" + (date1.getMonth() + 1) + "-"
            + dayStr;

        date1.setDate(date1.getDate() + 1);
        i++;
    }
    arr = dateArr;
    arr.push(value2);
    return arr;
}

//获取指定日期的周数第几周
function getYearWeek(param) {
    var a = new Array("日", "一", "二", "三", "四", "五", "六");
    var date = new Date(param);
    var week = date.getDay();
    var str = a[week];
    return str;
}


/**
 * 渲染个人考勤页面
 */
function renderercurrUserClass(that) {
    var dataHtml = '';
    if (that.length != 0) {
        for (var i = 0; i < that.length; i++) {
            //获取开课日期与开始时间
            var startTime = new Date(that[i].coursestarttime);
            var sthours = startTime.getHours();
            var starMill = startTime.getMinutes();
            var stMonth = startTime.getMonth() + 1;
            var stDate = startTime.getDate();
            //获取结束时间
            var endtTime = new Date(that[i].courseendtime);
            var endHours = endtTime.getHours();
            var endMill = endtTime.getMinutes();
            dataHtml += '<tr>'

            switch (that[i].status) {
                case 1: {
                    dataHtml +='<td style="color:#ed8b22">' + [stMonth, stDate].join('.') + '</td>'
                           + '<td><span class="layui-badge-dot" style="background-color: #ed8b22"></span></td>';
                    break;
                }
                case 2: {
                    dataHtml +='<td style="color:#3a74d3">' + [stMonth, stDate].join('.') + '</td>'
                         +'<td><span class="layui-badge-dot" style="background-color:  #3a74d3"></span></td>';
                    break;
                }
                case 3: {
                    dataHtml += '<td style="color:#4E5465">' + [stMonth, stDate].join('.') + '</td>'
                           +'<td><span class="layui-badge-dot" style="background-color: #4E5465"></span></td>';
                    break;
                }
            }
            dataHtml += '<td>' + that[i].coursename + '</td>'//课程名
                + '<td>' + [sthours, starMill].join(':') + "-" + [endHours, endMill].join(':') + '</td>'//时间
               /* + '<td>' + that[i].coachname + '</td>'*/
            switch (that[i].status) {
                case 1: {
                    dataHtml += '<td style="color: #ed8b22">已预约</td>';
                    break;
                }
                case 2: {
                    dataHtml += '<td style="color:#3a74d3">已完成</td>';
                    break;
                }
                case 3: {
                    dataHtml += '<td style="color:#4E5465">未打卡</td>';
                    break;
                }
            }
        }
    } else {
        dataHtml = '<tr><td colspan="8%">暂无课程</td></tr>';
    }
    return dataHtml;
}
/**
 * 获取项目根路径
 * @returns {string}
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


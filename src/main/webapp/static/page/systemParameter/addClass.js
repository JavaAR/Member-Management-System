var $form;
var form;
var $;
var laydate;
/*layui.config({
    base: "../../js/"
}).*/layui.use(['form', 'layer', 'upload', 'laydate'], function () {
    form = layui.form;
    var layer = parent.layer === undefined ? layui.layer : parent.layer;
    $ = layui.jquery;
    $form = $('form');
    laydate = layui.laydate;


    var token = $.cookie("token");
    var loc = location.href;
    var n1 = loc.length;
    var n2 = loc.indexOf("=");
    var cid = decodeURI(loc.substr(n2+1,n1-n2));
    var date = new Date();


    //开始日期渲染
   var startDate=  laydate.render({
        elem: '#startDate',
        theme: 'molv',
        min:date.toLocaleString(),
       done:function (value, date, endData) {
           endDate.config.min ={
               year: date.year,
               month: date.month - 1,
               date: date.date
           };
       }
    });
    //开始时间渲染
    var startTime = laydate.render({
        elem: '#startTime',
        type: 'time',
        theme: 'molv',
        position:'fixed',
        min: '05:00:00',
        max: '22:30:00',
        done:function (value, date, endData) {

        }
    });
    //结束日期渲染
    var endDate = laydate.render({
        elem: '#endDate',
        theme: 'molv'
    });
    //结束时间渲染
    var endTime = laydate.render({
        elem: '#endTime',
        type: 'time',
        min: '05:00:00',
        max: '22:30:00',
        btns: ['clear', 'confirm'],
        theme: 'molv'
    });
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
    });
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
    });
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
    //验证开始日期与结束日期是否正确
    form.verify({
        verifyData:function (value,item) {
            if(new RegExp($("#startTime").val()).test(value)){
                alert(startTime.value())
                   return "结束日期不能与开始日期相同";
            }
            if($("#startTime").val()>$("#endTime").val()){
                alert($("#startTime").val());
                return "结束时间不能小于开始时间,shabi ";
            }
        }
    });
    //提交课程资料
    form.on("submit(changeUser)", function (data) {
        /*var index = layer.msg('提交中，请稍候', {icon: 16, time: false, shade: 0.8});*/
        //获取输入框数据
        var coursename = $(".coursename").val();//课程名
        var classify = $("#selectclassify").val();//课程分类
        var coach = $("#selectcoach").val();//教练选择
        var address = $("#selectaddress").val();//地址
        var classMoney =$(".classmoney").val();//课程费用
        var startDate =$("#startDate").val();//开始日期
        var startTime = $("#startTime").val();//开始时间
        var endDate = $("#endDate").val();//结束日期
        var endTime = $("#endTime").val();//结束时间
        var weekTime = $("#selectweek").val();//每周几上课
        var weekDay = $("#selectweek").text();

        var j = parseInt(weekTime);
        var arrDates =  getWeek(startDate,endDate,j);

        layer.confirm('提交之后系统将为您生成'+startDate+'至'+endDate+"时间段内，所有"+$('#selectweek option[value ='+weekTime+ ']').text()+"的课程，确认添加吗？",{
            btn:['确认','取消'],
            yes:function (index, layero) {//确认回调
                $.ajax({
                    url:getRootPath()+"/api/insertClassInfo",
                    headers:{
                        'token':token
                    },
                    type:'post',
                    data: {
                       "coursename":coursename,
                        "courseclassify":classify,
                        "coachid":coach,
                        "addressid":address,
                        "coursemoney":classMoney,
                        "startTime":startTime,
                        "endTime":endTime,
                        "arrDates":JSON.stringify(arrDates)
                    },
                    success:function (res) {
                        if(res.success=="true"){
                            layer.msg("添加成功");
                        }else{
                            layer.msg(res.msg);
                        }
                    }
                })
            },
            btn2:function (index, layero) {//取消回调
                layer.close(index);
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })
});
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
/* 获取时间段内属于星期一(*)的日期们
* begin: 开始时间
* end：结束时间
* weekNum：星期几 {number}
*/
function getWeek (begin, end, weekNum){
    var dateArr = new Array();
    var stimeArr = begin.split("-");//=>["2018", "01", "01"]
    var etimeArr = end.split("-");//=>["2018", "01", "30"]

    var stoday = new Date();
    stoday.setUTCFullYear(stimeArr[0], stimeArr[1]-1, stimeArr[2]);
    var etoday = new Date();
    etoday.setUTCFullYear(etimeArr[0], etimeArr[1]-1, etimeArr[2]);

    var unixDb = stoday.getTime();//开始时间的毫秒数
    var unixDe = etoday.getTime();//结束时间的毫秒数

    for (var k = unixDb; k <= unixDe;) {
        var needJudgeDate = msToDate(parseInt(k)).withoutTime;
        //不加这个if判断直接push的话就是已知时间段内的所有日期
        if (new Date(needJudgeDate).getDay() === weekNum) {
            dateArr.push(needJudgeDate);
        }
        k = k + 24*60*60*1000;
    }
    return dateArr;
}
//根据毫秒数获取日期
function msToDate (msec) {
    var datetime = new Date(msec);
    var year = datetime.getFullYear();
    var month = datetime.getMonth();
    var date = datetime.getDate();
    var hour = datetime.getHours();
    var minute = datetime.getMinutes();
    var second = datetime.getSeconds();

    var result1 = year +
        '-' +
        ((month + 1) >= 10 ? (month + 1) : '0' + (month + 1)) +
        '-' +
        ((date + 1) < 10 ? '0' + date : date) +
        ' ' +
        ((hour + 1) < 10 ? '0' + hour : hour) +
        ':' +
        ((minute + 1) < 10 ? '0' + minute : minute) +
        ':' +
        ((second + 1) < 10 ? '0' + second : second);

    var result2 = year +
        '-' +
        ((month + 1) >= 10 ? (month + 1) : '0' + (month + 1)) +
        '-' +
        ((date + 1) < 11 ? '0' + date : date);

    var result = {
        hasTime: result1,
        withoutTime: result2
    };
    return result;
}



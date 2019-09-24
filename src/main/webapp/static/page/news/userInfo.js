var $form;
var form;
var $;
layui.config({
    base: "../../js/"
}).use(['form', 'layer', 'upload', 'laydate'], function () {
    form = layui.form();
    var layer = parent.layer === undefined ? layui.layer : parent.layer;
    $ = layui.jquery;
    $form = $('form');
    laydate = layui.laydate;
    /**
     * 学员个人信息展示
     */

    var token =  $.cookie("token");

    $("#back").click(function () {
        alert(2);
        location.href="newsList.html";
    });

    //查询出当前用户的详细信息并显示
    /*var uid = $.cookie("uid");*/
    var loc = location.href;
    var n1 = loc.length;
    var n2 = loc.indexOf("=");
    var uid = decodeURI(loc.substr(n2+1,n1-n2));
    $.ajax({
        url: getRootPath()+'/api/getUserInfo/' + uid,
        type: 'get',
        headers:{
            'token':token
        },
        success: function (res) {
            if (res.success = 'true') {
                var userCode = res.data.usercode;
                var username = res.data.username;
                var userPhone = res.data.userphone;
                var userBackGround = res.data.userbackground;
                var gender = res.data.gender;
                var userAge = res.data.age;
                var userBrithday = res.data.birthday;
                var leverName = res.data.leverName;
                //格式化日期
                dat = new Date(userBrithday);
                var year = dat.getFullYear();
                var month = dat.getMonth() + 1;
                var date = dat.getDate();
                //显示到输入框
                $(".userCode").val(userCode);//用户名
                $(".realName").val(username); //用户真实姓名
                /*$(".userSex input:radio[value=" +gender+ "]").attr('checked', 'checked'); //性别*/
                $("input[value="+ gender +"]").attr('checked','true');
                $(".userPhone").val(userPhone); //手机号
                $(".userBirthday").val([year, month, date].join('-')); //出生年月
                $(".Age").val(userAge); //用户年龄
                $(".userLever").val(leverName);
                $(".myself").val(userBackGround); //学员背景
                form.render('radio');
            } else {
                layer.msg(res.msg);
            }
        }
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



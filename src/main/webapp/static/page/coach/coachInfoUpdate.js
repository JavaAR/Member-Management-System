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
     * 教练个人信息渲染与提交
     */

    var isforbidden;
    var token = $.cookie("token");
    var loc = location.href;
    var n1 = loc.length;
    var n2 = loc.indexOf("=");
    var uid = decodeURI(loc.substr(n2+1,n1-n2));
    /**
     * 是否禁用
     */
    form.on("switch(isShow)",function (data) {
        var x = data.elem.checked;
        if(data.value=="false"){
            layer.confirm("改变之后该用户不可以登录系统，确认改变吗？",{
                btn: ['确认','取消'],
            },function (index) {//按钮1的回调
                layer.close(index);
                data.elem.checked=x;
                $("input[name='show']").attr("value","true");
                isforbidden = "true";
                form.render();
            },function (index, layero) {//按钮2的回调
                data.elem.checked = !x;
                $("input[name='show']").attr("value","false");
                isforbidden = "false";
                form.render();
            })
        }else if(data.value=="true"){
            layer.confirm("改变之后该用户可以继续登录系统，确认改变吗？",{
                btn: ['确认','取消'],
            },function (index) {//确认的回调
                layer.close(index);
                $("input[name='show']").attr("value","false");
                isforbidden = "false";
                form.render();
            },function (index, layero) {//取消的回调
                layer.close(index);
                data.elem.checked=x;
                $("input[name='show']").attr("value","true");
                isforbidden = "true";
                form.render();
            })
        }
    });


    //提交个人资料
    form.on("submit(changeUser)", function (data) {
        var index = layer.msg('提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        //获取输入框数据
        var usercode = $(".userCode").val();
        var username = $(".realName").val();
        var gender = data.field.sex;
        var userphone = $(".userPhone").val();
        var brithday = $(".userBirthday").val();
        str = brithday.replace(/-/g, '/');
        var date = new Date(str);
        var age = $(".Age").val();
        var myself = $(".myself").val();
        var forbdden = $("#isforbdden").val();
        //发送请求
        $.ajax({
            url:getRootPath()+'/api/updateCoachInfo',
            headers: {
                "token": token
            },
            type: 'post',
            contentType: "application/json",
            data: JSON.stringify({
                "uid":uid,
                'usercode': usercode,
                'username': username,
                'gender': gender,
                'userphone': userphone,
                'birthday': date,
                'age': age,
                'userbackground': myself,
                "forbidden":forbdden
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
    });
    //查询出当前用户的详细信息并显示
    $.ajax({
        url: getRootPath()+'/api/getCoachInfo/' + uid,
        headers:{
            'token':token
        },
        type: 'get',
        success: function (res) {
            if (res.success = 'true') {
                var userCode = res.data.usercode;
                var username = res.data.username;
                var userPhone = res.data.userphone;
                var userBackGround = res.data.userbackground;
                var gender = res.data.gender;
                var userAge = res.data.age;
                var userBrithday = res.data.birthday;
                var forbidden = res.data.forbidden;
               /* var userlever =res.data.userlever;*/
                //格式化日期
                dat = new Date(userBrithday);
                var year = dat.getFullYear();
                var month = dat.getMonth() + 1;
                var date = dat.getDate();
                //显示到输入框
                $(".userCode").val(userCode);//用户名
                $(".realName").val(username); //用户真实姓名
                $(".userSex input[value=" + gender + "]").attr("checked", "checked"); //性别
                $(".userPhone").val(userPhone); //手机号
                $(".userBirthday").val([year, month, date].join('-')); //出生年月
                $(".Age").val(userAge); //用户年龄
                $(".myself").val(userBackGround); //教练背景
                if(forbidden=="true"){
                    $("input[name='show']").attr('checked','checked');
                    $("input[name='show']").attr("value","true");
                }else if(forbidden=="false"){
                    $("input[name='show']").removeAttr('checked');
                    $("input[name='show']").attr("value","false");
                }
                form.render('radio');
                form.render('select');
                form.render('checkbox');
            } else {
                layer.msg(res.msg);
            }

        }
    })//


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

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

    //添加验证规则
    var oldpwd;
    form.verify({
        oldPwd: function (value, item) {
            if (value !== oldpwd) {
                return "密码错误，请重新输入！";
            }
        },
        newPwd: function (value, item) {
            if (value == oldpwd) {
                return "新密码不能与旧密码一致";
            }
            if (value.length < 6) {
                return "密码长度不能小于6位";
            }
        },
        confirmPwd: function (value, item) {
            if (!new RegExp($("#oldPwd").val()).test(value)) {
                return "两次输入密码不一致，请重新输入！";
            }
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
        var age = $(".Age").val();
        var myself = $(".myself").val();
        //发送请求
        $.ajax({
            url: getRootPath()+'/api/updateUserInfo',
            headers: {
                "token": token
            },
            type: 'post',
            contentType: "application/json",
            data: JSON.stringify({
                'usercode': usercode,
                'username': username,
                'gender': gender,
                'userphone': userphone,
                'birthday': brithday,
                'age': age,
                'userbackground': myself
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

    //修改密码
    form.on("submit(changePwd)", function (data) {
        var index = layer.msg('提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        $.ajax({
            url: getRootPath()+'/api/changeUserPwd',
            headers: {
                "token": token
            },
            type: "get",
            data: {
                'newPassword': data.field.finallPwd
            },
            success: function (res) {
                if (res.success =="true") {
                    layer.close(index);
                    layer.msg("修改成功，即将跳转到登陆页面");
                    setTimeout(function () {
                        top.location = getRootPath()+"/login.html";
                        $.removeCookie("token", {path: '/'});
                        $.removeCookie("genTime", {path: '/'});
                        $.removeCookie("expTime", {path: '/'});
                    },1000);
                } else if (res.success == "false") {
                    layer.close(index);
                    layer.msg(res.msg);
                } else {
                    layer.msg("修改失败,请重试");
                }
            }
        })
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })
    var token =   $.cookie("token");
    //查询出当前用户的详细信息并显示
    $.ajax({
        url:getRootPath() +'/api/getUserInfo',
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
                //获取密码 以便于将来修改密码用
                oldpwd = res.data.userpassword;
                //格式化日期
                dat = new Date(userBrithday);
                var year = dat.getFullYear();
                var month = dat.getMonth() + 1;
                var date = dat.getDate();
                console.log([year, month, date].join('-'));
                //显示到输入框
                $(".userCode").val(userCode);//用户名
                $(".realName").val(username); //用户真实姓名
                $(".userSex input[value="+gender+"]").attr( 'checked', "checked"); //性别
                $(".userPhone").val(userPhone); //手机号
                $(".userBirthday").val([year, month, date].join('-')); //出生年月
                $(".Age").val(userAge); //用户年龄
                $(".myself").val(userBackGround); //学员背景
                form.render('radio');
            } else {
                layer.msg(res.msg);
            }

        }


    })

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



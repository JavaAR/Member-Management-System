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
     * 添加教练
     */


    var token = $.cookie("token");
    //验证两次密码是否一致
    form.verify({
        newPwd: function (value, item) {
            if (value.length < 6) {
                return "密码长度不能小于6位";
            }
        },
        confirmPwd: function (value, item) {
            if (!new RegExp($("#oldPwd").val()).test(value)) {
                return "两次输入密码不一致，请重新输入！";
            }
        }
    })
    //验证手机号是否存在
    $(".userPhone").blur(function () {
        var userPhone =	$(".userPhone").val();
        $.ajax({
            url:getRootPath()+'/api/userPhoneIsExit/'+userPhone,
            type:'get',
            headers:{
                'token':token
            },
            success:function (res) {
                if(res.success=="false"){
                    setTimeout(function () {
                        layer.msg(res.msg)
                    },1000);
                    $(".userPhone").val("");
                }else if(res.success=="true"){
                }
            }
        })
    })
    //获取限制登录框的值
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
  /*  //初始等级下拉框
    $.ajax({
        url:getRootPath()+"/api/getAllUserLever",
        headers:{
            'token':token
        },
        type:"get",
        success:function (res) {
            if(res.success=="true"){
                var option = '<option value="">请选择</option>';
                for (var i = 0; i < res.data.length;i++){
                    option+="<option value='"+res.data[i].leverid+"'>"+ res.data[i].levername+"</option>";
                }
                $("#selectuselever").html("");
                $("#selectuselever").append(option);
                form.render();
            }else{
                layer.msg(res.msg);
            }
        }
    });*/

    //提交个人资料
    form.on("submit(insertUser)", function (data) {
        var index = layer.msg('提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        //获取输入框数据
        var usercode = $(".userCode").val();
        var username = $(".realName").val();
        var gender = data.field.sex;
        var userphone = $(".userPhone").val();
        var brithday = $(".userBirthday").val();
        var userpassword =  $(".pwd").val();
        var isforbdden = $("#isforbdden").val();
        var userLever = $("#selectuselever").val();
        str = brithday.replace(/-/g, '/');
        var date = new Date(str);
        date.setDate(date.getDate());
        var age = $(".Age").val();
        var myself = $(".myself").val();
        //发送请求
        $.ajax({
            url:getRootPath()+'/api/insertCoach',
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
                'birthday': date,
                'age': age,
                'userbackground': myself,
                'userpassword':userpassword,
                'forbidden':isforbdden
                /*"userlever":userLever*/
            }),
            success: function (res) {
                if (res.success == "true") {
                    layer.close(index);
                    layer.msg("提交成功,即将返回上一页面");
                    setTimeout("close('yes')",3000);
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })//

});

//延时关闭
function close(status){
    if(status=="yes"){
        window.parent.location.reload();//刷新父页面
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);
    }
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.close(index);
}


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



layui.config({
    base : "js/"
}).use(['form','layer'],function(){
    var form = layui.form(),
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery;
    //video背景
     $(window).resize(function(){
         if($(".video-player").width() > $(window).width()){
             $(".video-player").css({"height":$(window).height(),"width":"auto","left":-($(".video-player").width()-$(window).width())/2});
         }else{
             $(".video-player").css({"width":$(window).width(),"height":"auto","left":-($(".video-player").width()-$(window).width())/2});
         }
     }).resize();

    //登录按钮事件
    form.on('submit(login)', function (data) {
        $.ajax({
            url:getRootPath()+'/api/userLogin',
            type: 'post',
            data: {
                'username': data.field.username,
                'password': data.field.password
            },
            success: function (result) {
                if(result.errorCode==30003){
                    layer.msg(result.msg);
                }else if(result.errorCode==30002){
                    layer.msg(result.msg);
                }else if(result.errorCode==30000){
                    layer.msg(result.msg);
                }else if(result.success=="true"){
                    if(result.data.user.userrole==2){
                        /* 学员端 开发中*/
                        if(result.data.user.forbidden=="true"){
                            layer.msg("对不起，您已被管理员限制登录，请联系管理员");
                        }else{
                            $.cookie("token",result.data.token);
                            $.cookie("expTime",result.data.expTime);
                            $.cookie("genTime",result.data.genTime);
                            var url = "index.html";
                            window.location.assign(encodeURI(url));
                        }
                    }else{
                        layer.msg("这里是教练端登录入口，您的身份不符合");
                    }
                }
            }
        })
        return false;
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

})

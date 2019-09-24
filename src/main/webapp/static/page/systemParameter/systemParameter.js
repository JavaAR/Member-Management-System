layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'laypage','laydate'], function () {
    var form = layui.form(),
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        laydate = layui.laydate,
        $ = layui.jquery;
    //加载页面数据
    var token = $.cookie("token");
    getAllClassInfo();
    /**
     * 获取所有课程
     * @param param
     */
    var clasData = "";
    function getAllClassInfo() {

        $.ajax({
            url: getRootPath() + '/api/allClassInfo',
            headers: {
                'token': token
            },
            type: 'post',
            success: function (res) {
                if (res.success == "true") {
                    clasData = res.data;
                    classList();
                } else if (res.success == "false") {
                    if (res.errorCode == "30002") {
                        layer.msg(res.msg + "，即将跳转到登录页面");
                        setTimeout(function () {
                            location.href = getRootPath() + "/login.html";
                        }, 2000);
                    } else {
                        layer.msg(res.msg);
                    }
                }
            }
        })
    }
    //日期搜索开始框
    var search_startDate={
        elem: $("#startDate_search")[0], //需显示日期的元素选择器
        event: 'click', //触发事件
        format: 'YYYY-MM-DD', //日期格式
        istime: false, //是否开启时间选择
        isclear: true, //是否显示清空
        istoday: false, //是否显示天今
        issure: true, //是否显示确认
        festival: true, //是否显示节日
         min: '1900-01-01 00:00:00', //最小日期
        max: '2099-12-31 23:59:59', //最大日期
        start: '2014-6-15 23:00:00',  //开始日期
        fixed: false, //是否固定在可视区域
        zIndex: 99999999, //css z-index
        choose: function(dates){
            search_endDate.min = dates;
        }//选择好日期的回调
    };
    //开始时间点击事件
    $("#startDate_search").click(function () {
        laydate(search_startDate);
    });
    //结束日期搜索框
    var search_endDate={
        elem: $("#endDate_search")[0], //需显示日期的元素选择器
        event: 'click', //触发事件
        format: 'YYYY-MM-DD', //日期格式
        istime: false, //是否开启时间选择
        isclear: true, //是否显示清空
        istoday: false, //是否显示今天
        issure: true, //是否显示确认
        festival: true, //是否显示节日
        max: '2099-12-31 23:59:59', //最大日期
        start: '2014-6-15 23:00:00',  //开始日期
        fixed: false, //是否固定在可视区域
        zIndex: 99999999, //css z-index
        choose: function(dates){
        }//选择好日期的回调
    };
    $("#endDate_search").click(function () {
        laydate(search_endDate);
    });
    //日期搜索结束框
    //查询
    $(".search_btn").click(function () {
        if ($(".search_input").val() == "") {
            layer.msg("请输入需要查询的内容");
        } else if($("#startDate_search").val()=="") {
            layer.msg("请输入需要查询的开始时间");
        }else if($("#endDate_search").val()=="") {
            layer.msg("请输入需要查询的结束时间");
        }else{
            var username = $(".search_input").val();
            var start_search = $("#startDate_search").val();
            var end_search = $("#endDate_search").val();
            $.ajax({
                url: getRootPath() + '/api/allClassInfo',
                headers: {
                    'token': token
                },
                type: 'post',
                data: {
                    classname:username,
                    startDate:start_search,
                    endDate:end_search
                },
                success: function (res) {
                    if (res.success == "true") {
                        var classArr = [];
                        clasData = res.data;
                         for (var i = 0; i < clasData.length; i++) {
                             classArr.push(clasData[i]);
                         }
                        clasData = classArr;
                        classList(clasData);
                    } else if (res.success == "false") {
                        if (res.errorCode == "30002") {
                            layer.msg(res.msg + "，即将跳转到登录页面");
                            setTimeout(function () {
                                location.href = getRootPath() + "/login.html";
                            }, 2000);
                        } else {
                            layer.msg(res.msg);
                        }
                    }
                }
            })
        }
    });
    //课程预约信息
    $("body").on("click",".class_subscribe",function () {
        var id = $(this).data('id');
        location.href = "classeSubscribe.html?cid=" + id;
    });
    //添加课程
    //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
    $(window).one("resize", function () {
        $(".newsAdd_btn").click(function () {
            var index = layui.layer.open({
                title: "添加课程",
                type: 2,
                area: ['470px', '620px'],
                content: "addClass.html",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回课程列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            });
            layui.layer.full(index);
        })
    }).resize();
    //修改课程
    $("body").on("click", ".class_edit", function () {  //编辑
        var id = $(this).data('id');
        location.href = "classedit.html?cid=" + id;
    });
    //删除课程
    $("body").on("click", ".calss_del", function () {  //删除
        var cid = $(this).data('id');
        layer.confirm('确定删除此课程，删除后不能恢复？', {icon: 3, title: '提示信息'}, function (index) {
            $.ajax({
                url: getRootPath() + "/api/delClassInfo/" + cid,
                type: "get",
                headers: {
                    'token': token
                },
                success: function (res) {
                    if (res.success == "true") {
                        layer.msg("删除成功");
                        layer.close(index);
                        getAllClassInfo();
                    } else {
                        layer.msg(res.msg);
                    }
                }
            });
            layer.close(index);
        });
    });

//渲染数据
 function classList(that) {
        //分页
        function renderDate(data, curr) {
            var dataHtml = '';
            if (!that) {
                currData = clasData.concat().splice(curr * nums - nums, nums);
            } else {
                currData = that.concat().splice(curr * nums - nums, nums);
            }
            if (currData.length != 0) {
                for (var i = 0; i < currData.length; i++) {
                    dat = new Date(currData[i].coursestarttime);
                    var styear = dat.getFullYear();
                    var stmonth = dat.getMonth() + 1;
                    var stdate = dat.getDate();
                    var sthours = dat.getHours();
                    var stMill = dat.getMinutes();
                    dat = new Date(currData[i].courseendtime);
                    var enyear = dat.getFullYear();
                    var enmonth = dat.getMonth() + 1;
                    var endate = dat.getDate();
                    var enhours = dat.getHours();
                    var enMill = dat.getMinutes();
                    dataHtml+= '<tr>'
                        /*+ '<td >' + currData[i].coursename + '</td>'*/
                        +'<td><a style="color:#1E9FFF;" target="_blank" class="class_subscribe" data-id="'+ data[i].cid +'">'+currData[i].coursename+'</a></td>'
                        + '<td>' + currData[i].courseclassfyname + '</td>'
                        + '<td>' + currData[i].coachname + '</td>'
                        + '<td>' + currData[i].addname + '</td>'
                        + '<td>' + currData[i].coursemoney + '</td>'
                        +'<td>' + [styear, stmonth, stdate].join('-')+" "+[sthours,stMill].join(':')+'</td>'
                        + '<td>' + [enyear, enmonth, endate].join('-')+" "+[enhours,enMill].join(':')+'</td>'
                        + '<td>'
                        + '<a class="layui-btn layui-btn-mini class_edit" data-id="' + data[i].cid + '"><i class="iconfont icon-edit"></i> 编辑</a>'
                        + '<a class="layui-btn layui-btn-danger layui-btn-mini calss_del" data-id="' + data[i].cid + '"><i class="layui-icon">&#xe640;</i> 删除</a>'
                        + '</td>'
                        + '</tr>';
                }
            } else {
                dataHtml = '<tr><td colspan="8">暂无数据</td></tr>';
            }
            return dataHtml;
        }

        //分页
        var nums = 10; //每页出现的数据量
        if (that) {
            clasData = that;
        }
        laypage({
            cont: "page",
            pages: Math.ceil(clasData.length / nums),
            jump: function (obj) {
                $(".class_content").html(renderDate(clasData, obj.curr));
                form.render();
            }
        })
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
        return (localhostPaht + projectName);
    }
});

layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'laypage'], function () {
    var form = layui.form(),
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;

    /**
     * 学员初始页数据展示
     */

    //加载页面数据
    var token = $.cookie("token");
    getAllUserInfo();

    var userData = "";
    /**
     * 获取所有用户
     * @param param
     */
    function getAllUserInfo() {
        $.ajax({
            url: getRootPath() + '/api/allUserInfo',
            headers: {
                'token': token
            },
            type: 'post',
            success: function (res) {
                if (res.success == "true") {
                    userData = res.data;
                    newsList();
                } else {
                    layer.msg(res.msg);
                }
            }
        })
    }


    //查询
    $(".search_btn").click(function () {
        if ($(".search_input").val() != '') {
            var username = $(".search_input").val();
            $.ajax({
                url: getRootPath() + '/api/allUserInfo',
                headers: {
                    'token': token
                },
                data:{
                    'username':username
                },
                type: 'post',
                success: function (res) {
                    var userArr = [];
                    if (res.success == "true") {
                        userData = res.data;
                         for (var i = 0; i < userData.length; i++) {
                             userArr.push(userData[i]);
                         }
                         userData =userArr;
                        newsList(userData);
                    } else {
                        layer.msg(res.msg);
                    }
                }
            })
        } else {
            layer.msg("请输入需要查询的内容");
        }
    });

    //添加学员
    //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
    $(window).one("resize", function () {
        $(".newsAdd_btn").click(function () {
            var index = layui.layer.open({
                title: "添加学员",
                type: 2,
                area: ['470px', '600px'],
                content: "newsAdd.html",
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回学员列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                }
            });
            layui.layer.full(index);
        })
    }).resize();

    //修改学院信息
    $("body").on("click", ".news_edit", function () {  //编辑
        var id = $(this).data('id');
        location.href = "userInfoUpdate.html?uid=" + id;
    });
    //查看学员信息
    $("body").on("click", ".news_collect", function () {  //查看.
        var id = $(this).data('id');
        location.href = "userInfo.html?uid=" + id;
    });
    //删除学员
    $("body").on("click", ".news_del", function () {  //删除
        //_this.parents("tr").remove();
        var uid = $(this).data('id');
        layer.confirm('确定删除此学员，删除后不能恢复？', {icon: 3, title: '提示信息'}, function (index) {
            $.ajax({
                url: getRootPath() + "/api/delStudent/" + uid,
                type: "get",
                headers: {
                    'token': token
                },
                success: function (res) {
                    if (res.success == "true") {
                        layer.msg("删除成功");
                        layer.close(index);
                        getAllUserInfo();
                    } else {
                        layer.msg(res.msg);
                    }
                }
            });
            layer.close(index);
        });
    });

//渲染数据
    function newsList(that) {
        function renderDate(data, curr) {
            var dataHtml = '';
            if (!that) {
                currData = userData.concat().splice(curr * nums - nums, nums);
            } else {
                currData = that.concat().splice(curr * nums - nums, nums);
            }
            if (currData.length != 0) {
                for (var i = 0; i < currData.length; i++) {
                    dat = new Date(currData[i].birthday);
                    var year = dat.getFullYear();
                    var month = dat.getMonth() + 1;
                    var date = dat.getDate();
                    dataHtml += '<tr>'
                        + '<td align="left">' + currData[i].uid + '</td>'
                        + '<td>' + currData[i].username + '</td>'
                        + '<td>' + currData[i].userphone + '</td>'
                        + '<td>' + currData[i].age + '</td>';
                    if (currData[i].forbidden == "false") {
                        dataHtml += '<td style="color:#38ff3f">' + '否' + '</td>';
                    } else if (currData[i].forbidden == "true") {
                        dataHtml += '<td style="color:#ff000c">' + '是' + '</td>';
                    }
                    dataHtml += '<td>' + [year, month, date].join('-') + '</td>'
                        + '<td>'
                        + '<a class="layui-btn layui-btn-mini news_edit" data-id="' + data[i].uid + '"><i class="iconfont icon-edit"></i> 编辑</a>'
                        + '<a class="layui-btn layui-btn-normal layui-btn-mini news_collect" data-id="' + data[i].uid + '"><i class="layui-icon">&#xe600;</i> 查看</a>'
                        + '<a class="layui-btn layui-btn-danger layui-btn-mini news_del" data-id="' + data[i].uid + '"><i class="layui-icon">&#xe640;</i> 删除</a>'
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
            userData = that;
        }
        laypage({
            cont: "page",
            pages: Math.ceil(userData.length / nums),
            jump: function (obj) {
                $(".news_content").html(renderDate(userData, obj.curr));
                $('.news_list thead input[type="checkbox"]').prop("checked", false);
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

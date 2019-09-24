layui.config({
    base: "js/"
}).use(['form', 'layer', 'jquery', 'laypage'], function () {
    var form = layui.form(),
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;


    var loc = location.href;
    var n1 = loc.length;
    var n2 = loc.indexOf("=");
    var cid = decodeURI(loc.substr(n2+1,n1-n2));
    //加载页面数据
    var token = $.cookie("token");
    //获取所有预约以及考勤信息
    var allSubscribeInfo = "";
    $.ajax({
        url:getRootPath()+"/api/getCurrClassSubscribeInfo",
        type:'get',
        data:{
          'cid':cid
        },
        headers:{
            'token':token
        },
        success:function (res) {
            if(res.success=="true"){
                allSubscribeInfo = res.data
                SubscribeInfo();
            }else{
                layer.msg(res.msg)
            }
        }
    });

    //获取所有预约状态并渲染
    //等级下拉框选中
    $.ajax({
        url:getRootPath()+"/api/getAllSubscribeidStatus",
        headers:{
            'token':token
        },
        type:"get",
        success:function (res) {
            if(res.success=="true"){
                var option = '<option value="">请选择预约状态</option>';
                for (var i = 0; i < res.data.length;i++){
                    option+="<option value='"+res.data[i].statusid+"'>"+ res.data[i].statusname+"</option>";
                }
                $("#selectStatus").html("");
                $("#selectStatus").append(option);
                form.render();
            }else{
                layer.msg(res.msg);
            }
        }
    });

    /**
     * 点击查询
     */
    form.on("submit(selectByStatus)", function (data) {
        var status = $("#selectStatus").val();
        $.ajax({
            url:getRootPath()+"/api/getCurrClassSubscribeInfo",
            type:'get',
            data:{
                'cid':cid,
                'statusid':status
            },
            headers:{
                'token':token
            },
            success:function (res) {
                var Info =[];
                if(res.success=="true"){
                    allSubscribeInfo = res.data;
                    for(var i = 0;i<allSubscribeInfo.length;i++){
                        Info.push(allSubscribeInfo[i]);
                    }
                    allSubscribeInfo = Info;
                    SubscribeInfo(allSubscribeInfo)
                }else{
                    layer.msg(res.msg)
                }
            }
        });
        form.render();
    })
    //修改教练信息
    $("body").on("click", ".coach_edit", function () {  //编辑
        var sid = $(this).data('id');
        location.href = "checkWorkUpdate.html?sid=" + sid;
    });
//渲染数据
    function SubscribeInfo(that) {
        function renderDate(data, curr) {
            var dataHtml = '';
            if (!that) {
                currData = allSubscribeInfo.concat().splice(curr * nums - nums, nums);
            } else {
                currData = that.concat().splice(curr * nums - nums, nums);
            }
            if (currData.length != 0) {
                for (var i = 0; i < currData.length; i++) {
                    var dat = new Date(currData[i].subscribeiddate);
                    var year = dat.getFullYear();
                    var month = dat.getMonth() + 1;
                    var date = dat.getDate();
                    var startdat = new Date(currData[i].coursestarttime);
                    var startyear = startdat.getFullYear();
                    var startmonth = startdat.getMonth() + 1;
                    var startdate = startdat.getDate();
                    var startHours = startdat.getHours();
                    var startMill = startdat.getMinutes();
                    var enddat = new Date(currData[i].courseendtime);
                    var endyear = enddat.getFullYear();
                    var endmonth = enddat.getMonth() + 1;
                    var enddate = enddat.getDate();
                    var endHours = enddat.getHours();
                    var endtMill = enddat.getMinutes();
                    dataHtml += '<tr>'
                        + '<td>' + currData[i].coursename + '</td>'
                        + '<td>' + currData[i].studentname + '</td>'
                        + '<td>' + currData[i].coachname + '</td>'
                        + '<td>' + [year, month, date].join('-') + '</td>'
                        + '<td>' + [startyear, startmonth, startdate].join('-')+" "+[startHours,startMill].join(':') + '</td>'
                        + '<td>' + [endyear, endmonth, enddate].join('-')+" "+[endHours,endtMill].join(':') + '</td>'
                    switch (currData[i].status) {
                        case 1 :{
                            dataHtml += '<td status=1>' + '已预约' + '</td>';
                            break
                        }
                        case 2 :{
                            dataHtml += '<td status=2>' + '已打卡' + '</td>';
                            break
                        }
                        case 3 :{
                            dataHtml += '<td status=2>' + '未打卡' + '</td>';
                            break
                        }
                    }
                    dataHtml += '<td>'
                        /*+ '<a class="layui-btn layui-btn-mini coach_edit" data-id="' + data[i].subscribeid + '"><i class="iconfont icon-edit"></i> 编辑</a>'*/
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
        /* if (that) {
             allSubscribeInfo = that;
         }*/
        laypage({
            cont: "page",
            pages: Math.ceil(allSubscribeInfo.length / nums),
            jump: function (obj) {
                $(".subscribeContent").html(renderDate(allSubscribeInfo, obj.curr));
                form.render();
            }
        })
    }
    /*____________________________________________________________________________*/

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

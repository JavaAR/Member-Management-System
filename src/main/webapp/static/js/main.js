layui.config({
	base : "js/"
}).use(['form','element','layer','jquery'],function(){
	var form = layui.form(),
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		element = layui.element(),
		$ = layui.jquery;

	$(".panel a").on("click",function(){
		window.parent.addTab($(this));
	})

	//动态获取全部用户总数
	$.ajax({
		url:getRootPath()+"/api/getAllUserCount",
		type:'get',
		headers:{
			'token':$.cookie("token")
		},
		success:function (res) {
			if (res.success == "true") {
				$(".newMessage span").text(res.data);
			} else if (res.success == "false") {
				if (res.errorCode == "30002") {
					layer.msg(res.msg + "，即将跳转到登录页面");
					setTimeout(function () {
						location.href = getRootPath() + "/login.html";
					}, 2000);
				}else{
					layer.msg(res.msg);
				}
			}
		}
	});
	/*$.get("../json/newsList.json",
		function(data){
			var waitNews = [];
			$(".allNews span").text(data.length);  //文章总数
			for(var i=0;i<data.length;i++){
				var newsStr = data[i];
				if(newsStr["newsStatus"] == "待审核"){
					waitNews.push(newsStr);
				}
			}
			$(".waitNews span").text(waitNews.length);  //待审核文章
			//加载最新文章
			var hotNewsHtml = '';
			for(var i=0;i<5;i++){
				hotNewsHtml += '<tr>'
					+'<td align="left">'+data[i].newsName+'</td>'
					+'<td>'+data[i].newsTime+'</td>'
					+'</tr>';
			}
			$(".hot_news").html(hotNewsHtml);
		}
	)
*/
	//学员总数
	$.ajax({
		url:getRootPath()+"/api/getAllUserCount",
		type:'get',
		headers:{
			'token':$.cookie("token")
		},
		data:{'userRole':1},
		success:function (res) {
			if (res.success == "true") {
				/*$(".imgAll span").text(res.data);*/
				$(".userAll span").text(res.data);
			} else if (res.success == "false") {
				if (res.errorCode == "30002") {
					layer.msg(res.msg + "，即将跳转到登录页面");
					setTimeout(function () {
						location.href = getRootPath() + "/login.html";
					}, 2000);
				}else{
					layer.msg(res.msg);
				}
			}
		}
	});
	//教练总数
	$.ajax({
		url:getRootPath()+"/api/getAllUserCount",
		type:'get',
		headers:{
			'token':$.cookie("token")
		},
		data:{'userRole':2},
		success:function (res) {
			if (res.success == "true") {
				$(".coachAll span").text(res.data);
			} else if (res.success == "false") {
				if (res.errorCode == "30002") {
					layer.msg(res.msg + "，即将跳转到登录页面");
					setTimeout(function () {
						location.href = getRootPath() + "/login.html";
					}, 2000);
				}else{
					layer.msg(res.msg);
				}
			}
		}
	});
	//总预约数
	$.ajax({
		url:getRootPath()+"/api/getAllSubscribeCount",
		type:'get',
		headers:{
			'token':$.cookie("token")
		},
		data:{
			'userRole':2,
			"status":1
		},
		success:function (res) {
			if (res.success == "true") {
				$(".imgAll span").text(res.data);
			} else if (res.success == "false") {
				if (res.errorCode == "30002") {
					layer.msg(res.msg + "，即将跳转到登录页面");
					setTimeout(function () {
						location.href = getRootPath() + "/login.html";
					}, 2000);
				}else{
					layer.msg(res.msg);
				}
			}
		}
	});

	//数字格式化
	$(".panel span").each(function(){
		$(this).html($(this).text()>9999 ? ($(this).text()/10000).toFixed(2) + "<em>万</em>" : $(this).text());
	})

	//系统基本参数
/*	if(window.sessionStorage.getItem("systemParameter")){
		var systemParameter = JSON.parse(window.sessionStorage.getItem("systemParameter"));
		fillParameter(systemParameter);
	}else{
		$.ajax({
			/!*url : "../json/systemParameter.json",*!/
			type : "get",
			dataType : "json",
			success : function(data){
				fillParameter(data);
			}
		})
	}*/
	//填充数据方法
	//利用原生Js获取操作系统版本
	function getOS() {
		var sUserAgent = navigator.userAgent;
		var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
		var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");
		if (isMac) return "Mac";
		var isUnix = (navigator.platform == "X11") && !isWin && !isMac;
		if (isUnix) return "Unix";
		var isLinux = (String(navigator.platform).indexOf("Linux") > -1);
		if (isLinux) return "Linux";
		if (isWin) {
			var isWin2K = sUserAgent.indexOf("Windows NT 5.0") > -1 || sUserAgent.indexOf("Windows 2000") > -1;
			if (isWin2K) return "Win2000";
			var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1 || sUserAgent.indexOf("Windows XP") > -1;
			if (isWinXP) return "WinXP";
			var isWin2003 = sUserAgent.indexOf("Windows NT 5.2") > -1 || sUserAgent.indexOf("Windows 2003") > -1;
			if (isWin2003) return "Win2003";
			var isWinVista= sUserAgent.indexOf("Windows NT 6.0") > -1 || sUserAgent.indexOf("Windows Vista") > -1;
			if (isWinVista) return "WinVista";
			var isWin7 = sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows 7") > -1;
			if (isWin7) return "Win7";
			var isWin10 = sUserAgent.indexOf("Windows NT 10") > -1 || sUserAgent.indexOf("Windows 10") > -1;
			if (isWin10) return "Win10";
		}
		return "other";
	}
	$(".version").text("v1.0.0");
	$(".server").text(getOS);//当前系统
	$(".author").text("@向一");//作者
	$(".homePage").text("index.html");
	$(".dataBase").text("mysql");
	/*function fillParameter(data){
		//判断字段数据是否存在
		function nullData(data){
			if(data == '' || data == "undefined"){
				return "未定义";
			}else{
				return data;
			}
		}
		      //当前版本
		$(".author").text(nullData(data.author));        //开发作者
		   //网站首页
		$(".server").text(nullData(data.server));        //服务器环境
		    //数据库版本
		$(".maxUpload").text(nullData(data.maxUpload));    //最大上传限制
		$(".userRights").text(nullData(data.userRights));//当前用户权限
	}*/


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

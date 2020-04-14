<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
   <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="<%=basePath%>">
<title>华为后台管理</title>
<%@include file="link.jsp" %>
</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<div class="layui-logo">CRM客户管理</div>
			<ul class="layui-nav layui-layout-left">
			</ul>
			<ul class="layui-nav layui-layout-right">
				<li class="layui-nav-item"><a href="javascript:;"> 
						<span id="sys_name"></span>
				</a>
					<dl class="layui-nav-child">
						<dd id="revise" style="cursor: pointer">
							<a>修改密码</a>
						</dd>
					</dl></li>
				<li class="layui-nav-item"><a href="sysuserout">退出登录</a></li>
			</ul>
		</div>

		<div class="layui-side layui-bg-black">
			<div class="layui-side-scroll">
				<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
				<ul class="layui-nav layui-nav-tree" lay-filter="test">
					<li class="layui-nav-item"><a href="javascript:;"
						page="">权限管理</a></li>
					<li class="layui-nav-item"><a href="javascript:;"
						page="sys/role">角色管理</a></li>
				</ul>
			</div>
		</div>

		<div class="layui-body">
			<!-- 内容主体区域 -->
			<div id="tianqi">
					<iframe scrolling="no"
				height="80" frameborder="0" allowtransparency="true"
				src="//i.tianqi.com/index.php?c=code&id=8&bdc=%23FFFFFF&icon=1&num=3&site=15"></iframe>
				</div>
			<iframe  id="iframe" src="" style="display:none;height: 98%; width: 100%">
			</iframe>
		</div>
		<div class="layui-footer">
			<div id="time">当前时间:</div>
		</div>
	</div>

	<div hidden id="revisepoen">
		<div class="layui-form-item">
			<div class="layui-inline" style="margin-top: 25px" id="jiumima">
				<div id="flase1"></div>
				<label class="layui-form-label">旧密码</label>
				<div class="layui-input-inline">
					<input type="password" id="jiurevise" autocomplete="off"
						class="layui-input" lay-verify="required">
				</div>
			</div>
			<div class="layui-inline" style="margin-top: 25px">
				<div id="false2"></div>
				<label class="layui-form-label">新密码</label>
				<div class="layui-input-inline">
					<input type="password" id="xinrevise" autocomplete="off"
						class="layui-input" lay-verify="required">
				</div>
			</div>
			<div class="layui-inline" style="margin-top: 25px">
				<label class="layui-form-label">确认密码</label>
				<div class="layui-input-inline">
					<input type="password" id="qrrevise" autocomplete="off"
						class="layui-input" lay-verify="required">
				</div>
			</div>
		</div>
	</div>
<%@include file="script.jsp" %>
	<script>
		$("[page]").click(function() {
			$("#iframe").css("display","block");
			$("#iframe").attr("src", $(this).attr("page"));
			$("#tianqi").attr({hidden:'hidden'});
		});
		/* $.post("system/onlinecount", function(d) {
			$("#zaixian").text("在线人数 " + d)
		}); */
		//点击修改密码，弹出一个poen框
		$("#revise").click(function() {
			$("input").val("");
			$("#flase1").html("");
			$("#false2").html("");
			layer.open({
				type : 1,
				title : '修改密码',
				content : $("#revisepoen"),
				btn : [ '确定', '取消' ],
				btn1 : function(index, layero) {
					$("#flase1").html("");
					$("#false2").html("");
					$.post("system/revise", {
						"jiurevise" : $("#jiurevise").val(),
						"xinrevise" : $("#xinrevise").val(),
						"qrrevise" : $("#qrrevise").val(),
					}, function(d) {
						console.log(d);
						if (d == 'false1') {
							//旧密码输入不正确
							$("#flase1").html("旧密码输入不正确");
							$("input").val("");
						} else if (d == 'false2') {
							//两次密码输入不一致
							$("#false2").html("两次密码输入不一致");
							$("input").val("");
						} else {
							//密码修改成功，给个提示信息,跳转到登陆页面
							//关闭open弹出框
							layer.close(index);
							layer.msg('修改密码成功，即将跳转登陆页面', {
								icon : 0,
								time : 6000
							}, function() {
								window.location.href = "syslogin";
							});
						}

					});
				},
				btn2 : function() {
					layer.closeAll();
				}

			});
		});
		/* $.post("system/time", function(d) {
			$("#time").append(d);
		}); */
	/* 	$.post("system/sys_getphone",function(d){
			var data=d.data[0];
			$("#sys_name").html(data.sys_name);
			$("#sysuser_img").attr("src","stataic/file/"+data.sysuser_img);
		},"json");
		if('${sysuser.sys_lasttime}'==""){
			//说明没有登陆过，要先修改密码
			$("input").val("");
			$("#flase1").html("");
			$("#false2").html("");
			layer.open({
				type : 1,
				title : '修改密码',
				content : $("#revisepoen"),
				btn : [ '确定', '取消' ],
				btn1 : function(index, layero) {
					$("#flase1").html("");
					$("#false2").html("");
					$.post("system/revise", {
						"jiurevise" : $("#jiurevise").val(),
						"xinrevise" : $("#xinrevise").val(),
						"qrrevise" : $("#qrrevise").val(),
					}, function(d) {
						console.log(d);
						if (d == 'false1') {
							//旧密码输入不正确
							$("#flase1").html("旧密码输入不正确");
							$("input").val("");
						} else if (d == 'false2') {
							//两次密码输入不一致
							$("#false2").html("两次密码输入不一致");
							$("input").val("");
						} else {
							//密码修改成功，给个提示信息,跳转到登陆页面
							//关闭open弹出框
							layer.close(index);
							layer.msg('修改密码成功，即将跳转登陆页面', {
								icon : 0,
								time : 6000
							}, function() {
								window.location.href = "syslogin";
							});
						}

					});
				},
				btn2 : function() {
					layer.closeAll();
				}

			});
		} */
	</script>
</body>
</html>
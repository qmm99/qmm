<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
   <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!-- shiro提供的JSTL标签 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="<%=basePath%>">
<title>Insert title here</title>
<%@include file="link.jsp" %>
</head>
<body>
<script type="text/html" id="toolbarDemo">
		<input id="check" type="text" autocomplete="off" style="height:38px;">
		<button class="layui-btn layui-icon layui-icon-search" lay-event="select">查询</button>
		<button class="layui-btn layui-icon layui-icon-add-1" lay-event="add">新增</button>
		<button class="layui-btn" lay-event="quanxian">分配权限</button>
		<shiro:hasPermission name="user_del">
		<button class="layui-btn layui-icon layui-icon-close" lay-event="del">删除</button>
		</shiro:hasPermission>		
		<button class="layui-btn layui-icon layui-icon-refresh" lay-event="select">刷新</button>
</script>
<script type="text/html" id="barDemo">
	<a class="layui-btn layui-btn-xs layui-icon layui-icon-edit" lay-event="update">编辑</a>
	<a class="layui-btn layui-btn-xs" lay-event="">权限</a>
	<a class="layui-btn layui-btn-xs layui-icon layui-icon-close layui-bg-gray" lay-event="del">删除</a>
</script>
<table class="layui-table"
		lay-data="{url:'allrole',toolbar:'#toolbarDemo',id:'roletable'}"
		lay-filter="filter">
		<thead>
			<th lay-data="{type:'checkbox'}"></th>
			<th lay-data="{field:'role_id'}">角色编号</th>
			<th lay-data="{field:'role_name'}">角色名称</th>
			<th lay-data="{toolbar: '#barDemo'}">操作</th>
		</thead>
	</table>
<div hidden id="div_hidden">
		<div class="layui-form-item">
			<div class="layui-inline" style="margin-top: 25px;">
				<label class="layui-form-label" >角色名称</label>
				<div class="layui-input-inline">
					<input type="text" id="role_name"
						autocomplete="off" class="layui-input" lay-verify="required">
				</div>
			</div>
		</div>
	</div>
<%@include file="script.jsp" %>
	<script type="text/javascript">
	table.on('toolbar(filter)',function(obj){
		var checkStatus=table.checkStatus('roletable');
		var data=checkStatus.data;
		switch(obj.event){
		case 'select':
			//查询方法
			table.reload('roletable',{
				url:'allrole',
				method:'post',
				where:{
					"check":$("#check").val()
				}
			});
			break;
		case 'quanxian':
			//分配权限
			console.log(data[0].role_id);
			window.location.href="quanxian/"+data[0].role_id+"";
			//window.location.href="quanxian";
			break;
		case 'add':
			//增加方法
			$("input").val("");
			layer.open({
				type:1,
				title:'增加用户角色信息',
				content:$("#div_hidden"),
				btn:['确定','取消'],
				btn1:function(){
					$.post("addrole",{
						"role_name":$("#role_name").val()
					},function(){
						layer.closeAll();
						layer.msg("添加成功");
						table.reload('roletable');
					});
				},btn2:function(){
					//点击取消
					//关闭弹出框
					layer.closeAll();
				}
			});
			break;
		case 'del':
			//删除方法
			if(data.length>0){
				//已选中数据
				//创建一个数组
				var role_ids=new Array();
				for(var i=0;i<data.length;i++){
					role_ids[i]=data[i].role_id;
				}
				$.post("delrole",{
					"role_ids":role_ids,
				},function(d){
					layer.msg("删除成功");
					//重载表格
					table.reload('roletable');
				});
			}else{
				//未选中数据
				layer.msg("请先选中数据");
			}
			break;
		}
	});
	table.on('tool(filter)',function(obj){
		var data = obj.data;//获取当前行数据
		switch(obj.event){
		case 'update':
			//编辑方法
			$("#role_name").val(data.role_name);
				layer.open({
					type:1,
					title:'编辑【'+data.role_name+'】商品分类信息',
					content:$("#div_hidden"),
					btn:['确定','取消'],
					btn1:function(){
						$.post("uprole",{
							"role_id":data.role_id,
							"role_name":$("#role_name").val()
						},function(){
							layer.closeAll();
							table.reload('roletable');
							layer.msg("编辑成功");
						});
					},btn2:function(){
						//关闭所有弹出框
						layer.closeAll();
					}
				});
			break;
		case 'del':
			//删除方法
			//创建一个数组
			layer.confirm('是否删除【'+data.role_name+'】用户角色', function(index){
				var role_ids=new Array();
				role_ids[0]=data.role_id;
				$.post("delrole",{
					"role_ids":role_ids,
				},function(d){
					layer.msg("删除成功");
					//重载表格
					table.reload('roletable');
				});
			});
			break;
		}
	});
</script>
</body>
</html>
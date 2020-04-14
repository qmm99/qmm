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
<title>Insert title here</title>
<%@include file="link.jsp" %>
</head>
<body>
<div id="test1" class="demo-tree demo-tree-box"></div>
<div>
<button type="button" class="layui-btn baocun">保存</button>
<a href="role" style="color: white;"><button type="button" class="layui-btn">返回</button></a>
</div>
<input type="hidden" name="role_id" value="${role_id }">
<%@include file="script.jsp" %>
<script type="text/javascript">
var tree=layui.tree; 
var roleTree; 
var perm_ids=new Array();
var oldperm_ids=new Array();
var i=0;
$.ajaxSettings.async=false;
$.post("role_treeJson",function(d){
	console.log(d);
	roleTree=d;
});
tree.render({
  id:'perm_tree',
  elem: '#test1',
  showCheckbox:true,
  data: roleTree
});
//给保存按钮声明一个点击事件
$(".baocun").click(function(){
	var role_id=$("[name=role_id]").val();
	var checkData = tree.getChecked('perm_tree');
	var perms="";
	for(var i=0;i<checkData.length;i++){
		var children=checkData[i].children;
		for(var j=0;j<children.length;j++){
			perms+=children[j].id+",";
		}
	}
	console.log(checkData);
	console.log(role_id);
	location.href="role_perm/"+role_id+"/"+perms;
});
</script>
</body>
</html>
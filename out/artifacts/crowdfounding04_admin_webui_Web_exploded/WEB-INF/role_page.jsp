<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my_role.js"></script>
<script type="text/javascript">
    $(function () {
        window.pageNum = 1;
        window.pageSize =5;
        window.keyword ="";

        generatePage();

        $("#searchBtn").click(function(){
// ①获取关键词数据赋值给对应的全局变量
            window.keyword = $("#keywordInput").val();
// ②调用分页函数刷新页面
            generatePage();
        });

        $("#showAddModalBtn").click(function(){
            $("#addModal").modal("show");
        });

        $("#saveRoleBtn").click(function(){
// ①获取用户在文本框中输入的角色名称
// #addModal 表示找到整个模态框
// 空格表示在后代元素中继续查找
// [name=roleName]表示匹配 name 属性等于 roleName 的元素
            var roleName = $.trim($("#addModal [name=roleName]").val());
// ②发送 Ajax 请求
            $.ajax({ "url": "role/save.json",
                "type":"post",
                "data": { "name": roleName},
                "dataType": "json",
                "success":function(response){
                    var result = response.result;
                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");
// 将页码定位到最后一页
                        window.pageNum = 99999999;
// 重新加载分页数据
                        generatePage();
                    }
                    if(result == "FAILED") {
                        layer.msg("操作失败！"+response.message);
                    }
                },"error":function(response){
                    layer.msg(response.status+" "+response.statusText);
                }
            });
// 关闭模态框
            $("#addModal").modal("hide");
// 清理模态框
            $("#addModal [name=roleName]").val("");
        })

        // 6.给页面上的“铅笔”按钮绑定单击响应函数，目的是打开模态框
// 传统的事件绑定方式只能在第一个页面有效，翻页后失效了
// $(".pencilBtn").click(function(){
// alert("aaaa...");
// });
// 使用 jQuery 对象的 on()函数可以解决上面问题
// ①首先找到所有“动态生成”的元素所附着的“静态”元素
// ②on()函数的第一个参数是事件类型
// ③on()函数的第二个参数是找到真正要绑定事件的元素的选择器
// ③on()函数的第三个参数是事件的响应函数
        $("#rolePageBody").on("click",".pencilBtn",function(){
// 打开模态框
            $("#editModal").modal("show");
// 获取表格中当前行中的角色名称
            var roleName = $(this).parent().prev().text();
// 获取当前角色的 id
// 依据是：var pencilBtn = "<button id='"+roleId+"' ……这段代码中我们把 roleId 设置到id 属性了
// 为了让执行更新的按钮能够获取到 roleId 的值，把它放在全局变量上
            window.roleId = this.id;
// 使用 roleName 的值设置模态框中的文本框
            $("#editModal [name=roleName]").val(roleName);
        });


        // 7.给更新模态框中的更新按钮绑定单击响应函数
        $("#editRoleBtn").click(function(){
// ①从文本框中获取新的角色名称
            var roleName = $("#editModal [name=roleName]").val();
// ②发送 Ajax 请求执行更新
            $.ajax({ "url":"role/update.json", "type":"post",
                "data":{ "id":window.roleId,
                    "name":roleName
                },"dataType":"json",
                "success":function(response){
                    var result = response.result;
                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");
// 重新加载分页数据
                        generatePage();
                    }
                    if(result == "FAILED") {
                        layer.msg("操作失败！"+response.message);
                    }
                },"error":function(response){
                    layer.msg(response.status+" "+response.statusText);
                }
            });
// ③关闭模态框
            $("#editModal").modal("hide");
        });

        $("#confirmModalBtn").click(function(){
            $("#confirmModal").modal("show");
        });
        // var roleArray = [{roleId:5,roleName:"aaa"},{roleId:5,roleName:"bbb"}];
        // showConfirmModal(roleArray);


        $("#removeRoleBtn").click(function()
        {
            var requestBody = JSON.stringify(window.roleIdArray);
            alert(requestBody);
            $.ajax(
                {
                    "url":"role/delete/by/role/id/array.json",
                    "type":"post",
                    "data":requestBody,
                    "contentType":"application/json;charset=UTF-8",
                    "dataType":"json",
                    "success":function(response)
                    {
                        var result = response.result;
                        if(result == "SUCCESS") {
                            layer.msg("操作成功！");
// 重新加载分页数据
                            generatePage();
                        }
                        if(result == "FAILED") {
                            layer.msg("操作失败！"+response.message);
                        }
                    },"error":function(response){
                        layer.msg(response.status+" "+response.statusText);
                        alert(response.status+" "+response.statusText);
                    }
                }
            )
            $("#confirmModal").modal("hide");
        });

        $("#rolePageBody").on("click",".removeBtn",function(){
            var roleName = $(this).parent().prev().text();

            var roleArray =[{
                roleId:this.id,
                roleName:roleName
            }];
            alert("显示获取到的roleName"+roleName);
            showConfirmModal(roleArray);
        });

        $("#summaryBox").click(function()
        {
            var currentStatus = this.checked;

            $(".itemBox").prop("checked",currentStatus);
        });

        //全选或者全不选
        $("#rolePageBody").on("click",".itemBox",function(){
            var checkedBoxCount = $(".itemBox:checked").length;

            var totalBoxCount = $(".itemBox").length;

            $("#summaryBox").prop("checked",checkedBoxCount==totalBoxCount);

        });

        $("#batchRemoveBtn").click(function () {

            var roleArray = [];
            $(".itemBox:checked").each(function () {

                var roleId = this.id;

               var roleName = $(this).parent().next().text();

                roleArray.push(
                    {
                        "roleId":roleId,
                        "roleName":roleName
                    }
                )
            });
            if(roleArray.length==0)
            {
                layer.msg("请至少选择一个！执行删除");
                return ;
            }
            showConfirmModal(roleArray);
        });



        // 13.给分配权限按钮绑定单击响应函数
        $("#rolePageBody").on("click",".checkBtn",function(){
// 打开模态框
            $("#assignModal").modal("show");
// 在模态框中装载树 Auth 的形结构数据
            fillAuthTree();
        });

        $("#assignBtn").click(function(){
// ①收集树形结构的各个节点中被勾选的节点
// [1]声明一个专门的数组存放 id
            var authIdArray = [];
// [2]获取 zTreeObj 对象
            var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
// [3]获取全部被勾选的节点
            var checkedNodes = zTreeObj.getCheckedNodes();
// [4]遍历 checkedNodes
            for(var i = 0; i < checkedNodes.length; i++) {
                var checkedNode = checkedNodes[i];
                var authId = checkedNode.id;
                authIdArray.push(authId);
            }
// ②发送请求执行分配
            var requestBody = {
                "authIdArray":authIdArray, // 为了服务器端 handler 方法能够统一使用 List<Integer>方式接收数据，roleId 也存入数组
                "roleId":[window.roleId]
            };
            requestBody = JSON.stringify(requestBody);
            $.ajax({
                "url":"assign/do/role/assign/auth.json",
                "type":"post", "data":requestBody,
                "contentType":"application/json;charset=UTF-8",
                "dataType":"json",
                "success":function(response){
                    var result = response.result;
                    if(result == "SUCCESS") {
                        layer.msg("操作成功！");
                    }
                    if(result == "FAILED") {
                        layer.msg("操作失败！"+response.message);
                    }
                },"error":function(response) {
                    layer.msg(response.status+" "+response.statusText);
                }
            });
            $("#assignModal").modal("hide");
        });
    });
</script>
<body>
<%@ include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id = "keywordInput" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id = "searchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button id="showAddModalBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                           <tbody id="rolePageBody">
                           <tfoot>
                           <tr>
                               <td colspan="6" align="center">
                                   <div id="Pagination" class="pagination"><!--这里显示分页--></div>
                               </td>
                           </tr>
                        </tfoot>
                           </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal_role_add.jsp" %>
<%@include file="/WEB-INF/modal_role_edit.jsp" %>
<%@include file="/WEB-INF/modal_role_confirm.jsp" %>
<%@include file="/WEB-INF/modal_role_assign_auth.jsp" %>
</body>
</html>
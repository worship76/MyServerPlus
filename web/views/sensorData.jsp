<%--
  Created by IntelliJ IDEA.
  User: 话长更
  Date: 2020/4/3
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

    <script type="text/javascript">

        function searchSensor() {
            $("#dg").datagrid('load', {
                "date": $("#s_date").val(),
                "sid": $("#s_sid").val()
            });
        }


        function deleteDate() {
            var selectedRows = $("#dg").datagrid('getSelections');
            if (selectedRows.length == 0) {
                $.messager.alert("系统提示", "请选择要删除的数据！");
                return;
            }

            var strIds = [];
            for (var i = 0; i < selectedRows.length; i++) {
                strIds.push(selectedRows[i].id);
            }
            var ids = strIds.join(",");
            $.messager.confirm("系统提示", "您确认要删除这<font color=red>"
                + selectedRows.length + "</font>条数据吗？", function (r) {
                if (r) {
                    $.post("${pageContext.request.contextPath}/sensor/delete.do", {
                        ids: ids
                    }, function (result) {
                        if (result.success) {
                            $.messager.alert("系统提示", "数据已成功删除！");
                            $("#dg").datagrid("reload");
                        } else {
                            $.messager.alert("系统提示", "数据删除失败！");
                        }
                    }, "json");
                }
            });
        }

    </script>

</head>
<body style="margin: 1px">
    <table id="dg" title="传感器(表)" class="easyui-datagrid" fitColumns="true"
       pagination="true" rownumbers="true"
       url="${pageContext.request.contextPath}/sensor/all.do" fit="true"
       toolbar="#tb" border="1">
        <thead>
        <tr>
            <th field="cb" checkbox="true" align="center"></th>
            <th field="id" align="center" >编号</th>
            <th field="date"  align="center" >日期</th>
            <th field="time"  align="center" >时间</th>
            <th field="sid" align="center" >传感器</th>
            <th field="smoke" align="center" >烟雾/%</th>
            <th field="temperature" align="center" >温度/°C</th>
        </tr>
        </thead>
    </table>
    <div id="tb">
        <div>
        <a
            href="javascript:deleteDate()" class="easyui-linkbutton"
            iconCls="icon-remove" plain="true">删除</a>
    </div>
        <div>
            日期：&nbsp;
            <input type="text" id="s_date" size="20"
               onkeydown="if(event.keyCode==13) searchSensor()"/>
            传感器：&nbsp;
            <input type="text" id="s_sid" size="20"
               onkeydown="if(event.keyCode==13) searchSensor()"/>
            <a href="javascript:searchSensor()"
           class="easyui-linkbutton"
           iconCls="icon-search" plain="true">搜索</a>
        </div>
    </div>
</body>
</html>

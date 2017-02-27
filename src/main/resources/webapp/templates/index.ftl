<html>  
<body>  
      
    <h3>用户列表:</h3>  
      
    <table border="1px solid #8968CD" style="border-collapse: collapse;">
    <tr>
    	<th>序号</th>
    	<th>名称</th>
    	<th>电话</th>
	</tr>  
    <#list userList as user>  
        <tr>  
            <td>${user.uid}</td>  
            <td>${user.uname}</td>  
            <td>${user.uphone}</td>  
        </tr>  
    </#list>  
    </table>  
      
</body>  
</html>  
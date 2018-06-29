<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- Import da taglib -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lista de usuários</title>
</head>
<body>	
    <p>${mensagem}</p>
	<table>
    <tr>
        <td>Id</td>
        <td>Nome</td>
        <td>E-mail</td>
    </tr>

    <c:forEach items="${usuarios}" var="usuario">
        <tr>
            <td>${usuario.id}</td>
            <td>${usuario.nome}</td>
            <td>${usuario.email}</td>
        </tr>
    </c:forEach>
</table>
	
	<div>
	<p><a href="/exemplo-spring-mvc-basico">Voltar</a></p>
	</div>	
</body>
</html>
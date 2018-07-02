<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Inserir Usuário</title>
</head>
<body>
	<div>
		<form action="inserirUsuario" id="forminsereusuario" method="post" name="forminsereusuario">
			<p>Inserir usuário</p> 
			Nome: <input name="nome" type="text"><form:errors path="usuario.nome" /><br>
			Login: <input name="login" type="text"><form:errors path="usuario.login"/><br>
			E-mail: <input name="email" type="text"><form:errors path="usuario.email" /><br>
			Senha: <input name="senha" type="text"><form:errors path="usuario.senha" /><br>
			<p>
				<input name="botaoInserir" type="submit" value="Inserir" />
			</p>
		</form>
	</div>
	<div>
	<p><a href="/exemplo-spring-mvc-basico">Voltar</a></p>
	</div>
</body>
</html>
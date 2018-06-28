<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Alterar Usuário</title>
</head>
<body>
	<div>
		<form action="alterarUsuario" id="formalterausuario" method="post" name="formalterausuario">
			<p>Alterar usuário</p>
			Nome: <input name="nome" type="text"><br>
			Login: <input name="login" type="text"><br>
			E-mail: <input name="email" type="text"><br>
			Senha: <input name="senha" type="text"><br>
			<p>
				<input name="botaoalterar" type="submit" value="Alterar" />
			</p>
		</form>
	</div>
	<div>
	<p><a href="/exemplo-spring-mvc-basico">Voltar</a></p>
	</div>
</body>
</html>
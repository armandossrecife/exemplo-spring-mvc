package br.ufpi.es.exemplo_spring_mvc_basico.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ufpi.es.exemplo_spring_mvc_basico.modelo.Usuario;

@Controller
public class AcessoController {
	
	//recurso1
	@RequestMapping(value="/efetuarLogin", method=RequestMethod.POST)
	public String processarLogin(Usuario usuario, HttpSession session, Model model){
		String email;
		String senha;
		
		email = usuario.getEmail();
		senha = usuario.getSenha();
		
		if (email.equals("armando@ufpi.edu.br") && senha.equals("123")){
			session.setAttribute("usuarioLogado", email);
			model.addAttribute("mensagem", "Bem vindo " + email);
			System.out.println("Usuario " + email + " logado com sucess!");
			return "pagina-principal";
		}else{
			model.addAttribute("mensagem", "Erro: usuario ou senha!");
			return "formulario-login";
		}
		
	}
	
	//recurso2
	@RequestMapping(value="/formularioLogin")
	public String carregarFormularioLogin(){
		return "formulario-login";
	}
	
	//recurso3
	@RequestMapping(value="/logout")
	public String processarLogout(HttpSession session) {
		String nomeUsuario;
		
		nomeUsuario = session.getAttribute("usuarioLogado").toString();
		session.invalidate();
		System.out.println("Usario " + nomeUsuario + " deslogado");
		return "formulario-login";
	}
}

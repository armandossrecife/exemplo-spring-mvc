package br.ufpi.es.exemplo_spring_mvc_basico.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.ufpi.es.exemplo_spring_mvc_basico.modelo.Usuario;

@Controller
public class AcessoController {
	
	//recurso 1
	@RequestMapping(value="/")
	public ModelAndView home(HttpSession session) throws IOException{
		if (session.getAttribute("usuario") != null){
			return new ModelAndView("pagina-principal");
		}else{
			return new ModelAndView("home");
		}
	}
	
	//recurso2
	@RequestMapping(value="/efetuarLogin", method=RequestMethod.POST)
	public String processarLogin(Usuario usuario, HttpSession session, Model model){
		String email;
		String senha;
		
		email = usuario.getEmail();
		senha = usuario.getSenha();
		
		if (email.equals("armando@ufpi.edu.br") && senha.equals("123")){
			session.setAttribute("usuarioLogado", email);
			session.setAttribute("usuario", email);
			model.addAttribute("mensagem", "Bem vindo " + email);
			System.out.println("Usuario " + email + " logado com sucess!");
			return "pagina-principal";
		}else{
			model.addAttribute("mensagem", "Erro: usuario ou senha!");
			return "formulario-login";
		}
		
	}
	
	//recurso3
	@RequestMapping(value="/formularioLogin")
	public String carregarFormularioLogin(){
		return "formulario-login";
	}
	
	//recurso4
	@RequestMapping(value="/logout")
	public String processarLogout(HttpSession session) {
		String nomeUsuario;
		
		nomeUsuario = session.getAttribute("usuarioLogado").toString();
		session.invalidate();
		System.out.println("Usario " + nomeUsuario + " deslogado");
		return "formulario-login";
	}
}
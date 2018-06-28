package br.ufpi.es.exemplo_spring_mvc_basico.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.ufpi.es.exemplo_spring_mvc_basico.dados.RepositorioListaUsuarios;
import br.ufpi.es.exemplo_spring_mvc_basico.dados.UsuarioDAO;
import br.ufpi.es.exemplo_spring_mvc_basico.modelo.Usuario;

@Controller
public class AcessoController {
	private RepositorioListaUsuarios repositorio;
	private UsuarioDAO controladorDados;
	
	/**
	 * Construtor
	 */
	public AcessoController(){
		this.iniciaControladorDados();
	}
	
	/**
	 * Inicializa e carrega os dados padrões do controlador
	 */
	public void iniciaControladorDados(){
        repositorio = new RepositorioListaUsuarios();
        repositorio.populaUsuarios();
        controladorDados = new UsuarioDAO(repositorio);
	}
	
	/**
	 * Página principal da aplicação
	 * @param session Session do usuário da aplicação
	 * @return TelaPrincipal.jsp | Home.jsp
	 */
	//recurso 1
	@RequestMapping(value="/")
	public ModelAndView home(HttpSession session){
		if (session.getAttribute("usuario") != null){
			return new ModelAndView("TelaPrincipal");
		}else{
			return new ModelAndView("Home");
		}
	}
	
	/**
	 * Processa o login do usuário 
	 * @param usuario Dados do usuário
	 * @param session Session do usuário da aplicação
	 * @param model Model da aplicação
	 * @return página TelaPrincipal.jsp | TelaLogin.jsp
	 */
	//recurso2
	@RequestMapping(value="/efetuarLogin", method=RequestMethod.POST)
	public ModelAndView processarLogin(Usuario usuario, HttpSession session, Model model){
		String email;
		String senha;
		Usuario usuarioAux;
		
		email = usuario.getEmail();
		senha = usuario.getSenha();
		usuarioAux = controladorDados.buscarPorEmail(email, senha);
		
		if (usuarioAux != null){
			session.setAttribute("usuarioLogado", email);
			session.setAttribute("usuario", usuarioAux);
			model.addAttribute("mensagem", "Bem vindo " + email);
			System.out.println("Usuario " + email + " logado com sucesso!");
			return new ModelAndView("TelaPrincipal");
		}else{
			model.addAttribute("mensagem", "Erro: usuario ou senha!");
			return new ModelAndView("TelaLogin");
		}
	}
	
	/**
	 * Carrega o formulário de login da aplicação
	 * @return página TelaLogin.jsp
	 */
	//recurso3
	@RequestMapping(value="/formularioLogin")
	public ModelAndView carregarFormularioLogin(){
		return new ModelAndView("TelaLogin");
	}
	
	/**
	 * Faz o logout e encerramento da sessão do usuário
	 * @param session Session do usuário
	 * @return página TelaLogin.jsp
	 */
	//recurso4
	@RequestMapping(value="/logout")
	public ModelAndView processarLogout(HttpSession session) {
		String nomeUsuario;
		
		nomeUsuario = session.getAttribute("usuarioLogado").toString();
		session.invalidate();
		System.out.println("Usario " + nomeUsuario + " deslogado");
		return new ModelAndView("TelaLogin");
	}
}
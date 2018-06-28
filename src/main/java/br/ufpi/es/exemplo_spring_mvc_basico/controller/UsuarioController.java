package br.ufpi.es.exemplo_spring_mvc_basico.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
public class UsuarioController {
	private static final long serialVersionUID = 1L;
	private RepositorioListaUsuarios repositorio;
	private UsuarioDAO controladorDados;
	
	/**
	 * Contrutor
	 */
	public UsuarioController(){
		this.iniciaControladorDados();
	}
	
	/**
	 * Carrega o repositório de usuários para ser manipulado
	 */
	public void iniciaControladorDados(){
        repositorio = new RepositorioListaUsuarios();
        repositorio.populaUsuarios();
        controladorDados = new UsuarioDAO(repositorio);
	}
	
	//recurso1
	/**
	 * Carrega a página Home
	 * @return Home.jsp
	 */
	@RequestMapping(value="/recurso1")
	public String recurso1(){
		return "Home";
	}
	
	//recurso2
	/**
	 * Faz a busca de usuários
	 * @param request HttpServletRequest do usuário 
	 * @param session HttpSession do usuário da aplicação
	 * @param model Model da aplicação
	 * @return jsp do ModelAndView da aplicação
	 * @throws ServletException exceção do tipo Servlet
	 * @throws IOException exceção do tipo IOException
	 */
	@RequestMapping(value="/buscarUsuario", method=RequestMethod.POST)
	protected ModelAndView processarBusca(HttpServletRequest request, HttpSession session, Model model) throws ServletException, IOException {
		//recupar os dados passados pelo formulario de busca
		String tipo = request.getParameter("opcaotipo");
		String conteudo = request.getParameter("conteudobusca");
		
		List<Usuario> lista = new LinkedList<Usuario>();
		
		lista = controladorDados.buscaPorConteudoETipo(conteudo, tipo);
		
		//checa se tem uma sessão válida e reencaminha a resposta para exibir o resultado da busca
		if (session.getAttribute("usuario") != null) {
			model.addAttribute("usuarios", lista);
			return new ModelAndView("usuarios/TelaListarUsuarios");
		}else {
			return new ModelAndView("Home");
		}
	}
	
	//recurso 3 
	/**
	 * Carrega o formulário de busca de usuários
	 * @param session Session do usuário da aplicação
	 * @return página TelaBuscarUsuario.jsp | Home.jsp
	 */
	@RequestMapping(value="/formularioBusca", method=RequestMethod.GET)
	public ModelAndView carregaFormularioBusca(HttpSession session){		
    	if (session.getAttribute("usuario") != null) {
    		return(new ModelAndView("usuarios/TelaBuscarUsuario"));
    	}else {
    		return(new ModelAndView("Home"));
    	}
	}
	
	//recurso 4
	/**
	 * Lista os usuários da aplicação
	 * @param session Session do usuário da aplicação
	 * @param model Model da aplicação
	 * @return página TelaListarUsuarios.jsp | Home.jsp
	 * @throws IOException trata a exceção IOException caso aconteça
	 */
	@RequestMapping(value="/listarUsuarios", method=RequestMethod.GET)
	public ModelAndView processarListaUsuarios(HttpSession session, Model model) throws IOException {
		List<Usuario> lista = controladorDados.getUsuarios();

		if (session.getAttribute("usuario") != null) {
			model.addAttribute("usuarios", lista);
			return(new ModelAndView("usuarios/TelaListarUsuarios"));
		} else {
			return(new ModelAndView("Home"));
		}
	}
	
	//recurso 5
	/**
	 * Faz a alteração dos dados de um usuário
	 * @param request HttpServletRequest da aplicação
	 * @param session Session do usuário da aplicação
	 * @param model Model da aplicação
	 * @return página TelaPrincipal.jsp | Home.jsp
	 * @throws ServletException trata a exceção ServletException caso aconteça
	 * @throws IOException trata a exceção IOException caso aconteça
	 */
	@RequestMapping(value="/alterarUsuario", method=RequestMethod.POST)
	public ModelAndView processarAlterarUsuario(HttpServletRequest request, HttpSession session, Model model) throws ServletException, IOException{
		//Carrega os dados do usuário selecionado passados pelo formularioAlterarUsuario
		String nome = request.getParameter("");
		String login = request.getParameter("");
		String email = request.getParameter("");
		String senha = request.getParameter("");
		
		Usuario novo = new Usuario();
		novo.setNome(nome);
		novo.setLogin(login);
		novo.setEmail(email);
		novo.setSenha(senha);
		
		//checa se tem uma sessão válida e reencaminha o dashboard principal
		if (session.getAttribute("usuario") != null) {
			//Com os novos dados passados chama o método alterar Usuário do DAO
			//TODO fazer a alteração de acordo com o id do usuário alterado dados originais x novos dados
			this.repositorio.alterar(null, novo);
			return new ModelAndView("TelaPrincipal");
		}else {
			return new ModelAndView("Home");
		}		
	}

	/**
	 * Carrega o formulário Alterar Usuário
	 * @param session Session do usuário da aplicação 
	 * @return página TelaAlterarUsuario.jsp | Home.jsp
	 */
	@RequestMapping(value="/formularioAlterar", method=RequestMethod.GET)
	public ModelAndView carregaFormularioAlterar(HttpSession session){		
    	if (session.getAttribute("usuario") != null) {
    		return(new ModelAndView("usuarios/TelaAlterarUsuario"));
    	}else {
    		return(new ModelAndView("Home"));
    	}
	}

	/**
	 * Carrega o formulário Inserir Usuário
	 * @param session Session do usuário da aplicação
	 * @return página TelaInserirUsuario.jsp | Home.jsp
	 */
	@RequestMapping(value="/formularioInserir", method=RequestMethod.GET)
	public String carregarFormularioInserir(HttpSession session){
		if (session.getAttribute("usuario") != null){
			return "usuarios/TelaInserirUsuario"; 
		}else{
			return "Home"; 
		}
	}

	/**
	 * 
	 * @param usuario Dados do Usuário
	 * @param session Session do usuário da aplicação
	 * @param model Model da aplicação
	 * @return página TelaPrincipal.jsp | Home.jsp
	 * @throws ServletException 
	 * @throws IOException
	 */
	@RequestMapping("/inserirUsuario")
	public ModelAndView processarInserirUsuario(Usuario usuario, HttpSession session, Model model) throws ServletException, IOException{		
		if (session.getAttribute("usuario") != null) {
			repositorio.inserir(usuario);
			model.addAttribute("mensagem", "Usuario inserido com sucesso!");
			return new ModelAndView("TelaPrincipal");
		}else {
			return new ModelAndView("Home");
		}		
	}
	
}

package br.ufpi.es.exemplo_spring_mvc_basico.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import br.ufpi.es.exemplo_spring_mvc_basico.dados.RepositorioListaUsuarios;
import br.ufpi.es.exemplo_spring_mvc_basico.dados.UsuarioDAO;
import br.ufpi.es.exemplo_spring_mvc_basico.modelo.Usuario;
import br.ufpi.es.exemplo_spring_mvc_basico.validation.UsuarioValidation;

@Controller
public class UsuarioController {
	private static final long serialVersionUID = 1L;
	private RepositorioListaUsuarios repositorio;
	private UsuarioDAO controladorDados;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.addValidators(new UsuarioValidation());
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
		List<Usuario> lista = usuarioDAO.listar();

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
	 * @param usuario Usuario da aplicação
	 * @param result BindingResult da aplicação para checar os erros
	 * @param session Session do usuário da aplicação
	 * @param redirect RedirectAttributes
	 * @return página TelaListarUsuarios.jsp | Home.jsp
	 * @throws ServletException trata a exceção ServletException caso aconteça
	 * @throws IOException trata a exceção IOException caso aconteça
	 */
	@RequestMapping(value="/alterarUsuario", method=RequestMethod.POST)
	public ModelAndView processarAlterarUsuario(@Valid Usuario usuario, BindingResult result, HttpSession session, RedirectAttributes redirectAttribute) 
			throws ServletException, IOException{
		//checa se tem uma sessão válida e reencaminha o dashboard lista de usuários
		if (session.getAttribute("usuario") != null) {
			if(result.hasErrors()){
				System.out.println((result.getFieldErrorCount("nome") > 0) ? "nome em branco!" : "campo nome ok!");
				System.out.println((result.getFieldErrorCount("login") > 0) ? "login em branco!" : "campo login ok!");
				System.out.println((result.getFieldErrorCount("email") > 0) ? "email em branco!" : "campo email ok!");
				System.out.println((result.getFieldErrorCount("senha") > 0) ? "senha em branco!" : "campo senha ok!");
		        return new ModelAndView("usuarios/TelaInserirUsuario");
		    }
			//Com os novos dados passados chama o método alterar Usuário do DAO
			//TODO fazer a alteração de acordo com o id do usuário alterado dados originais x novos dados
			this.repositorio.alterar(null, usuario);
			redirectAttribute.addFlashAttribute("mensagem", "Usuario alterado com sucesso!");
			return new ModelAndView("redirect:/listarUsuarios");
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
	 * Insere usuário
	 * @param usuario Dados do Usuário
	 * @param result BindingResult que checa os erros de entrada da interface
	 * @param session Session do usuário da aplicação
	 * @param redirect RedirectAttributes mensagem de redirect
	 * @return página TelaPrincipal.jsp | Home.jsp
	 * @throws ServletException 
	 * @throws IOException
	 */
	@RequestMapping("/inserirUsuario")
	public ModelAndView processarInserirUsuario(@Valid Usuario usuario, BindingResult result, HttpSession session, RedirectAttributes redirectAttribute) 
			throws ServletException, IOException{		
		if (session.getAttribute("usuario") != null) {
			if(result.hasErrors()){
				System.out.println((result.getFieldErrorCount("nome") > 0) ? "nome em branco!" : "campo nome ok!");
				System.out.println((result.getFieldErrorCount("login") > 0) ? "login em branco!" : "campo login ok!");
				System.out.println((result.getFieldErrorCount("email") > 0) ? "email em branco!" : "campo email ok!");
				System.out.println((result.getFieldErrorCount("senha") > 0) ? "senha em branco!" : "campo senha ok!");
		        return new ModelAndView("usuarios/TelaInserirUsuario");
		    }
			usuarioDAO.inserir(usuario);
			System.out.println("Dados do usuário inserido: " + usuario);
			redirectAttribute.addFlashAttribute("mensagem", "Usuario inserido com sucesso!");
			return new ModelAndView("redirect:/listarUsuarios");
		}else {
			return new ModelAndView("Home");
		}		
	}
	
}
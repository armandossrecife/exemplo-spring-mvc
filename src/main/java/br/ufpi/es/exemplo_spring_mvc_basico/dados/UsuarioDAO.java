package br.ufpi.es.exemplo_spring_mvc_basico.dados;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ufpi.es.exemplo_spring_mvc_basico.modelo.Usuario;

@Repository
@Transactional
public class UsuarioDAO {
	IRepositorioUsuarios repositorio;
	
	@PersistenceContext
	private EntityManager manager;
	
	/**
	 * Construtor
	 * @param tipo repositorio
	 */
	public UsuarioDAO(IRepositorioUsuarios tipo){
		this.repositorio = tipo;
	}
	
	/**
	 * Construtor default
	 */
	public UsuarioDAO(){		
	}
	
	/**
	 * Retorna todos os usuários cadastrados na lista de usuários
	 * @return lista de usuários
	 */
	public List<Usuario> getUsuarios(){
		return repositorio.listar();
	}
	
	/**
	 * Dados login e senha de um usuário checa se ele existe
	 * @param login login do usuário
	 * @param senha senha do usuário
	 * @return um Usuário se existe e null se não existe
	 */
	public Usuario buscar(String login, String senha){
		return repositorio.buscar(login, senha);
	}
	
	/**
	 * Faz uma busca de usuário de acordo com o tipo de busca escolhida
	 * @param conteudo dado do usuário
	 * @param tipo tipo nome, email ou login
	 * @return lista contendo resultado da busca
	 */
	public List<Usuario> buscaPorConteudoETipo(String conteudo, String tipo){
		return repositorio.buscarPorConteudoETipo(conteudo, tipo);
	}
	
	/**
	 * Insere um novo usuário no repositório ORM
	 * @param u dados do Usuario
	 */
	public void inserir(Usuario u) {
		manager.persist(u);
	}

	/**
	 * Faz a busca de um usuário por e-mail
	 * @param email email do usuário 
	 * @param senha senha do usuário
	 * @return Usuário localizado
	 */
	public Usuario buscarPorEmail(String email, String senha) {
		return repositorio.buscarPorEmail(email, senha);
	}
	
	/**
	 * Dado um usuário original alterar os dados do usuário
	 * @param original dados originais
	 * @param novo novos dados
	 */
	public void alterar(Usuario original, Usuario novo){
		this.repositorio.alterar(original, novo);
	}
	
	/**
	 * Dado um usuário remove o usuário
	 * @param u dados do usuario
	 */
	public void remover(Usuario u){
		this.repositorio.remover(u);
	}
	
	/**
	 * Lista de usuários cadastrados
	 * @return lista de usuários
	 */
	public List<Usuario> listar(){
		String query = "select u from Usuario u";
		return manager.createQuery(query, Usuario.class).getResultList();
	}
}
package br.ufpi.es.exemplo_spring_mvc_basico.dados;

import java.util.List;

import br.ufpi.es.exemplo_spring_mvc_basico.modelo.Usuario;

public interface IRepositorioUsuarios {
	public void inserir(Usuario u);
	public List<Usuario> listar();
	public Usuario buscar(String login, String senha);
	public void alterar(Usuario original, Usuario novo);
	public void remover(Usuario u);
	public List<Usuario> buscarPorConteudoETipo(String conteudo, String tipo);
	public Usuario buscarPorEmail(String email, String senha);
}

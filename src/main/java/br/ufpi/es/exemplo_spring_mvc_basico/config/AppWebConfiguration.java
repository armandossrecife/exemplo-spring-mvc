package br.ufpi.es.exemplo_spring_mvc_basico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.ufpi.es.exemplo_spring_mvc_basico.controller.AcessoController;
import br.ufpi.es.exemplo_spring_mvc_basico.controller.UsuarioController;
import br.ufpi.es.exemplo_spring_mvc_basico.dados.UsuarioDAO;

@Configuration
@ComponentScan(basePackageClasses={AcessoController.class, UsuarioController.class, UsuarioDAO.class})
@EnableWebMvc
public class AppWebConfiguration {
	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
}

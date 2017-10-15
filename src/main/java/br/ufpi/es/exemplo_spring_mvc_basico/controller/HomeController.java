package br.ufpi.es.exemplo_spring_mvc_basico.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value="/")
	public ModelAndView test(HttpSession session) throws IOException{
		if (session.getAttribute("usuario") != null){
			return new ModelAndView("pagina-principal");
		}else{
			return new ModelAndView("home");
		}
	}
}

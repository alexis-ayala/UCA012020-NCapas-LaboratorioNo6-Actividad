package com.uca.capas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uca.capas.dao.EstudianteDAO;
import com.uca.capas.domain.Estudiante;

@Controller
public class MainController {
	@Autowired
	private EstudianteDAO estudianteDAO;
	
	@RequestMapping("/listado")
	public ModelAndView initMain() {
		ModelAndView mav = new ModelAndView();
		List<Estudiante> estudiantes = null;
		try {
			estudiantes = estudianteDAO.findAll();
		}catch(Exception e) {
			e.printStackTrace();
		}
		mav.addObject("estudiantes",estudiantes);
		mav.setViewName("listado");
		return mav;
	}
	@RequestMapping("/registrar")
	public ModelAndView registrar(@Valid @ModelAttribute Estudiante estudiante, BindingResult result) {
		ModelAndView mav = new ModelAndView();	
		if(result.hasErrors()) {
			mav.setViewName("index");
		}else {
			try {
				estudianteDAO.insert(estudiante);
				return new ModelAndView("redirect:/inicio");
			}catch(Exception e) {
				//mav.setViewName("index");
				e.printStackTrace();
			}
		}
		return mav;
	}
	@RequestMapping("/eliminar")
	public ModelAndView eliminar(@RequestParam(required=false) Integer codigo) {
		ModelAndView mav = new ModelAndView("borrar");
		if(codigo!=null) {
			try {
				estudianteDAO.delete(codigo);
				return new ModelAndView("redirect:/inicio");
			}catch(Exception e) {
				//mav.setViewName("index");
				e.printStackTrace();
			}
		}
		
		return mav;
	}
	@RequestMapping("/inicio")
	public String producto(Estudiante estudiante) {
		return "index";
	}
	@RequestMapping("/borrar")
	public String borrar() {
		return "borrar";
	}
	@RequestMapping("/")
	public String index(Estudiante estudiante) {
		return "index";
	}
}

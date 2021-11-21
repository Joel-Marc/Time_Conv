package com.projects.Time_Conv;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class controller {
	@RequestMapping("/")
	public String index() {
		return "index";
	}

<<<<<<< HEAD
	@RequestMapping(value = "Savesearch", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute search Search) {
=======
	@RequestMapping(value = "/Savesearch", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute String Search) {
>>>>>>> fdb90a10d17b996eaf4f5dd67ebb9be53a956911
		System.out.println(Search);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Savesearch");
		modelAndView.addObject("search", Search);
		return modelAndView;
	}

}

package com.projects.Time_Conv;

import java.io.IOException;

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

	@RequestMapping(value = "/Savesearch", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute search Search) throws IOException, InterruptedException {
		System.out.println(Search + " " + Search.getSearch());
		ModelAndView modelAndView = new ModelAndView();
		Search.setSearch(Toke.testToke(Search.getSearch()));
		// Toke.testToke("07:30 am IST to PST");
		modelAndView.setViewName("Savesearch");
		modelAndView.addObject("search", Search);
		return modelAndView;
	}

}

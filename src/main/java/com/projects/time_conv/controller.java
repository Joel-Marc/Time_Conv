package com.projects.time_conv;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Controll {
	@RequestMapping("/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@PostMapping(value = "/Savesearch")
	public ModelAndView save(@ModelAttribute search sear) throws IOException, InterruptedException {
		ModelAndView modelAndView = new ModelAndView();
		sear.setSearch(Toke.testToke(sear.getSearch()));
		modelAndView.setViewName("Savesearch");
		modelAndView.addObject("search", sear);
		return modelAndView;
	}

}

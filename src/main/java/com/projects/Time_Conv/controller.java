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

	@RequestMapping(value = "/Savesearch", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute search Search) {
		System.out.println(Search);
	}

}

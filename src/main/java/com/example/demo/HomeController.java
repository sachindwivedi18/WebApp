package com.example.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping("home")
	public ModelAndView home(@RequestParam("name") String myName , HttpSession sesssion)
	{
//		System.out.println("Hi "+myName);
//		sesssion.setAttribute("name", myName);   // Setting value in session so can be fetched by JSP Page
	
		ModelAndView mv =  new ModelAndView();
		mv.addObject("name",myName);  // any values are stored in here
		
		mv.setViewName("home");  // View name is here
		
		return mv;  // Entire thing is returned in here 
	}
}
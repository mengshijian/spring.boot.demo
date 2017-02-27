package spring.boot.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import spring.boot.demo.entity.User;
import spring.boot.demo.service.UserService;

@Controller
@RequestMapping("/simple")
public class SimpleController {
	
	@Autowired
	private UserService service;
	
	@RequestMapping(value = "/userList")
	public ModelAndView index(ModelAndView model){
		List<User> list = service.findAllUser();
		model.addObject("userList", list);
		model.setViewName("index");
		return model;
	}

}

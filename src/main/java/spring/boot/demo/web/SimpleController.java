package spring.boot.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/simple")
public class SimpleController {
	
	@RequestMapping(value = "/index")
	public String index(){
		System.out.println("rerwerew");
		return "index";
	}

}

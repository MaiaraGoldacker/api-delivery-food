package com.api.algafood;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class meuPrimeiroController {

	@GetMapping("/hello")
	@ResponseBody
	public String helloWorld() {
		return "Hello";
	}
}

package com.fastcampus.ch2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Hello {
	
	@RequestMapping("/hello")
	private void main()
	{
		System.out.println("Hello2");
	}
}

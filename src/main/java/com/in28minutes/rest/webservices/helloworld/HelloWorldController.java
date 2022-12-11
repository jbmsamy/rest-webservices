package com.in28minutes.rest.webservices.helloworld;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	private MessageSource messageSource;
	
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	//@RequestMapping(path="hello-world",method=RequestMethod.GET)
	@GetMapping(path="/hello-world2")
	public String helloWorld()	{
		return "Hello World!";
	}
	
	@GetMapping(path="/hello-world-internationalized")
	public String helloWorldInternationalized()	{
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
		//return "Hello World!";
	}
	
	@GetMapping(path="hello-world-bean")
	public HelloWorldBean helloWorlBean() {
		return new HelloWorldBean("Hello World!");
	}
	
	@GetMapping(path="hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathvariabele(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World!, %s",name));
	}
}

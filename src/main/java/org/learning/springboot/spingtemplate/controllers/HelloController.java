package org.learning.springboot.spingtemplate.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello";
	}

	@GetMapping("/activity")
	public Object getActivity() {
		String url = "https://www.boredapi.com/api/activity";
		RestTemplate restTemplate = new RestTemplate();
		Object result = restTemplate.getForObject(url, Object.class);
		return result;
	}
	
	@GetMapping("/projects")
	public Object getProjects() {
		String url = "https://api.publicapis.org/entries";
		RestTemplate restTemplate = new RestTemplate();
		Object result = restTemplate.getForObject(url, Object.class);
		return result;
	}
	
	@GetMapping("/passengers")
	public Object getPassengerInfo(@RequestParam int pageNumber, @RequestParam int pageSize) {
		String url = "https://api.instantwebtools.net/v1/passenger?page="+pageNumber+"&size="+pageSize;
		RestTemplate restTemplate = new RestTemplate();
		Object result = restTemplate.getForObject(url, Object.class);
		return result;
	}
	
	

}

package com.basics.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.basics.exception.UserNotFoundException;
import com.basics.model.User;
import com.basics.service.UserService;

@RestController
@RequestMapping(value="/ws")
public class WsController {
	public Logger log = LogManager.getLogger(com.basics.controller.WsController.class);
	
	@Autowired
	public UserService userService;
	
	@RequestMapping(value="/users",method = RequestMethod.GET)
	public List<User> getAllUsers()  {
		log.debug("getAllUsers");
		List<User> list = userService.getAllUsers();
		return list;
	}
	
	@RequestMapping(value="/search",method = RequestMethod.GET)
	public  List<User> users2() {
		log.debug("search");
		List<User> list = userService.search("firstname","test");
		return list;
	}
	
	@RequestMapping(value="/one",method=RequestMethod.GET,headers="Accept=application/json")
	public User getId(@PathVariable int id) {
		return new User("test","test");
	}	

	@RequestMapping(value="/user",method = RequestMethod.GET)
	public @ResponseBody User user() {
		return new User("test","test");
	}	
	
	@RequestMapping(value="/userBinding",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseEntity<User> test(@Valid @RequestBody User user, BindingResult bindingResult) {
		log.debug("Errors:"+bindingResult.getErrorCount());
		if (bindingResult.hasErrors()) {
			//...
		} else {
			//...
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value="/exception",method=RequestMethod.GET)
	@ResponseBody
	public void exception2() throws UserNotFoundException {
		throw new UserNotFoundException();
	}
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public String test()  {
		return "greeting";
	}
}

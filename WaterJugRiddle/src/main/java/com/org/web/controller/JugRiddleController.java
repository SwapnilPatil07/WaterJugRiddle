package com.org.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.org.exception.CustomException;
import com.org.helper.Constant;
import com.org.helper.JugRiddleHelper;
import com.org.model.JugRiddleRequest;
import com.org.model.State;
import com.org.web.services.JugRiddleService;

/**
 * 
 * Jug Riddle application controller. 
 * @author Swapnil Patil
 *
 */
@Controller
public class JugRiddleController  implements ErrorController{
	static Logger logger = Logger.getLogger(JugRiddleController.class);
	
	@Autowired
	JugRiddleService jugRiddleService;
	
	/**
	 * 
	 * @param jugRiddleRequest - Values of jugX, jugY and jugZ.
	 * @param model - Model to set state for Thymeleaf template.
	 * @return Thymeleaf fragment populated with state list.
	 */
	@PostMapping("/getSteps")
	public String getSteps(@RequestBody JugRiddleRequest jugRiddleRequest, Model model) {
		logger.info("Inside getSteps");
		List<State> stateList = new ArrayList<State>();
		logger.info("Request parameters - " + jugRiddleRequest );
		try {
			
			int jugX = jugRiddleRequest.getJugX();
			int jugY = jugRiddleRequest.getJugY();
			int jugZ = jugRiddleRequest.getJugZ();
						
			int steps = jugRiddleService.findMinSteps(jugX, jugY, jugZ, stateList);
			
			if(stateList.isEmpty()) {
				logger.info("Solution NOT found");
				model.addAttribute("msg", Constant.noSolution);
				model.addAttribute("list", stateList);
			}else {
				logger.info("Solution found. Return thymeleaf fragment.");
				if(steps > 100) {
					model.addAttribute("size", steps);
					model.addAttribute("stateCounter", steps - 100);
				}else {
					model.addAttribute("size",  stateList.size());
					model.addAttribute("stateCounter", 0);
				}
				model.addAttribute("msg", Constant.success);
				model.addAttribute("jugX", jugX);
				model.addAttribute("jugY", jugY);
				model.addAttribute("jugXhgt", JugRiddleHelper.calculateJugHeight(jugX, jugY));
				model.addAttribute("jugYhgt", JugRiddleHelper.calculateJugHeight(jugY, jugX));
				model.addAttribute("stateList", stateList);
			}			
			
		}catch(CustomException e) {
			logger.error("CustomException while processing request - " + e.getMessage(), e);
			model.addAttribute("msg", e.getMessage());
		}catch(Exception e) {
			logger.error("Exception while processing request - " + e.getMessage(), e);
			model.addAttribute("msg", Constant.exceptionMessage);
		}
		
		 return "steps/steps :: list";
		
	}
	
	/**
	 * 
	 * Home page of application.
	 * @return Home page 
	 */
	@GetMapping("/")
	public String getRiddle() {
		logger.info("Inside getRiddle");
		return "riddle";
	}
		
	/**
	 * Show error page if exception is thrown from application.
	 * @return Error Page
	 */
	@GetMapping("/error")
    public String error() {
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
	

}

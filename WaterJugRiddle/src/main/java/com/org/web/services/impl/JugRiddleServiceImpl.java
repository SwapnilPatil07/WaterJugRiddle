package com.org.web.services.impl;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.org.exception.CustomException;
import com.org.helper.Constant;
import com.org.helper.JugRiddleHelper;
import com.org.model.State;
import com.org.web.services.JugRiddleService;

/**
 * Service to calculate steps.
 * 
 * @author Swapnil Patil
 *
 */
@Service
public class JugRiddleServiceImpl implements JugRiddleService {
	static Logger logger = Logger.getLogger(JugRiddleServiceImpl.class);
		
	
	/**
	 * Find minimum steps required to accumulate jugZ-gallon using jugX and jugY.
	 * 
	 * @param jugX - Jug X
	 * @param jugY - Jug Y
	 * @param jugZ - Jug Z
	 * @param stateList - List of state for each step
	 * @return Returns minimum steps required to accumulate jugZ-gallon. 
	 * 		   Throw custom exception if invalid input or solution cannot be achieved using provided parameters. 
	 */
	public int findMinSteps(int jugX, int jugY, int jugZ, List<State> stateList) throws CustomException{ 
		logger.info("Inside service");
		
		// If jug capacity is less than or equal to zero then request cannot be processed.
		if(jugX <= 0 || jugY <= 0 || jugZ <= 0)
			throw new CustomException(Constant.inputMustNotBeLessThanZero);
		
			
		//If amount to measure is more than largest jug then request cannot be processed.
		if (jugZ > jugY && jugZ > jugX) {
			logger.info("Jug Z [" + jugZ +"]  is larger than Jug X ["+ jugX +"] and Jug Y ["+ jugY +"]. Not processing request.");
			// This can be hadled at client side but keeping it to demostrate server side validation 
			throw new CustomException(Constant.jugZMustNotBeGreatest);  			
		}
			

		int gcdXY = JugRiddleHelper.findGCD(jugY, jugX);		
		// If jugZ is not divisible by GCD of jugX and jugY request cannot be processed.
		if (jugZ % gcdXY != 0) {
			logger.info("Jug Z [" + jugZ +"]  is not divisible by GCD [" + gcdXY +"] of Jug X ["+ jugX +"] and Jug Y ["+ jugY +"]. Not processing request.");
			throw new CustomException(Constant.jugZMustBeDevidedByGCD);
		}
					
		logger.info("Finding steps.");
	
		// 1. Water of jugX poured into jugY. When jugX empty fill it. When jugY full empty it. Repeat until JugX or jugY equals jugZ.  
		// 2. Water of jugY poured into jugX. When jugY empty fill it. When jugX full empty it. Repeat until JugX or jugY equals jugZ.
		//Calculate minimum steps required without saving data in list
		int stepsFromX = JugRiddleHelper.transferWater(null, jugX,jugY,jugZ, true, false,  0);
		int stepsFromY = JugRiddleHelper.transferWater(null, jugY,jugX,jugZ, false, false, 0); 
		
		List<State> states = new ArrayList<State>();
		
		logger.info("Step listFromX - count [" + stepsFromX + "].");		
		logger.info("Step listFromY - count [" + stepsFromY + "].");
					
				
		// Save last 100 steps in list	
		if(stepsFromX < stepsFromY)
			JugRiddleHelper.transferWater(states, jugX,jugY,jugZ, true, true,  stepsFromX);
		else
			JugRiddleHelper.transferWater(states, jugY,jugX,jugZ, false, true, stepsFromY); 
		
		logger.debug("Step listFromY- values [" + states + "].");	
		stateList.addAll(states);
		
		return stepsFromX < stepsFromY ? stepsFromX : stepsFromY;
	} 
	
}

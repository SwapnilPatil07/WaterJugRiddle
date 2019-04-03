package com.org.helper;

import java.util.List;

import org.apache.log4j.Logger;

import com.org.exception.CustomException;
import com.org.model.State;

/**
 * Helper class for application.
 * 
 * @author Swapnil Patil
 *
 */
public class JugRiddleHelper {
	static Logger logger = Logger.getLogger(JugRiddleHelper.class);
		
	/**
	 * Create state with current value of JugX and JugY with message.
	 * 
	 * @param tempFromJug - Water transferred from jug.
	 * @param tempToJug - Water transferred to jug.
	 * @param isJugX - True if jugX is the tempFromJug parameter.
	 * @param operation - Operation performed on jugX and/or jugY.
	 * @param amount - Amount of water involved in operation.
	 * @return State of JugX and JugY with message explaining operation performed.
	 * @throws CustomException 
	 */
	public static State createState(int tempFromJug, int tempToJug, boolean isJugX, String operation, int amount) throws CustomException{		
		State state = new State();
		String jug;	
		String message;
		try {
			if(isJugX) {	
				state.setJugX(tempFromJug);
				state.setJugY(tempToJug);
			}else {
				state.setJugX(tempToJug); 
				state.setJugY(tempFromJug);
			}
			
			// Get jug name.
			jug = isJugX == true ? Constant.jugXName : Constant.jugYName;
			
			// Populate message with details of operation performed.
			if(Constant.opFill.equals(operation)) {
				message = operation + " " + amount +  " gal in " + jug;
			}else if(Constant.opTransfer.equals(operation)){
				message = operation + " " + amount + " gal from" + jug;
			}else {
				message = operation + " " + amount +  " gal from " + (Constant.jugXName.equals(jug) ? Constant.jugYName : Constant.jugXName);
			}
		
			state.setMessage(message);
		}catch(Exception e) {
			logger.error("Exception while setting states - " + e.getMessage(), e);
			throw e;
		}
				
		return state;
	}
	
	/**
	 * Find greatest common divisor using recursion. 
	 * @param jugX - Jug X
	 * @param jugY - Jug Y
	 * @return GCD of Jug X and Jug Y
	 */
	public static int findGCD(int jugX, int jugY){
		 if (jugY == 0) 
		       return jugX; 
		 return findGCD(jugY, jugX%jugY); 
	}
	
	/**
	 * 
	 * @param stateList - List of state for each step
	 * @param fromJug - Transferred from jug.
	 * @param toJug - Transferred to jug.
	 * @param jugZ - Jug Z : Expected outcome.
	 * @param isJugX - Is first parameter (fromJug) is Jug X.
	 * @param saveToList - Does state of steps needs to be saved.
	 * @param steps - steps for achieving solution  
	 * @return Steps to transfer from fromJug to toJug.
	 */
	public static int transferWater(List<State> stateList, int fromJug, int toJug, int jugZ, boolean isJugX, boolean saveToList, int steps) 
		throws CustomException{
				
		//initialize temporary variables
		int tempFromJug = fromJug;
		int tempToJug = 0;
		int tempSteps = 0;
		
		//initialize step as temp variable initialized
		tempSteps++;
		if(steps <= 100 && saveToList) 
			stateList.add(createState(tempFromJug, tempToJug, isJugX, Constant.opFill, fromJug));		
		
		//Loop until expected outcome is achieved.
		while(tempFromJug != jugZ && tempToJug != jugZ) {			
			
			int water = Math.min(tempFromJug, toJug - tempToJug); 
			
			//Transfer "water" from "tempFromJug" to "tempToJug"
			tempToJug += water; 
			tempFromJug -= water;
			tempSteps++;
			
			// increase step as water added to jug			
			if((steps > 100 && steps - tempSteps < 100) || steps<= 100 && saveToList) 				
				stateList.add(createState(tempFromJug, tempToJug, isJugX, Constant.opTransfer, water));
		
					
			if (tempFromJug == jugZ || tempToJug == jugZ) 
				break; 
			
			// If tempFromJug empty fill it.
			if (tempFromJug == 0){ 
				tempFromJug = fromJug; 
				tempSteps++;
				if((steps > 100 && steps - tempSteps < 100) || steps<= 100 && saveToList) 	
					stateList.add(createState(tempFromJug, tempToJug, isJugX, Constant.opFill, fromJug));				
			} 
	
			// If tempToJug full empty it
			if (tempToJug == toJug) { 
				tempToJug = 0; 
				tempSteps++; 
				if((steps > 100 && steps - tempSteps < 100) || steps<= 100 && saveToList) 	
					stateList.add(createState(tempFromJug, tempToJug, isJugX, Constant.opEmpty, toJug));				 
			} 
			
		}		
		return tempSteps; 
	}
	
	/**
	 * Calculate Jug height for jug.
	 * Only Smaller jug height is calculated.
	 * Smaller jug height calculated in proportion with big Jug. 
	 * If calculated height is too small (less than 50) then 
	 * return default (50) so that it will not 
	 * appear too small on screen.  
	 * 
	 * @param forJug - Jug whose height will be calculated.
	 * @param withJug - Height is calculated in proportion with this parameter.
	 * @return Height to show on screen.
	 */
	public static int calculateJugHeight(float forJug, float withJug) {	
			float tempHeight =  (forJug/withJug) * 100;
			return Math.round(tempHeight < 50 ? 50 : tempHeight > 100 ? 100 : tempHeight);
	}
		
}

package com.org.web.services;

import java.util.List;

import com.org.exception.CustomException;
import com.org.model.State;

/**
 * 
 * Jug Riddle Service
 * @author Swapnil Patil
 *
 */
public interface JugRiddleService {
	public int findMinSteps(int jugX, int jugY, int jugZ, List<State> stateList) throws CustomException;
}

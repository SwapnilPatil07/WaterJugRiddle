package com.org.web.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.org.exception.CustomException;
import com.org.helper.Constant;
import com.org.model.State;
import com.org.web.services.JugRiddleService;
import com.org.web.services.impl.JugRiddleServiceImpl;

public class JugRiddleServiceTest {
	
	JugRiddleService riddleService;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void before() {
		riddleService = new JugRiddleServiceImpl();
	}

	@Test 
	public void whenParametersZeroOrLess_Exception() throws CustomException{
		expectedEx.expect(CustomException.class);
		expectedEx.expectMessage(Constant.inputMustNotBeLessThanZero);
		riddleService.findMinSteps(0, -1, -1, null);
	}
	
	@Test
	public void whenJugZisGreatestNumber_Exception() throws CustomException{
		expectedEx.expect(CustomException.class);
		expectedEx.expectMessage(Constant.jugZMustNotBeGreatest);
		riddleService.findMinSteps(10, 10, 25, null);
	}
	
	@Test
	public void whenJugZisNotDivisibleByGcdOfJugXAndJugY_Exception() throws CustomException{
		 expectedEx.expect(CustomException.class);
		 expectedEx.expectMessage(Constant.jugZMustBeDevidedByGCD);		 
	     riddleService.findMinSteps(26, 13, 25, null);
	}
	
	@Test
	public void whenJugZisDivisibleByGcdOfJugXAndJugY_ReturnResult() throws CustomException{	
		 List<State> stateList = new ArrayList<State>(); 
		 riddleService.findMinSteps(39, 13, 26, stateList);
		 assertEquals("If jugZ is divisible by GCD of jugX and jugY must return data",
				 	2, stateList.size());		 
	}
	
	@Test
	public void whenAbleToFindSteps_ReturnMinSteps() throws CustomException{
		 List<State> stateList = new ArrayList<State>();
	   	 State state1 = new State(39, 0, "Fill 39 gal in  Jug X");
    	 State state2 = new State(26, 13, "Transfer 13 gal from Jug X");
    	 List<State> expectedList = new ArrayList<State>();
    	 expectedList.add(state1);
    	 expectedList.add(state2);
				 
		 riddleService.findMinSteps(39, 13, 26, stateList);
		 assertEquals("Must return minimum steps to fill jugZ",	2, stateList.size());
		 assertEquals("Result state should match",	expectedList.get(0), stateList.get(0));
		 assertEquals("Result state should match",	expectedList.get(1), stateList.get(1));
		 
	}
	
	@Test(expected = NullPointerException.class)
	public void whenXYZValuesAreValidAndListIsNull_Exception() throws CustomException{		 
		 riddleService.findMinSteps(39, 13, 26, null);
	}


}

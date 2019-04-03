package com.org.helper;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.org.exception.CustomException;
import com.org.model.State;


public class JugRiddleHelperTest {
	

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void whenOpration_ReturnAppropriateMessage() throws CustomException{
		assertThat("Operation 'empty' must return string containing word Empty", JugRiddleHelper.createState(1, 5, true, Constant.opEmpty, 50).getMessage(), containsString("Empty"));
		assertThat("Operation 'fill' must return string containing word Empty", JugRiddleHelper.createState(1, 5, true, Constant.opFill, 50).getMessage(), containsString("Fill"));
		assertThat("Operation 'transfer' must return string containing word Empty", JugRiddleHelper.createState(1, 5, true, Constant.opTransfer, 50).getMessage(), containsString("Transfer"));
	}
	
	public void whenAmount_ReturnAppropriateMessage() throws CustomException{
		assertThat("Operation 'empty' with amount 50 must return string containing 50", JugRiddleHelper.createState(1, 5, true, Constant.opEmpty, 50).getMessage(), containsString("50"));
		assertThat("Operation 'fill' with amount 60  must return string containing 60", JugRiddleHelper.createState(1, 5, true, Constant.opFill, 60).getMessage(), containsString("60"));
		assertThat("Operation 'transfer' with amount 70  must return string containing 70", JugRiddleHelper.createState(1, 5, true, Constant.opTransfer, 70).getMessage(), containsString("70"));
	}
	
	
	@Test
	public void whenReverseFlag_ReverseNumbers() throws CustomException{		
		assertEquals("State with revese of [X=1,Y=5] must be [X=5, Y=1]" ,5, JugRiddleHelper.createState(1, 5, false, Constant.opEmpty, 50).getJugX());
	}

	@Test
	public void whenNotReverse_DoNotReverseNumbers() throws CustomException{		
		assertEquals("State without revese [X=1,Y=5] must be [X=1, Y=5]" ,1, JugRiddleHelper.createState(1, 5, true, Constant.opEmpty, 50).getJugX());
	}
	
	
	@Test
	public void whenGcd_ReturnGcdOfNumbers() {		
		assertEquals("GCD of [X=25,Y=50] must be 25" ,25 , JugRiddleHelper.findGCD(25, 50));
	}
	
	@Test
	public void whenOneNumberZero_OtherNumberIsGcd() {		
		assertEquals("GCD of [X=3,Y=0] must be 3" ,3 , JugRiddleHelper.findGCD(3, 0));
	}
	
	@Test
	public void whenTransferWaterWithSavedState_StepsShouldMatch() throws CustomException{
		 List<State> stateList = new ArrayList<State>();
	 	 State state0 = new State(6, 0, "Fill 6 gal in  Jug X");
    	 State state1 = new State(5, 1, "Transfer 1 gal from Jug X");
    	 State state5 = new State(3, 1, "Transfer 1 gal from Jug X");
    	 List<State> expectedList = new ArrayList<State>();
    	 expectedList.add(state0);
    	 expectedList.add(state1);
    	 expectedList.add(state5);
		 int steps = JugRiddleHelper.transferWater(stateList, 6, 1, 3, true, true, 0);
		 assertThat("Number of steps must be 6", steps, is(6));
	 	 assertThat("List must not be empty",stateList, not(IsEmptyCollection.empty()));
	 	 assertEquals("Result state should match",	expectedList.get(0), stateList.get(0));
		 assertEquals("Result state should match",	expectedList.get(1), stateList.get(1));
		 assertEquals("Result state should match",	expectedList.get(2), stateList.get(5));	 	 
	}
	
	@Test
	public void whenTransferWaterWithoutState_StepsShouldMatch() throws CustomException{
		int steps = JugRiddleHelper.transferWater(null, 200, 10, 120, true, false, 0);
		assertThat("Number of steps must be 16", steps, is(16));		
	}
	
	
	@Test
	public void whenHeightGreater100_Return100() {		
		assertEquals("When equal or greater than 100 must return 100" ,100 , JugRiddleHelper.calculateJugHeight(150, 50));
	}
	
	@Test
	public void whenRatioBetween50And100_ReturnRatio() {		
		assertEquals("When height between 50 to 100 must return height" ,75 , JugRiddleHelper.calculateJugHeight(60, 80));
	}
	
	@Test
	public void whenRatioBelow50_Return50() {		
		assertEquals("When height below 50 must return 50" ,50 , JugRiddleHelper.calculateJugHeight(60, 600));
	}
	
	@Test(expected = CustomException.class) 
	public void whenCreateStateWithNegative_Exception() throws CustomException {
		JugRiddleHelper.createState(-1, 0, true, Constant.opEmpty, 50);
	}

}

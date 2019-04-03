package com.org.web.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.model.JugRiddleRequest;
import com.org.web.services.impl.JugRiddleServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(JugRiddleController.class)
public class JugRiddleControllerTest {
 
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private JugRiddleServiceImpl jugRiddleService;
    

    @Test
    public void givenStates_whenGetStates_thenReturnNoSolution() throws Exception {
    		       		 
    	JugRiddleRequest jugRiddleRequest = new JugRiddleRequest();
    	jugRiddleRequest.setJugX(1);
    	jugRiddleRequest.setJugY(50);
    	jugRiddleRequest.setJugZ(15);
    	
    	when(jugRiddleService.findMinSteps(10, 10, 10, null)).thenReturn(0);
    	
    	MvcResult result = mvc.perform(
    		     MockMvcRequestBuilders.post("/getSteps") 
    		      .content(asJsonString(jugRiddleRequest))
    		      .contentType(MediaType.APPLICATION_JSON)) 	 
    		      .andExpect(status().isOk())
    		      .andReturn(); 
    			  
	    String content = result.getResponse().getContentAsString();
	    assertThat("Response must contain No Solution found message", content, containsString("No solution found for given input. Please try with different input."));
    		    
    }
   
    
    @Test
    public void givenStates_whenDefaultURL_thenReturnHomePage() throws Exception {    	       		 
    	
       mvc.perform(get("/") 
    		      .contentType(MediaType.APPLICATION_JSON)) 	 
    		      .andExpect(status().isOk())
    		      .andExpect(MockMvcResultMatchers.view().name("riddle"));
    	
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}


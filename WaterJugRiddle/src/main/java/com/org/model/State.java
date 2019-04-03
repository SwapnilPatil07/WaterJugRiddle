package com.org.model;

import com.org.exception.CustomException;
import com.org.helper.Constant;

/**
 * Class to save state and operation message.
 * 
 * @author Swapnil Patil
 *
 */
public class State {
	int jugX;
	int jugY;
	String message;
	
	public State() {
		
	}
		
	public State(int jugX, int jugY, String message) {
		super();
		this.jugX = jugX;
		this.jugY = jugY;
		this.message = message;
	}
	public int getJugX() {
		return jugX;
	}
	public void setJugX(int jugX) throws CustomException{
		if(jugX >= 0)
			this.jugX = jugX;
		else
			throw new CustomException(Constant.JugXStateLessZeroExce);
		
		
	}
	public int getJugY() {
		return jugY;
	}
	public void setJugY(int jugY) throws CustomException{
		if(jugY >= 0)
			this.jugY = jugY;
		else
			throw new CustomException(Constant.JugYStateLessZeroExce);
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "State [jugX=" + jugX + ", jugY=" + jugY + ", message=" + message + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + jugX;
		result = prime * result + jugY;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (jugX != other.jugX)
			return false;
		if (jugY != other.jugY)
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}
	
	

}

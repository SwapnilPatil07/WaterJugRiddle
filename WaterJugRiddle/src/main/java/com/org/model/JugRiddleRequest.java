package com.org.model;

import com.org.exception.CustomException;
import com.org.helper.Constant;

/**
 * 
 * Class to store request parameters.
 * @author Swapnil Patil
 *
 */
public class JugRiddleRequest {

    int jugX;
    int jugY;
    int jugZ;
    
	public int getJugX() {
		return jugX;
	}
	public void setJugX(int jugX) throws CustomException{
		if(jugX > 0)
			this.jugX = jugX;
		else
			throw new CustomException(Constant.JugXInputLessZeroExce);
	}
	public int getJugY() {
		return jugY;
	}
	public void setJugY(int jugY) throws CustomException {
		if(jugY > 0)
			this.jugY = jugY;
		else 
			throw new CustomException(Constant.JugYInputLessZeroExce);
	}
	public int getJugZ() {
		return jugZ;
	}
	public void setJugZ(int jugZ) throws CustomException{
		if(jugZ > 0)
			this.jugZ = jugZ;
		else 
			throw new CustomException(Constant.JugZInputLessZeroExce);
	}
	@Override
	public String toString() {
		return "JugRiddleRequest [jugX=" + jugX + ", jugY=" + jugY + ", jugZ=" + jugZ + "]";
	}
}
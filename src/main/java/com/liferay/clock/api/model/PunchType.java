package com.liferay.clock.api.model;

public enum PunchType {

	IN(0), OUT(1);
	
	int type;
	
	PunchType(int type){
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}

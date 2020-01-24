package com.xcase.klearnow.impl.simple.transputs;

import com.xcase.klearnow.transputs.GetActorRequest;

public class GetActorRequestImpl extends KlearNowRequestImpl implements GetActorRequest {
    private String actorId;
    
	@Override
	public String getActorId() {
		return this.actorId;
	}

	@Override
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

}

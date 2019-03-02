package com.him.geektrust.meetfamily.model;

import com.him.geektrust.meetfamily.operations.AddChildOperationProcessor;
import com.him.geektrust.meetfamily.operations.GetRelationshipOperationProcessor;
import com.him.geektrust.meetfamily.operations.OperationProcessor;

public enum Operation {
	ADD_CHILD, GET_RELATIONSHIP;
	
	
	public OperationProcessor getProcessor() {
		if(equals(ADD_CHILD)) {
			return AddChildOperationProcessor.getInstance();
			
		}else if(equals(GET_RELATIONSHIP)) {
			return GetRelationshipOperationProcessor.getInstance();
		}
		return null;
	}
	
	
	

}

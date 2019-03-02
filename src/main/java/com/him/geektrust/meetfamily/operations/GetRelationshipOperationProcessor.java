package com.him.geektrust.meetfamily.operations;

import java.util.List;

import com.him.geektrust.meetfamily.model.FamilyTree;

public class GetRelationshipOperationProcessor implements OperationProcessor {

	private static GetRelationshipOperationProcessor processor=new GetRelationshipOperationProcessor();
	
	private GetRelationshipOperationProcessor() {
		
	}
	
	public static OperationProcessor getInstance() {
		return processor;
	}
	
	@Override
	public void processOperation(List<String> args, FamilyTree family) {
		// TODO Auto-generated method stub

	}

}

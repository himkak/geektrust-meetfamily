package com.him.geektrust.meetfamily.operations;

import java.util.List;

import com.him.geektrust.meetfamily.model.FamilyTree;
import com.him.geektrust.meetfamily.model.Relationship;

public class GetRelationshipOperationProcessor implements OperationProcessor {

	private static GetRelationshipOperationProcessor processor = new GetRelationshipOperationProcessor();

	private GetRelationshipOperationProcessor() {

	}

	public static OperationProcessor getInstance() {
		return processor;
	}

	@Override
	public void processOperation(List<String> args, FamilyTree family) {
		String personName = args.get(0);
		String relationShip = args.get(1);
		String result = Relationship.getEnum(relationShip).handleRelationship(personName, family);
		System.out.println(result);
	}

}

package com.him.geektrust.meetfamily.operations;

import java.util.List;

import com.him.geektrust.meetfamily.model.FamilyTree;

public interface OperationProcessor {
	
	void processOperation(List<String> args, FamilyTree family);

}

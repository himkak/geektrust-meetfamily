package com.him.geektrust.meetfamily;

import java.util.Optional;

import com.him.geektrust.meetfamily.model.FamilyTree;
import com.him.geektrust.meetfamily.model.Person;
import com.him.geektrust.meetfamily.model.Person.Gender;
import com.him.geektrust.meetfamily.operations.AddChildOperationProcessor;
/**
 * This class initializes the family tree, sets all the relations.
 * @author himanshu
 *
 */
public class FamilyTreeInitializer {

	public static FamilyTree initializeFamilyTree() {
		Person queen = Person.builder().name("Queen Anga").gender(Gender.Female).build();
		Person king = Person.builder().name("King Shah").gender(Gender.Male).parallelRelative(Optional.of(queen))
				.build();
		FamilyTree familyTree = FamilyTree.builder().king(king).queen(queen).build();

		Person p1 = addChild(queen.getName(), familyTree, "Chit", Gender.Male);
		p1.setParallelRelative("Amba", Gender.Female);
		addChild(queen.getName(), familyTree, "Ish", Gender.Male);
		addChild(queen.getName(), familyTree, "Vich", Gender.Male).setParallelRelative("Lika", Gender.Female);
		addChild(queen.getName(), familyTree, "Aras", Gender.Male).setParallelRelative("Chitra", Gender.Female);
		addChild(queen.getName(), familyTree, "Satya", Gender.Female).setParallelRelative("Vyan", Gender.Male);
		addChild("Amba", familyTree, "Dritha", Gender.Female).setParallelRelative("Jaya", Gender.Male);
		addChild("Dritha", familyTree, "Yodhan", Gender.Male);
		addChild("Amba", familyTree, "Tritha", Gender.Female);
		addChild("Amba", familyTree, "Vritha", Gender.Male);
		addChild("Lika", familyTree, "Vila", Gender.Female);
		addChild("Lika", familyTree, "Chika", Gender.Female);
		addChild("Chitra", familyTree, "Jnki", Gender.Female).setParallelRelative("Arit", Gender.Male);
		addChild("Chitra", familyTree, "Ahit", Gender.Male);
		addChild("Jnki", familyTree, "Laki", Gender.Male);
		addChild("Jnki", familyTree, "Lavnya", Gender.Female);
		addChild("Satya", familyTree, "Asva", Gender.Male).setParallelRelative("Satvy", Gender.Female);
		addChild("Satya", familyTree, "Vyas", Gender.Male).setParallelRelative("Krpi", Gender.Female);
		addChild("Satya", familyTree, "Atya", Gender.Female);
		addChild("Satvy", familyTree, "Vasa", Gender.Male);
		addChild("Satvy", familyTree, "Vasa", Gender.Male);
		addChild("Krpi", familyTree, "Kriya", Gender.Male);
		addChild("Krpi", familyTree, "Krithi", Gender.Female);

		return familyTree;
	}

	private static Person addChild(String motherName, FamilyTree familyTree, String childName, Gender gender) {
		AddChildOperationProcessor childOp = (AddChildOperationProcessor) AddChildOperationProcessor.getInstance();
		Optional<Person> child = childOp.addChildNode(familyTree, motherName, childName, gender);
		return child.isPresent() ? child.get() : null;
	}

}

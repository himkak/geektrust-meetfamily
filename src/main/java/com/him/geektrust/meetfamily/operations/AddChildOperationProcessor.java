package com.him.geektrust.meetfamily.operations;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.him.geektrust.meetfamily.model.FamilyTree;
import com.him.geektrust.meetfamily.model.Person;
import com.him.geektrust.meetfamily.model.Person.Gender;

/*
 * This singleton class searches the mother node, creates a child node and adds the child node to mother node.
 */
public class AddChildOperationProcessor implements OperationProcessor {

	private static OperationProcessor processor = new AddChildOperationProcessor();

	private AddChildOperationProcessor() {
	}

	public static OperationProcessor getInstance() {
		return processor;
	}

	@Override
	public void processOperation(List<String> args, FamilyTree family) {
		if (!args.isEmpty()) {
			String motherName = args.get(0);
			String childName = args.get(1);
			Gender gender = Gender.valueOf(args.get(2));
			if (addChildNode(family, motherName, childName, gender).isPresent()) {
				System.out.println("CHILD_ADDITION_SUCCEEDED");
			}
		}
	}

	public Optional<Person> addChildNode(FamilyTree family, String motherName, String childName, Gender gender) {

		Optional<Person> motherNode = getExistingMotherNode(motherName, family);
		Person childNode = createChildNode(childName, gender, motherNode);
		if (motherNode.isPresent()) {
			addChildNode(motherNode.get(), childNode);
			return Optional.of(childNode);
		} else {
			return Optional.empty();
		}
	}

	private void addChildNode(Person motherNode, Person childNode) {
		List<Person> children = motherNode.getDirectChildRelations();
		if (children != null) {
			children.add(childNode);
		} else {
			children = new LinkedList<>();
			children.add(childNode);
			motherNode.setDirectChildRelations(children);
		}
	}

	private Person createChildNode(String childName, Gender gender, Optional<Person> parentNode) {
		return Person.builder().name(childName).gender(gender).parentNode(parentNode).build();
	}

	private Optional<Person> getExistingMotherNode(String motherName, FamilyTree family) {
		Person currentNode = family.getQueen();
		if (currentNode.getName().equalsIgnoreCase(motherName)) {
			return Optional.of(currentNode);
		}
		Optional<Person> motherNode = currentNode.searchNode(motherName);
		if (motherNode.isPresent()) {
			if (motherNode.get().getGender() == Gender.Female) {
				return motherNode;
			} else {
				System.out.println("CHILD_ADDITION_FAILED");
				return Optional.empty();
			}
		} else {
			System.out.println("PERSON_NOT_FOUND ");
			return Optional.empty();
		}
	}

}

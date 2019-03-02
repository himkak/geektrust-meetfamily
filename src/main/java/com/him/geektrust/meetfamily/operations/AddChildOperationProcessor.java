package com.him.geektrust.meetfamily.operations;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.him.geektrust.meetfamily.model.FamilyTree;
import com.him.geektrust.meetfamily.model.Person;
import com.him.geektrust.meetfamily.model.Person.Gender;

public class AddChildOperationProcessor implements OperationProcessor {

	private static OperationProcessor processor = new AddChildOperationProcessor();

	private AddChildOperationProcessor() {
		// throw new Exception();
	}

	public static OperationProcessor getInstance() {
		return processor;
	}

	@Override
	public void processOperation(List<String> args, FamilyTree family) {
		String motherName = args.get(0);
		String childName = args.get(1);
		Gender gender = Gender.valueOf(args.get(2));
		//System.out.println("motherName:" + motherName + ", childName:" + childName + ", gender:" + gender);

		Person childNode = createChildNode(childName, gender);

		if(addChildNode(family, motherName, childNode)) {
			System.out.println("CHILD_ADDITION_SUCCEEDED");
		}else {
			System.err.println("Mother with name:" + motherName + " not found");
		}
	}

	public boolean addChildNode(FamilyTree family, String motherName, Person childNode) {
		Optional<Person> motherNode = getExistingMotherNode(motherName, family);
		if (motherNode.isPresent()) {
			addChildNode(motherNode.get(), childNode);
			return true;
		} else {
			return false;
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

	private Person createChildNode(String childName, Gender gender) {
		return Person.builder().name(childName).gender(gender).build();
	}

	private Optional<Person> getExistingMotherNode(String motherName, FamilyTree family) {
		Person currentNode = family.getQueen();
		if (currentNode.getName().equalsIgnoreCase(motherName)) {
			return Optional.of(currentNode);
		}
		return searchNode(motherName, currentNode);
	}

	public Optional<Person> searchNode(String nodeName, Person currentNode) {

		/*
		 * if (currentNode.getGender() == Gender.Male &&
		 * currentNode.getParallelRelative() != null) { currentNode =
		 * currentNode.getParallelRelative(); }
		 */

		//System.out.println("Inside processNode, with args: motherName:" + motherName + ", currentNode:" + currentNode);
		Optional<Person> searchedNode=Optional.empty();
		if (currentNode.getGender()==Gender.Female && currentNode.getDirectChildRelations() != null && currentNode.getDirectChildRelations().size() != 0) {
			List<Person> childrenNodes = currentNode.getDirectChildRelations();
			for (Person currentChildNode : childrenNodes) { // horizontal iteration

				// vertical iteration
				while (currentChildNode != null) {

					//if (currentChildNode.getGender() == Gender.Female) {
						if (currentChildNode.getName().equalsIgnoreCase(nodeName)) {
							//System.out.println("Exiting processNode:returning:" + currentChildNode);
							return Optional.of(currentChildNode);
						}
				//	} 
				else {
						Person wife = currentChildNode.getParallelRelative();
						if (wife != null && wife.getName().equalsIgnoreCase(nodeName)) {
							//System.out.println("Exiting processNode:returning:" + wife);
							return Optional.of(wife);
						}
					}
					
					Optional<Person> person= searchNode(nodeName, currentChildNode);
					if(person.isPresent()) {
						//System.out.println("Exiting processNode:returning:" + person.get());
						return person;
					}else {
						break;
					}
					/*
					 * if (person.isPresent() || person.get().getDirectChildRelations() == null ||
					 * person.get().getDirectChildRelations().size() == 0) { return person; }
					 */
				}
			}
		} else {
			//System.out.println("No child.");
		}
		//System.out.println("Exiting processNode:returning empty");
		return searchedNode;
	}

}

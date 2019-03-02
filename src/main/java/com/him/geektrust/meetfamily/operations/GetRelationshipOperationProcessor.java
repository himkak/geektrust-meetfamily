package com.him.geektrust.meetfamily.operations;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.him.geektrust.meetfamily.model.FamilyTree;
import com.him.geektrust.meetfamily.model.Person;
import com.him.geektrust.meetfamily.model.Person.Gender;
import com.him.geektrust.meetfamily.model.Relationship;

public class GetRelationshipOperationProcessor implements OperationProcessor {

	private static GetRelationshipOperationProcessor processor = new GetRelationshipOperationProcessor();
	private Predicate<Person> isFemale = n -> n.getGender() == Gender.Female;
	private Predicate<Person> isMale = n -> n.getGender() == Gender.Male;
	Function<Person, String> personToName = Person::getName;

	private GetRelationshipOperationProcessor() {

	}

	public static OperationProcessor getInstance() {
		return processor;
	}

	@Override
	public void processOperation(List<String> args, FamilyTree family) {
		// System.out.println("Starting to search for relationship");
		String personName = args.get(0);
		String relationShip = args.get(1);
		BiFunction<String, FamilyTree, String> biFUnc = null;
		switch (Relationship.getEnum(relationShip)) {
		case Paternal_Uncle:
			biFUnc = (a, b) -> getPaternalUncle(a, b);
			break;
		case Maternal_Uncle:
			biFUnc = (a, b) -> getMaternalUncle(a, b);
			break;
		case Paternal_Aunt:
			biFUnc = (a, b) -> getPaternalAunt(a, b);
			break;
		case Maternal_Aunt:
			biFUnc = (a, b) -> getMaternalAunt(a, b);
			break;
		case Sister_In_Law:
			biFUnc = (a, b) -> getSisterInLaw(a, b);
			break;
		case Son:
			biFUnc = (a, b) -> getSon(a, b);
			break;
		case Daughter:
			biFUnc = (a, b) -> getDaughter(a, b);
			break;
		case Siblings:
			biFUnc = (a, b) -> getSiblings(a, b);
			break;
		case Brother_In_Law:
			biFUnc = (a, b) -> getBrotherInLaw(a, b);
			break;
		default:
			break;
		}

		System.out.println(biFUnc.apply(personName, family));

	}

	public String getPaternalUncle(String nodeName, FamilyTree family) {
		Person node = family.getKing().searchNode(nodeName).get();

		return node.getParentNode().get().getParentNode().get().getWife().getDirectChildRelations().stream().filter(
				n -> n.getGender() == Gender.Male && node.getParentNode().get().getHusband().getName() != n.getName())
				.map(personToName).collect(Collectors.joining(" "));
	}

	public String getMaternalUncle(String nodeName, FamilyTree family) {
		Person node = family.getKing().searchNode(nodeName).get();

		return node.getParentNode().get().getParentNode().get().getDirectChildRelations().stream().filter(isMale)
				.map(personToName).collect(Collectors.joining(" "));
	}

	public String getPaternalAunt(String nodeName, FamilyTree family) {
		Person node = family.getKing().searchNode(nodeName).get();

		return node.getParentNode().get().getParentNode().get().getParallelRelative().get().getDirectChildRelations()
				.stream().filter(isFemale).map(personToName).collect(Collectors.joining(" "));
	}

	public String getMaternalAunt(String nodeName, FamilyTree family) {
		Person node = family.getKing().searchNode(nodeName).get();

		return node.getParentNode().get().getParentNode().get().getDirectChildRelations().stream().filter(isFemale)
				.map(personToName).collect(Collectors.joining(" "));
	}

	public String getSisterInLaw(String nodeName, FamilyTree family) {
		Person node = family.getKing().searchNode(nodeName).get();

		if (node.getParallelRelative().isPresent())
			return node.getParallelRelative().get().getParentNode().get().getDirectChildRelations().stream()
					.filter(isFemale).map(personToName).collect(Collectors.joining(" "));
		return "NONE";

	}

	public String getBrotherInLaw(String nodeName, FamilyTree family) {
		Person node = family.getKing().searchNode(nodeName).get();

		if (node.getParallelRelative().isPresent())
			return node.getParallelRelative().get().getParentNode().get().getDirectChildRelations().stream()
					.filter(n -> n.getGender() == Gender.Male && node.getHusband().getName() != n.getName())
					.map(personToName).collect(Collectors.joining(" "));
		return "NONE";

	}

	public String getSon(String nodeName, FamilyTree family) {

		Person node = family.getKing().searchNode(nodeName).get();
		if (node.getDirectChildRelations() != null)
			return node.getDirectChildRelations().stream().filter(isMale).map(personToName)
					.collect(Collectors.joining(" "));
		return "NONE";

	}

	public String getDaughter(String nodeName, FamilyTree family) {

		Optional<Person> node = family.getKing().searchNode(nodeName);
		if (node.isPresent() && node.get().getDirectChildRelations() != null)
			return node.get().getDirectChildRelations().stream().filter(isFemale).map(Person::getName)
					.collect(Collectors.joining(" "));
		return "PERSON_NOT_FOUND";

	}

	public String getSiblings(String nodeName, FamilyTree family) {

		Person node = family.getKing().searchNode(nodeName).get();
		String res = node.getParentNode().get().getDirectChildRelations().stream()
				.filter(n -> n.getName() != node.getName()).map(Person::getName).collect(Collectors.joining(" "));

		return res != null && res != "" ? res : "NONE";

	}

}

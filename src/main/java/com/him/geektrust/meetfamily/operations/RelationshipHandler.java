package com.him.geektrust.meetfamily.operations;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.him.geektrust.meetfamily.model.FamilyTree;
import com.him.geektrust.meetfamily.model.Person;
import com.him.geektrust.meetfamily.model.Person.Gender;

/**
 * Class contains all the functions to handle the relationship.
 * 
 * @author himanshu
 *
 */
public class RelationshipHandler {

	static Function<Person, String> personToName = Person::getName;
	static Predicate<Person> isFemale = n -> n.getGender() == Gender.Female;
	static Predicate<Person> isMale = n -> n.getGender() == Gender.Male;

	public static final BiFunction<String, FamilyTree, String> siblingRelHandler = (String nodeName,
			FamilyTree family) -> {

		Person node = family.getKing().searchNode(nodeName).get();
		String res = node.getParentNode().get().getDirectChildRelations().stream()
				.filter(n -> n.getName() != node.getName()).map(Person::getName).collect(Collectors.joining(" "));

		return res != null && res != "" ? res : "NONE";
	};

	public static final BiFunction<String, FamilyTree, String> daughterRelHandler = (String nodeName,
			FamilyTree family) -> {

		Optional<Person> node = family.getKing().searchNode(nodeName);
		if (node.isPresent() && node.get().getDirectChildRelations() != null)
			return node.get().getDirectChildRelations().stream().filter(isFemale).map(Person::getName)
					.collect(Collectors.joining(" "));
		return "PERSON_NOT_FOUND";

	};

	public static final BiFunction<String, FamilyTree, String> sonRelHandler = (String nodeName, FamilyTree family) -> {
		Person node = family.getKing().searchNode(nodeName).get();
		if (node.getDirectChildRelations() != null)
			return node.getDirectChildRelations().stream().filter(isMale).map(personToName)
					.collect(Collectors.joining(" "));
		return "NONE";
	};

	public static final BiFunction<String, FamilyTree, String> brotherInLawRelHandler = (String nodeName,
			FamilyTree family) -> {
		Person node = family.getKing().searchNode(nodeName).get();

		if (node.getSpouse().isPresent())
			return node.getSpouse().get().getParentNode().get().getDirectChildRelations().stream()
					.filter(n -> n.getGender() == Gender.Male && node.getHusband().getName() != n.getName())
					.map(personToName).collect(Collectors.joining(" "));
		return "NONE";
	};

	public static final BiFunction<String, FamilyTree, String> sisterInLawRelHandler = (String nodeName,
			FamilyTree family) -> {
		Person node = family.getKing().searchNode(nodeName).get();

		if (node.getSpouse().isPresent())
			return node.getSpouse().get().getParentNode().get().getDirectChildRelations().stream().filter(isFemale)
					.map(personToName).collect(Collectors.joining(" "));
		return "NONE";
	};

	public static final BiFunction<String, FamilyTree, String> maternalAuntRelHandler = (String nodeName,
			FamilyTree family) -> {
		Person node = family.getKing().searchNode(nodeName).get();

		return node.getParentNode().get().getParentNode().get().getDirectChildRelations().stream().filter(isFemale)
				.map(personToName).collect(Collectors.joining(" "));
	};

	public static final BiFunction<String, FamilyTree, String> paternalAuntRelHandler = (String nodeName,
			FamilyTree family) -> {
		Person node = family.getKing().searchNode(nodeName).get();

		return node.getParentNode().get().getParentNode().get().getSpouse().get().getDirectChildRelations().stream()
				.filter(isFemale).map(personToName).collect(Collectors.joining(" "));
	};

	public static final BiFunction<String, FamilyTree, String> maternalUncleRelHandler = (String nodeName,
			FamilyTree family) -> {
		Person node = family.getKing().searchNode(nodeName).get();

		return node.getParentNode().get().getParentNode().get().getDirectChildRelations().stream().filter(isMale)
				.map(personToName).collect(Collectors.joining(" "));

	};

	public static final BiFunction<String, FamilyTree, String> paternalUncleRelHandler = (String nodeName,
			FamilyTree family) -> {
		Person node = family.getKing().searchNode(nodeName).get();

		return node.getParentNode().get().getParentNode().get().getWife().getDirectChildRelations().stream().filter(
				n -> n.getGender() == Gender.Male && node.getParentNode().get().getHusband().getName() != n.getName())
				.map(personToName).collect(Collectors.joining(" "));
	};

}

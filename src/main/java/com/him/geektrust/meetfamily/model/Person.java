package com.him.geektrust.meetfamily.model;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * This is the main node class.
 * @author himanshu
 *
 */
@Setter
@Getter
@Builder
public class Person {

	private String name;
	private Gender gender;
	private List<Person> directChildRelations;
	@Builder.Default()
	private Optional<Person> parallelRelative = Optional.empty();
	@Builder.Default()
	private Optional<Person> parentNode = Optional.empty();
	@Builder.Default()
	private BiPredicate<Person, String> compareNodeName = (node, name) -> node.getName().equalsIgnoreCase(name);

	public enum Gender {
		Male, Female
	}

	public void setParallelRelative(String name, Gender gender) {
		parallelRelative = Optional.of(Person.builder().name(name).gender(gender).build());
		parallelRelative.get().setParallelRelative(Optional.of(this));
	}

	public Optional<Person> searchNode(String nodeName) {
		return searchNode(nodeName, this);
	}

	public Person getWife() {
		if (getGender() == Gender.Female) {
			return this;
		} else {
			return getParallelRelative().get();
		}
	}

	public Person getHusband() {
		if (getGender() == Gender.Male) {
			return this;
		} else {
			return getParallelRelative().get();
		}
	}

	public Optional<Person> getParentNode() {
		if (parentNode.isPresent()) {
			return parentNode;
		} else {
			return getParallelRelative() != null ? getParallelRelative().get().getParentNode() : Optional.empty();
		}
	}

	private Optional<Person> searchNode(String nodeName, Person currentNode) {

		if (currentNode.getName().equals(nodeName)) {
			return Optional.of(currentNode);
		} else {
			if (currentNode.getGender() != Gender.Female && currentNode.getParallelRelative() != null) {
				currentNode = currentNode.getParallelRelative().isPresent() ? currentNode.getParallelRelative().get()
						: null;
			}
		}
		Optional<Person> searchedNode = Optional.empty();
		if (currentNode!=null && currentNode.getDirectChildRelations() != null && currentNode.getDirectChildRelations().size() != 0) {
			List<Person> childrenNodes = currentNode.getDirectChildRelations();
			for (Person currentChildNode : childrenNodes) { // horizontal iteration

				// vertical iteration
				while (currentChildNode != null) {
					if (compareNodeName.test(currentChildNode, nodeName)) {
						return Optional.of(currentChildNode);
					} else {

						Person wife = currentChildNode.getParallelRelative().isPresent()
								? currentChildNode.getParallelRelative().get()
								: null;
						if (wife != null && wife.getName().equalsIgnoreCase(nodeName)) {
							return Optional.of(wife);
						} 
					}

					Optional<Person> person = searchNode(nodeName, currentChildNode);
					if (person.isPresent()) {
						return person;
					} else {
						break;
					}
				}
			}
		} else {
		}
		return searchedNode;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", gender=" + gender + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}

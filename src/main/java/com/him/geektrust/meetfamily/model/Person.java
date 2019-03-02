package com.him.geektrust.meetfamily.model;

import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class Person {
	
	private String name;
	private Gender gender;
	private List<Person> directChildRelations;
	private Person parallelRelative;
	@Builder.Default()
	private Optional<String> relationWithParentNode=Optional.empty();
	
	public enum Gender{
		Male, Female
	}
	
	public void setParallelRelative(String name, Gender gender) {
		parallelRelative=Person.builder().name(name).gender(gender).build();
	}

}

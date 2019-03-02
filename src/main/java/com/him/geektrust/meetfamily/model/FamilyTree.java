package com.him.geektrust.meetfamily.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
//@AllArgsConstructor
public class FamilyTree {

	// private List<Person> family;
	private Person king;
	private Person queen;

}

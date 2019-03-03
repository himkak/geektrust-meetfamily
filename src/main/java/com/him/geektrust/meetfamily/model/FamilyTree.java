package com.him.geektrust.meetfamily.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * This pojo is the main holder object of the family, containing the king and the queen.
 * @author himanshu
 *
 */
@Getter
@Setter
@Builder
//@AllArgsConstructor
public class FamilyTree {

	// private List<Person> family;
	private Person king;
	private Person queen;


}

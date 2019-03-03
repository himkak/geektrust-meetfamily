package com.him.geektrust.meetfamily.model;

import java.util.function.BiFunction;

import com.him.geektrust.meetfamily.operations.RelationshipHandler;

/**
 * Relationship enum, tied with its function.
 * 
 * @author himanshu
 *
 */
public enum Relationship {

	Paternal_Uncle("Paternal-Uncle", RelationshipHandler.paternalUncleRelHandler),
	Maternal_Uncle("Maternal-Uncle", RelationshipHandler.maternalUncleRelHandler),
	Paternal_Aunt("Paternal-Aunt", RelationshipHandler.paternalAuntRelHandler),
	Maternal_Aunt("Maternal-Aunt", RelationshipHandler.maternalAuntRelHandler),
	Sister_In_Law("Sister-In-Law", RelationshipHandler.sisterInLawRelHandler),
	Brother_In_Law("Brother-In-Law", RelationshipHandler.brotherInLawRelHandler),
	Son("Son", RelationshipHandler.sonRelHandler), Daughter("Daughter", RelationshipHandler.daughterRelHandler),
	Siblings("Siblings", RelationshipHandler.siblingRelHandler);

	private BiFunction<String, FamilyTree, String> relationshipHandler;

	private String relationName;

	Relationship(String relationName, BiFunction<String, FamilyTree, String> relationshipHandler) {
		this.relationName = relationName;
		this.relationshipHandler = relationshipHandler;
	}

	public static Relationship getEnum(String relName) {
		for (Relationship value : values()) {
			if (value.relationName.equals(relName)) {
				return value;
			}
		}
		return null;
	}

	public String handleRelationship(String personName, FamilyTree family) {
		return relationshipHandler.apply(personName, family);
	}

}

package com.him.geektrust.meetfamily.model;

public enum Relationship {

	Paternal_Uncle("Paternal-Uncle"), Maternal_Uncle("Maternal-Uncle"), Paternal_Aunt("Paternal-Aunt"),
	Maternal_Aunt("Maternal-Aunt"), Sister_In_Law("Sister-In-Law"), Brother_In_Law("Brother-In-Law"), Son("Son"),
	Daughter("Daughter"), Siblings("Siblings");

	private String relationName;

	Relationship(String relationName) {
		this.relationName = relationName;
	}

	public static Relationship getEnum(String relName) {
		for (Relationship value : values()) {
			if (value.relationName.equals(relName)) {
				return value;
			}
		}
		return null;
	}

}

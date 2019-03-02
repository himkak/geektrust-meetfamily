package com.him.geektrust.meetfamily.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InputFileData {
	
	private Operation operation;
	private List<String> arguments;

}

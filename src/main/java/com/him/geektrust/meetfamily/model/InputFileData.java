package com.him.geektrust.meetfamily.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * This pojo class holds the input command provided in the file.s
 * @author himanshu
 *
 */
@Getter
@Setter
@Builder
public class InputFileData {

	private Operation operation;
	private List<String> arguments;

	

}

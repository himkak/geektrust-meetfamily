package com.him.geektrust.meetfamily.operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.him.geektrust.meetfamily.model.InputFileData;
import com.him.geektrust.meetfamily.model.Operation;

/**
 * The task of this class is to read the file, and populate the InputFileData
 * object and return it.
 * 
 * @author himanshu
 *
 */
public class FileProcessor {

	public static List<InputFileData> processFile(String pathOfInputFile) {
		File file = new File(pathOfInputFile);
		List<InputFileData> inputData = new LinkedList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String st;
			while ((st = br.readLine()) != null) {
				List<String> inputArgs = Arrays.asList(st.split(" "));
				InputFileData inputLineData = InputFileData.builder().operation(Operation.valueOf(inputArgs.get(0)))
						.arguments(inputArgs.subList(1, inputArgs.size())).build();
				inputData.add(inputLineData);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return inputData;

	}

}

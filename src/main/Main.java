package main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

//Libraries needed 
/*
 * commons io
 */

public class Main {
	private static Vector<String> files = new Vector<String>();

	public static void main(String[] args) {
		mainFunction();
	}

	private static void mainFunction() {
		String[] listToCheck = listOfFilesInDirectory("PATH TO GET FILES FROM");

		for (int i = 0; i < listToCheck.length; i++) {
			try {
				if (checkIfExists(FileUtils.readFileToByteArray(new File(listToCheck[i])), new File(listToCheck[i]).getName()))
					System.out.println("Duplicate Detected");
				else
					saveFile((new File(listToCheck[i]).getName()),FilenameUtils.getExtension(listToCheck[i]),FileUtils.readFileToByteArray(new File(listToCheck[i])));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				continue;
			}
		}
		System.gc();
	}

	private static boolean checkIfExists(byte[] fileToCheck, String name) {
		if (files == null || files.isEmpty())
			return false;
		
		String[] listToCheckAgainst = listOfFilesInDirectory("/Volumes/data_bank1/Photos-Unique");

		for (int i = 0; i < listToCheckAgainst.length; i++) {
			try {
				if (name.equals(new File(listToCheckAgainst[i]).getName())){
					if (Arrays.equals(fileToCheck, FileUtils.readFileToByteArray(new File(listToCheckAgainst[i]))))
						return true;
				}
			} catch (IOException e) {
				continue;
			}
		}

		return false;
	}

	private static String[] listOfFilesInDirectory(String path) {
		String fileList = FileUtils.listFiles(new File(path),TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE).toString();
		String[] arrayOfFileNames = Arrays.asList(fileList.split(", ")).toArray(new String[(Arrays.asList(fileList.split(", "))).size()]);

		arrayOfFileNames[0] = arrayOfFileNames[0].replace("[", "");
		arrayOfFileNames[arrayOfFileNames.length - 1] = arrayOfFileNames[arrayOfFileNames.length - 1].replace("]", "");

		return arrayOfFileNames;
	}

	private static void saveFile(String filename, String extension,
			byte[] fileInByteArray) throws IOException {

		if (checkIfNameExists(filename)) {
			for (int i = 0; i > -1; i++) {
				String temp = filename + Integer.toString(i);
				if (checkIfNameExists(temp))
					continue;
				else
					break;
			}
		}

		String basePath = "PATH To Save the Unique Files To";
		File temp = new File(basePath + filename);
		FileUtils.writeByteArrayToFile(temp, fileInByteArray);
		
		files.add(filename);

	}

	private static boolean checkIfNameExists(String name) {
		for (int i = 0; i < files.size(); i++)
			if (files.elementAt(i).equals(name))
				return true;
		return false;
	}
}

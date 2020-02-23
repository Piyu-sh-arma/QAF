package com.SupportUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;

public class FilesUtil {

	public static boolean fileExist(String path) {
		return (new File(path).isFile());
	}

	public static boolean folderExist(String path) {
		return (new File(path).isDirectory());
	}

	public static String readFile(String path) throws IOException {
		return FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
	}

	public static void writeToFile(String path, String data) throws IOException {
		FileUtils.write(new File(path), data, StandardCharsets.UTF_8);
	}

	public static void writeToFile(String path, String data, boolean append) throws IOException {
		FileUtils.write(new File(path), data, StandardCharsets.UTF_8, append);
	}

}

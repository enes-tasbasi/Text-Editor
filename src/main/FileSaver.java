package main;

import java.io.*;

public class FileSaver {
	public FileSaver(String text, File file) throws IOException {
		FileWriter fw = new FileWriter(file + ".txt");
		fw.write(text);
		fw.close();
	}
}

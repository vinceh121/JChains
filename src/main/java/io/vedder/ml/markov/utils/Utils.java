package io.vedder.ml.markov.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Utils {
	public static void writeToFile(final String filePath, final String contents) {
		try {
			final File file = new File(filePath);

			if (!file.exists()) {
				file.createNewFile();
			}
			final FileWriter fw = new FileWriter(file.getAbsoluteFile());
			final BufferedWriter bw = new BufferedWriter(fw);
			bw.write(contents);
			bw.close();

		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> readFile(final String filePath) {
		final List<String> lines = new LinkedList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(filePath)));
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line + "\n");
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}

		return lines;
	}
}

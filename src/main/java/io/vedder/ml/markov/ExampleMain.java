package io.vedder.ml.markov;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import io.vedder.ml.markov.consumer.TokenConsumer;
import io.vedder.ml.markov.consumer.file.FileTokenConsumer;
import io.vedder.ml.markov.generator.Generator;
import io.vedder.ml.markov.generator.file.FileGenerator;
import io.vedder.ml.markov.holder.MapTokenHolder;
import io.vedder.ml.markov.holder.TokenHolder;
import io.vedder.ml.markov.threading.JobManager;
import io.vedder.ml.markov.tokenizer.file.FileTokenizer;
import io.vedder.ml.markov.tokens.Token;

public class ExampleMain {

	static Logger log = Logger.getLogger(ExampleMain.class);
	public static boolean verbose = false;

	private static int lookback = 1;
	private static int mapInitialSize = 10000;
	private static int numSent = 1;
	private static List<String> filePaths = null;

	/**
	 * Example use of the Markov Chain library.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			args = new String[] { "-v", "-i", "50000", "3", "10", "-f", "inputs/shakespear.txt",
					"inputs/AllsWellThatEndsWell.txt" };
		}

		ExampleMain.parseArgs(Arrays.asList(args));

		if (!ExampleMain.verbose) {
			ExampleMain.log.setLevel(Level.WARN);
		}

		// Data structure to hold tokens
		final TokenHolder tokenHolder = new MapTokenHolder(ExampleMain.mapInitialSize);

		final JobManager jm = new JobManager();

		// Fills the TokenHolder with tokens
		for (final String filePath : ExampleMain.filePaths) {
			final FileTokenizer fileTokenizer = new FileTokenizer(tokenHolder, ExampleMain.lookback, filePath);
			jm.addTokenizer(fileTokenizer);
		}

		jm.runAll();

		// Uses the TokenHolder to generate Collections of tokens.
		final Generator g = new FileGenerator(tokenHolder, ExampleMain.lookback);

		// Takes Collections of tokens and consumes them
		final TokenConsumer tc = new FileTokenConsumer();

		// Kicks off the tokenization process

		final List<Collection<Token>> tokensCollections = new LinkedList<Collection<Token>>();

		// Creates Lists of tokens
		for (int i = 0; i < ExampleMain.numSent / 2; i++) {
			tokensCollections.add(g.generateTokenList());
		}

		// Creates lazy collections of tokens
		for (int i = 0; i < ExampleMain.numSent / 2 + ExampleMain.numSent % 2; i++) {
			tokensCollections.add(g.generateLazyTokenList());
		}

		// Consumer consumes both types of collections
		ExampleMain.log.info("Printing Tokens...\n" + "===============\n");

		for (final Collection<Token> l : tokensCollections) {
			tc.consume(l);
		}

	}

	/**
	 * Parses CL args and configures the application for launch.
	 *
	 * @param args
	 */
	private static void parseArgs(final List<String> args) {
		if (args.size() < 3 || !args.contains("-f")) {
			ExampleMain.printUsageAndExit();
		}

		for (String a : args) {
			a = a.trim();
		}

		if (args.contains("-v")) {
			ExampleMain.verbose = true;
		}

		if (args.contains("-i")) {
			final int index = args.indexOf("-i");
			ExampleMain.mapInitialSize = Integer.parseInt(args.get(index + 1));
		}

		final int dashFIndex = args.indexOf("-f");

		final String lookbackString = args.get(dashFIndex - 2);
		final String numSentString = args.get(dashFIndex - 1);
		final List<String> filePathStrings = args.subList(dashFIndex + 1, args.size());

		ExampleMain.lookback = Integer.parseInt(lookbackString);
		ExampleMain.numSent = Integer.parseInt(numSentString);
		ExampleMain.filePaths = filePathStrings;
	}

	private static void printUsageAndExit() {
		System.out.println("ARGS: [-v] [-i (map initial size)] <lookback> <number of sentences> [-f <input files>]");
		System.exit(-1);
	}
}

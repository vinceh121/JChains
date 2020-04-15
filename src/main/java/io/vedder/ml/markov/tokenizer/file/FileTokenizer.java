package io.vedder.ml.markov.tokenizer.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import io.vedder.ml.markov.LookbackContainer;
import io.vedder.ml.markov.holder.TokenHolder;
import io.vedder.ml.markov.tokenizer.Tokenizer;
import io.vedder.ml.markov.tokens.Token;
import io.vedder.ml.markov.tokens.file.DelimitToken;
import io.vedder.ml.markov.tokens.file.StringToken;
import io.vedder.ml.markov.utils.Utils;

/**
 * Implementation of the {@link Tokenizer} for text files.
 *
 * @author kyle
 *
 */
public class FileTokenizer extends Tokenizer {

	static Logger log = Logger.getLogger(FileTokenizer.class);

	private final int LOOKBACK;
	private final Set<String> END_MARKS;
	private final Token DELIMIT_TOKEN = DelimitToken.getInstance();
	private final String filePath;

	private List<String> listStrings = null;

	public FileTokenizer(final TokenHolder th, final int lookback, final String filePath) {
		super(th);
		this.END_MARKS = new HashSet<String>(Arrays.asList(".", "?", "!"));
		this.LOOKBACK = lookback;
		this.filePath = filePath;
	}

	@Override
	public void tokenize() {
		this.listStrings = this.splitStrings(Utils.readFile(this.filePath));
		this.addTokensToHolder();
	}

	private void addTokensToHolder() {
		final List<Token> l = this.getTokens(this.listStrings);
		this.addTokenList(l);
	}

	private void addTokenList(final List<Token> tokens) {
		FileTokenizer.log.info("Chunking " + tokens.size() + " tokens for file \"" + this.filePath + "\"...\n");
		for (int wordIndex = this.LOOKBACK; wordIndex < tokens.size() - 1; wordIndex++) {

			// List for the lookback
			final List<Token> lookBackList = new ArrayList<Token>(this.LOOKBACK);

			Token t = null;

			// loop adds lists to ensure that lookback lists of size 1 to size
			// "lookBack" are added to the lookbackList
			chunkLoop: for (int lookBackCount = 0; lookBackCount < this.LOOKBACK; lookBackCount++) {
				t = tokens.get(wordIndex - lookBackCount);
				lookBackList.add(0, t);

				// constructor call is to copy lookBackList
				this.th.addToken(new LookbackContainer(this.LOOKBACK, lookBackList), tokens.get(wordIndex + 1));

				// if lookback hits delimiter token, stop
				if (t == this.DELIMIT_TOKEN) {
					break chunkLoop;
				}
			}
		}
	}

	private List<Token> getTokens(final List<String> listStrings) {
		final List<Token> tokenList = new LinkedList<Token>();
		tokenList.add(this.DELIMIT_TOKEN);

		for (final String s : listStrings) {
			tokenList.add(new StringToken(s));
			if (this.END_MARKS.contains(s)) {
				tokenList.add(this.DELIMIT_TOKEN);
			}
		}

		// check to see if ends with delimiter token
		if (tokenList.get(tokenList.size() - 1) != this.DELIMIT_TOKEN) {
			tokenList.add(this.DELIMIT_TOKEN);
		}
		return tokenList;
	}

	private List<String> splitStrings(final List<String> lines) {
		// Regex from:
		// http://stackoverflow.com/questions/24222730/split-a-string-and-separate-by-punctuation-and-whitespace
		final List<String> splits = new ArrayList<String>();
		List<String> temp;
		for (final String s : lines) {
			temp = Arrays.asList(s.replaceAll("  ", " ")
					.split("\\s+|(?=\\W\\p{Punct}|\\p{Punct}\\W)|(?<=\\W\\p{Punct}|\\p{Punct}\\W})"));
			for (final String t : temp) {
				if (t != "" && !t.isEmpty()) {
					splits.add(t);
				}
			}
		}
		return splits;
	}
}

package io.vedder.ml.markov.generator.file;

import java.util.LinkedList;
import java.util.List;

import io.vedder.ml.markov.LookbackContainer;
import io.vedder.ml.markov.collection.TokenCollection;
import io.vedder.ml.markov.collection.file.FileTokenCollection;
import io.vedder.ml.markov.generator.Generator;
import io.vedder.ml.markov.holder.TokenHolder;
import io.vedder.ml.markov.tokens.Token;
import io.vedder.ml.markov.tokens.file.DelimitToken;

public class FileGenerator extends Generator {

	private final int LOOKBACK;
	private final Token DELIMIT_TOKEN = DelimitToken.getInstance();

	public FileGenerator(final TokenHolder th, final int lookback) {
		super(th);
		this.LOOKBACK = lookback;
	}

	@Override
	public List<Token> generateTokenList() {
		final List<Token> line = new LinkedList<Token>();

		final LookbackContainer c = new LookbackContainer(this.LOOKBACK, this.DELIMIT_TOKEN);
		Token t = null;
		while ((t = this.th.getNext(c)) != this.DELIMIT_TOKEN && t != null) {
			line.add(t);
			c.addToken(t);
		}
		return line;
	}

	@Override
	public TokenCollection generateLazyTokenList() {
		return new FileTokenCollection(this.th, this.LOOKBACK);
	}

}

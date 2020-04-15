package io.vedder.ml.markov.collection.file;

import java.util.Iterator;

import io.vedder.ml.markov.LookbackContainer;
import io.vedder.ml.markov.holder.TokenHolder;
import io.vedder.ml.markov.tokens.Token;
import io.vedder.ml.markov.tokens.file.DelimitToken;

public class FileTokenCollectionIterator implements Iterator<Token> {

	private TokenHolder th;
	private final Token DELIMIT_TOKEN = DelimitToken.getInstance();

	private Token currentToken;

	private final LookbackContainer c;

	public FileTokenCollectionIterator(final TokenHolder th, final int lookBack) {
		super();
		this.th = th;
		this.c = new LookbackContainer(lookBack, this.DELIMIT_TOKEN);
		this.currentToken = this.nextToken();
	}

	private Token nextToken() {
		final Token t = this.th.getNext(this.c);
		this.c.addToken(t);
		return t;
	}

	public boolean hasNext() {
		return this.currentToken != null && this.currentToken != this.DELIMIT_TOKEN;
	}

	public Token next() {
		final Token t = this.currentToken;
		this.currentToken = this.nextToken();
		return t;
	}

}

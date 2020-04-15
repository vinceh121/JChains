package io.vedder.ml.markov.collection.file;

import java.util.Iterator;

import io.vedder.ml.markov.collection.TokenCollection;
import io.vedder.ml.markov.holder.TokenHolder;
import io.vedder.ml.markov.tokens.Token;

public class FileTokenCollection extends TokenCollection {

	private final int LOOKBACK;

	public FileTokenCollection(final TokenHolder th, final int lookBack) {
		super(th);
		this.LOOKBACK = lookBack;
	}

	public Iterator<Token> iterator() {
		return new FileTokenCollectionIterator(this.th, this.LOOKBACK);
	}

}

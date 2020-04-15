package io.vedder.ml.markov.consumer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import io.vedder.ml.markov.tokens.Token;

public class StringBuilderTokenConsumer extends TokenConsumer {
	private static final List<String> PUNCTUATION = Arrays.asList(",", ";", ":", ".", "?", "!", "-");

	private final StringBuilder builder = new StringBuilder();

	@Override
	public void consume(final Collection<Token> collection) {
		collection.forEach(new Consumer<Token>() {
			public void accept(final Token w) {
				if (!StringBuilderTokenConsumer.PUNCTUATION.contains(w.toString())) {
					StringBuilderTokenConsumer.this.builder.append(" ");
				}

				StringBuilderTokenConsumer.this.builder.append(w.toString());
			}
		});

		this.builder.append("\n");
	}

	public StringBuilder getBuilder() {
		return this.builder;
	}
}

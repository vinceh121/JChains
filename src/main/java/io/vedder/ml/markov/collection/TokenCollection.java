package io.vedder.ml.markov.collection;

import java.util.Collection;

import io.vedder.ml.markov.holder.TokenHolder;
import io.vedder.ml.markov.tokens.Token;

public abstract class TokenCollection implements Collection<Token> {

	protected TokenHolder th;

	public TokenCollection(final TokenHolder th) {
		this.th = th;
	}

	public boolean isEmpty() {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	public int size() {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	public boolean contains(final Object o) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	public Object[] toArray() {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	public <T> T[] toArray(final T[] a) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	public boolean add(final Token e) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	public boolean remove(final Object o) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	public boolean containsAll(final Collection<?> c) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	public boolean addAll(final Collection<? extends Token> c) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	public boolean removeAll(final Collection<?> c) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	public boolean retainAll(final Collection<?> c) {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

	public void clear() {
		throw new RuntimeException("Cannot call this method on a lazy TokenCollection");
	}

}

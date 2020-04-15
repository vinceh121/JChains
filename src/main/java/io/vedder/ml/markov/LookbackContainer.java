package io.vedder.ml.markov;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import io.vedder.ml.markov.tokens.Token;

public class LookbackContainer {
	private LinkedList<Token> tokenList = null;

	private final int MAX_SIZE;

	public LookbackContainer(final int maxSize, final Token ts) {
		this(maxSize, Arrays.asList(ts));
	}

	public LookbackContainer(final int maxSize, final List<Token> ts) {
		this.MAX_SIZE = maxSize;
		this.tokenList = new LinkedList<Token>(ts);
	}

	/**
	 * Adds a token while ensuring that the maximum size property is maintained.
	 *
	 * @param token
	 * @param maxSize
	 */
	public void addToken(final Token token) {
		if (this.tokenList == null) {
			this.tokenList = new LinkedList<Token>();
		}

		while (this.tokenList.size() > this.MAX_SIZE) {
			this.tokenList.removeFirst();
		}

		this.tokenList.add(token);
	}

	/**
	 * Returns if this container is empty.
	 *
	 * @return
	 */
	public boolean isEmpty() {
		return this.tokenList.isEmpty();
	}

	public LookbackContainer shrinkContainer() {
		final List<Token> lst = new LinkedList<Token>(this.tokenList);
		if (!lst.isEmpty()) {
			lst.remove(0);
		}
		return new LookbackContainer(this.MAX_SIZE - 1, lst);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.tokenList == null ? 0 : this.tokenList.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final LookbackContainer other = (LookbackContainer) obj;
		if (this.tokenList == null) {
			if (other.tokenList != null) {
				return false;
			}
		} else if (!this.tokenList.equals(other.tokenList)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "LookbackContainer [tokenList=" + this.tokenList + "]";
	}

}

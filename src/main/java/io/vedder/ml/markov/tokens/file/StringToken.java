package io.vedder.ml.markov.tokens.file;

import io.vedder.ml.markov.tokens.Token;

/**
 * Token for representing a single word.
 *
 * @author kyle
 *
 */
public class StringToken extends Token {

	private final String data;

	public StringToken(final String data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.data == null ? 0 : this.data.hashCode());
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
		final StringToken other = (StringToken) obj;
		if (this.data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!this.data.equals(other.data)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.data;
	}

}

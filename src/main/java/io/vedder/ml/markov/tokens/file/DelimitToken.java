package io.vedder.ml.markov.tokens.file;

import io.vedder.ml.markov.tokens.Token;

/**
 * Token for delimiting the start and end of sentences.
 *
 * @author kyle
 *
 */
public class DelimitToken extends Token {
	private static final String TOKEN_STRING = "<DELIMIT TOKEN>";

	private static DelimitToken t = null;

	public static DelimitToken getInstance() {
		if (DelimitToken.t == null) {
			DelimitToken.t = new DelimitToken();
		}
		return DelimitToken.t;
	}

	private DelimitToken() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (DelimitToken.TOKEN_STRING == null ? 0 : DelimitToken.TOKEN_STRING.hashCode());
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
		final DelimitToken other = (DelimitToken) obj;
		if (DelimitToken.TOKEN_STRING == null) {
			if (DelimitToken.TOKEN_STRING != null) {
				return false;
			}
		} else if (!DelimitToken.TOKEN_STRING.equals(DelimitToken.TOKEN_STRING)) {
			return false;
		}
		return true;
	}

}

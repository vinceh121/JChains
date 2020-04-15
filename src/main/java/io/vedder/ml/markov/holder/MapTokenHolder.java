package io.vedder.ml.markov.holder;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import io.vedder.ml.markov.LookbackContainer;
import io.vedder.ml.markov.tokens.Token;

/**
 * HashMap based implementation of {@link TokenHolder}.
 *
 * @author kyle
 *
 * @param <T>
 */
public class MapTokenHolder implements TokenHolder {

	private Map<LookbackContainer, Map<Token, Integer>> tokenMap;
	private Random r = null;

	public MapTokenHolder(final int mapInitialSize) {
		this.r = new Random();
		this.tokenMap = new ConcurrentHashMap<LookbackContainer, Map<Token, Integer>>(mapInitialSize);
	}

	public void addToken(final LookbackContainer lbc, final Token next) {
		Map<Token, Integer> nextElementMap = null;
		if (this.tokenMap.containsKey(lbc)) {
			nextElementMap = this.tokenMap.get(lbc);
		} else {
			nextElementMap = new HashMap<Token, Integer>();
		}

		if (!nextElementMap.isEmpty() && nextElementMap.containsKey(next)) {
			nextElementMap.put(next, nextElementMap.get(next) + 1);

		} else {
			nextElementMap.put(next, 1);
		}
		this.tokenMap.put(lbc, nextElementMap);
	}

	public Token getNext(LookbackContainer look) {
		Map<Token, Integer> nextElementList = null;

		// Look for the largest lookback container which has a match. May be
		// empty.
		while (!look.isEmpty() && (nextElementList = this.tokenMap.get(look)) == null) {
			look = look.shrinkContainer();
		}

		if (nextElementList == null) {
			throw new RuntimeException("Unable to find match to given input");
		}

		int sum = 0;
		// calculate sum
		for (final Entry<Token, Integer> entry : nextElementList.entrySet()) {
			sum += entry.getValue();
		}

		int randInt = this.r.nextInt(sum) + 1;
		for (final Entry<Token, Integer> entry : nextElementList.entrySet()) {
			if (randInt <= entry.getValue()) {
				return entry.getKey();
			}
			randInt -= entry.getValue();
		}

		throw new RuntimeException("Failed to get next token");
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		final List<String> lst = new LinkedList<String>();
		for (final Entry<LookbackContainer, Map<Token, Integer>> e : this.tokenMap.entrySet()) {
			lst.add(e.toString() + "\n");

		}
		Collections.sort(lst);

		for (final String l : lst) {
			sb.append(l);
		}

		return sb.toString();
	}

}

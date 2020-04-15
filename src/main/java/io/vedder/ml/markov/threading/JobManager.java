package io.vedder.ml.markov.threading;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import io.vedder.ml.markov.tokenizer.Tokenizer;

public class JobManager {

	static Logger log = Logger.getLogger(JobManager.class);

	private List<Runnable> jobs = null;

	public JobManager() {
		this.jobs = new LinkedList<Runnable>();
	}

	public void addTokenizer(final Tokenizer t) {
		this.addJob(new TokenizerJob(t));
	}

	public void addJob(final Runnable t) {
		this.jobs.add(t);
	}

	public void runAll() {
		final List<Thread> threads = new LinkedList<Thread>();
		for (final Runnable r : this.jobs) {
			final Thread th = new Thread(r);
			threads.add(th);
			th.start();
		}

		try {
			JobManager.log.info("Awating all job completions...\n");
			for (final Thread th : threads) {
				th.join();
			}
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		JobManager.log.info("All jobs complete!\n");
	}
}

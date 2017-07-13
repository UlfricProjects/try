package com.ulfric.tryto;

import java.io.IOException;
import java.io.UncheckedIOException;

public class Try {

	public static void toRun(CheckedRunnable runnable) {
		try {
			runnable.run();
		} catch (Exception thrown) {
			throw new RuntimeException(thrown);
		}
	}

	public static void toRun(IoCheckedRunnable runnable) {
		try {
			runnable.run();
		} catch (IOException thrown) {
			throw new UncheckedIOException(thrown);
		}
	}

	private Try() {
	}

}
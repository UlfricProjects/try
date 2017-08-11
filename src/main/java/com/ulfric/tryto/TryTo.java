package com.ulfric.tryto;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;

public class TryTo {

	public static void run(CheckedRunnable runnable) {
		try {
			runnable.run();
		} catch (Exception thrown) {
			throw new RuntimeException(thrown);
		}
	}

	public static void runIo(IoCheckedRunnable runnable) {
		try {
			runnable.run();
		} catch (IOException thrown) {
			throw new UncheckedIOException(thrown);
		}
	}

	public static <T> T get(CheckedSupplier<T> supplier) {
		try {
			return supplier.get();
		} catch (Exception thrown) {
			throw new RuntimeException(thrown);
		}
	}

	public static <T> T getIo(IoCheckedSupplier<T> supplier) {
		try {
			return supplier.get();
		} catch (IOException thrown) {
			throw new UncheckedIOException(thrown);
		}
	}

	public static <T, R> R apply(T value, CheckedFunction<T, R> function) {
		try {
			return function.apply(value);
		} catch (Exception thrown) {
			throw new RuntimeException(thrown);
		}
	}

	public static <T, R> R applyIo(T value, IoCheckedFunction<T, R> function) {
		try {
			return function.apply(value);
		} catch (IOException thrown) {
			throw new UncheckedIOException(thrown);
		}
	}

	public static <T extends Closeable, R> R applyAutoclose(T value, IoCheckedFunction<T, R> function) {
		return applyIo(value, v -> {
			try (T closeThis = value) {
				return function.apply(value);
			}
		});
	}

	private TryTo() {
	}

}
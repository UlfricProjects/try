package com.ulfric.tryto;

import java.io.Closeable;
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

	public static <T> T toGet(CheckedSupplier<T> supplier) {
		try {
			return supplier.get();
		} catch (Exception thrown) {
			throw new RuntimeException(thrown);
		}
	}

	public static void toRunIo(IoCheckedRunnable runnable) {
		try {
			runnable.run();
		} catch (IOException thrown) {
			throw new UncheckedIOException(thrown);
		}
	}

	public static <T, R> R toApplyIo(T value, IoCheckedFunction<T, R> function) {
		try {
			return function.apply(value);
		} catch (IOException thrown) {
			throw new UncheckedIOException(thrown);
		}
	}

	public static <T extends Closeable, R> R toApplyAutoclose(T value, IoCheckedFunction<T, R> function) {
		return toApplyIo(value, v -> {
			try (T closeThis = value) {
				return function.apply(value);
			}
		});
	}

	public static <T> T toGetIo(IoCheckedSupplier<T> supplier) {
		try {
			return supplier.get();
		} catch (IOException thrown) {
			throw new UncheckedIOException(thrown);
		}
	}

	private Try() {
	}

}
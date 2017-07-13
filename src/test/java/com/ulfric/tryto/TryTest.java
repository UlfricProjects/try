package com.ulfric.tryto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.google.common.truth.Truth;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Constructor;

@RunWith(JUnitPlatform.class)
class TryTest {

	@Test
	void testToRunCheckedRunnableNoExceptions() {
		boolean[] ran = new boolean[1];
		CheckedRunnable run = () -> ran[0] = true;
		Try.toRun(run);
		Truth.assertThat(ran[0]).isTrue();
	}

	@Test
	void testToRunCheckedRunnableRethrowsCheckedException() {
		CheckedRunnable run = () -> { throw new Exception(); };
		Assertions.assertThrows(RuntimeException.class, () -> Try.toRun(run));
	}

	@Test
	void testToRunIoCheckedRunnableNoExceptions() {
		boolean[] ran = new boolean[1];
		IoCheckedRunnable run = () -> ran[0] = true;
		Try.toRun(run);
		Truth.assertThat(ran[0]).isTrue();
	}

	@Test
	void testToRunIoCheckedRunnableRethrowsCheckedIoException() {
		IoCheckedRunnable run = () -> { throw new IOException(); };
		Assertions.assertThrows(UncheckedIOException.class, () -> Try.toRun(run));
	}

	@Test
	void testInstantiate() throws Exception {
		Constructor<?> constructor = Try.class.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		constructor.newInstance();
	}

}
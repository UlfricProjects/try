package com.ulfric.tryto;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.google.common.truth.Truth;

import com.ulfric.veracity.Veracity;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Constructor;

@RunWith(JUnitPlatform.class)
class TryTest {

	@Test
	void testToRunNoExceptions() {
		boolean[] ran = new boolean[1];
		CheckedRunnable run = () -> ran[0] = true;
		Try.toRun(run);
		Truth.assertThat(ran[0]).isTrue();
	}

	@Test
	void testToRunRethrowsCheckedException() {
		CheckedRunnable run = () -> { throw new Exception(); };
		Veracity.assertThat(() -> Try.toRun(run)).doesThrow(RuntimeException.class);
	}

	@Test
	void testToGetNoExceptions() {
		CheckedSupplier<Boolean> get = () -> true;
		Truth.assertThat(Try.toGet(get)).isTrue();
	}

	@Test
	void testToGetRethrowsCheckedException() {
		CheckedSupplier<?> get = () -> { throw new Exception(); };
		Veracity.assertThat(() -> Try.toGet(get)).doesThrow(RuntimeException.class);
	}

	@Test
	void testToIoRunIoNoExceptions() {
		boolean[] ran = new boolean[1];
		IoCheckedRunnable run = () -> ran[0] = true;
		Try.toRunIo(run);
		Truth.assertThat(ran[0]).isTrue();
	}

	@Test
	void testToRunIoRethrowsCheckedIoException() {
		IoCheckedRunnable run = () -> { throw new IOException(); };
		Veracity.assertThat(() -> Try.toRunIo(run)).doesThrow(UncheckedIOException.class);
	}

	@Test
	void testToGetIoRethrowsCheckedIoException() {
		IoCheckedSupplier<?> run = () -> { throw new IOException(); };
		Veracity.assertThat(() -> Try.toGetIo(run)).doesThrow(UncheckedIOException.class);
	}

	@Test
	void testToGetIoNoExceptions() {
		IoCheckedSupplier<Integer> run = () -> 5;
		Veracity.assertThat(() -> Try.toGetIo(run)).runsWithoutExceptions();
	}

	@Test
	void testToApplyAutocloseNoExceptions() throws Exception {
		IoCheckedFunction<Closeable, ?> run = ignore -> null;
		Closeable close = Mockito.mock(Closeable.class);
		Veracity.assertThat(() -> Try.toApplyAutoclose(close, run)).runsWithoutExceptions();
		Mockito.verify(close, Mockito.atLeastOnce()).close();
	}

	@Test
	void testToApplyAutocloseFunctionThrowsIoException() throws Exception {
		IoCheckedFunction<Closeable, ?> run = ignore -> { throw new IOException(); };
		Closeable close = Mockito.mock(Closeable.class);
		Veracity.assertThat(() -> Try.toApplyAutoclose(close, run)).doesThrow(UncheckedIOException.class);
		Mockito.verify(close, Mockito.atLeastOnce()).close();
	}

	@Test
	void testToApplyAutocloseCloseThrowsIoException() throws Exception {
		IoCheckedFunction<Closeable, ?> run = ignore -> null;
		Closeable close = Mockito.mock(Closeable.class);
		Mockito.doThrow(IOException.class).when(close).close();
		Veracity.assertThat(() -> Try.toApplyAutoclose(close, run)).doesThrow(UncheckedIOException.class);
		Mockito.verify(close, Mockito.atLeastOnce()).close();
	}

	@Test
	void testToApplyAutocloseCloseAndFunctionThrowIoException() throws Exception {
		IoCheckedFunction<Closeable, ?> run = ignore -> { throw new IOException(); };
		Closeable close = Mockito.mock(Closeable.class);
		Mockito.doThrow(IllegalStateException.class).when(close).close();
		Veracity.assertThat(() -> Try.toApplyAutoclose(close, run)).doesThrow(UncheckedIOException.class);
		Mockito.verify(close, Mockito.atLeastOnce()).close();
	}

	@Test
	void testInstantiate() throws Exception {
		Constructor<?> constructor = Try.class.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		constructor.newInstance();
	}

}
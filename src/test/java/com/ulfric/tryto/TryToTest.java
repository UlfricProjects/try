package com.ulfric.tryto;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.common.truth.Truth;

import com.ulfric.veracity.Veracity;

import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Constructor;

class TryToTest {

	@Test
	void testToRunNoExceptions() {
		boolean[] ran = new boolean[1];
		CheckedRunnable run = () -> ran[0] = true;
		TryTo.run(run);
		Truth.assertThat(ran[0]).isTrue();
	}

	@Test
	void testToRunRethrowsCheckedException() {
		CheckedRunnable run = () -> { throw new Exception(); };
		Veracity.assertThat(() -> TryTo.run(run)).doesThrow(RuntimeException.class);
	}

	@Test
	void testToGetNoExceptions() {
		CheckedSupplier<Boolean> get = () -> true;
		Truth.assertThat(TryTo.get(get)).isTrue();
	}

	@Test
	void testToGetRethrowsCheckedException() {
		CheckedSupplier<?> get = () -> { throw new Exception(); };
		Veracity.assertThat(() -> TryTo.get(get)).doesThrow(RuntimeException.class);
	}

	@Test
	void testToIoRunIoNoExceptions() {
		boolean[] ran = new boolean[1];
		IoCheckedRunnable run = () -> ran[0] = true;
		TryTo.runIo(run);
		Truth.assertThat(ran[0]).isTrue();
	}

	@Test
	void testToRunIoRethrowsCheckedIoException() {
		IoCheckedRunnable run = () -> { throw new IOException(); };
		Veracity.assertThat(() -> TryTo.runIo(run)).doesThrow(UncheckedIOException.class);
	}

	@Test
	void testToGetIoRethrowsCheckedIoException() {
		IoCheckedSupplier<?> run = () -> { throw new IOException(); };
		Veracity.assertThat(() -> TryTo.getIo(run)).doesThrow(UncheckedIOException.class);
	}

	@Test
	void testToGetIoNoExceptions() {
		IoCheckedSupplier<Integer> run = () -> 5;
		Veracity.assertThat(() -> TryTo.getIo(run)).runsWithoutExceptions();
	}

	@Test
	void testToApplyAutocloseNoExceptions() throws Exception {
		IoCheckedFunction<Closeable, ?> run = ignore -> null;
		Closeable close = Mockito.mock(Closeable.class);
		Veracity.assertThat(() -> TryTo.applyAutoclose(close, run)).runsWithoutExceptions();
		Mockito.verify(close, Mockito.atLeastOnce()).close();
	}

	@Test
	void testToApplyAutocloseFunctionThrowsIoException() throws Exception {
		IoCheckedFunction<Closeable, ?> run = ignore -> { throw new IOException(); };
		Closeable close = Mockito.mock(Closeable.class);
		Veracity.assertThat(() -> TryTo.applyAutoclose(close, run)).doesThrow(UncheckedIOException.class);
		Mockito.verify(close, Mockito.atLeastOnce()).close();
	}

	@Test
	void testToApplyAutocloseCloseThrowsIoException() throws Exception {
		IoCheckedFunction<Closeable, ?> run = ignore -> null;
		Closeable close = Mockito.mock(Closeable.class);
		Mockito.doThrow(IOException.class).when(close).close();
		Veracity.assertThat(() -> TryTo.applyAutoclose(close, run)).doesThrow(UncheckedIOException.class);
		Mockito.verify(close, Mockito.atLeastOnce()).close();
	}

	@Test
	void testToApplyAutocloseCloseAndFunctionThrowIoException() throws Exception {
		IoCheckedFunction<Closeable, ?> run = ignore -> { throw new IOException(); };
		Closeable close = Mockito.mock(Closeable.class);
		Mockito.doThrow(IllegalStateException.class).when(close).close();
		Veracity.assertThat(() -> TryTo.applyAutoclose(close, run)).doesThrow(UncheckedIOException.class);
		Mockito.verify(close, Mockito.atLeastOnce()).close();
	}

	@Test
	void testInstantiate() throws Exception {
		Constructor<?> constructor = TryTo.class.getDeclaredConstructors()[0];
		constructor.setAccessible(true);
		constructor.newInstance();
	}

}
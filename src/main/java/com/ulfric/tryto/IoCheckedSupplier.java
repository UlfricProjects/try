package com.ulfric.tryto;

import java.io.IOException;

@FunctionalInterface
public interface IoCheckedSupplier<T> {

	T get() throws IOException;

}
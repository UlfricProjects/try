package com.ulfric.tryto;

import java.io.IOException;

@FunctionalInterface
public interface IoCheckedFunction<T, R> {

	R apply(T value) throws IOException;

}
package com.ulfric.tryto;

import java.io.IOException;

@FunctionalInterface
public interface IoCheckedConsumer<T> {

	void accept(T value) throws IOException;

}
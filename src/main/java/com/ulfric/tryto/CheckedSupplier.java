package com.ulfric.tryto;

@FunctionalInterface
public interface CheckedSupplier<T> {

	T get() throws Exception;

}
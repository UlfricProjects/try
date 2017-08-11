package com.ulfric.tryto;

@FunctionalInterface
public interface CheckedFunction<T, R> {

	R apply(T value) throws Exception;

}
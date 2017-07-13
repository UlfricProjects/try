package com.ulfric.tryto;

@FunctionalInterface
public interface CheckedRunnable {

	void run() throws Exception;

}
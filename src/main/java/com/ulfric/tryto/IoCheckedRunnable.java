package com.ulfric.tryto;

import java.io.IOException;

@FunctionalInterface
public interface IoCheckedRunnable {

	void run() throws IOException;

}
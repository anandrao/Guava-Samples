package com.guava;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.anand.sandbox.junit.runner.ConcurrentJunitRunner;
import com.anand.sandbox.junit.runner.Concurrent;


@RunWith(ConcurrentJunitRunner.class)
@Concurrent(threads = 6)
public final class ConcurrencyTest {

	@Test
	public void test0() throws Throwable {
		printAndWait();
	}

	@Test
	public void test1() throws Throwable {
		printAndWait();
	}

	@Test
	public void test2() throws Throwable {
		printAndWait();
	}

	@Test
	public void test3() throws Throwable {
		printAndWait();
	}

	@Test
	public void test4() throws Throwable {
		printAndWait();
	}

	@Test
	public void test5() throws Throwable {
		printAndWait();
	}

	@Test
	public void test6() throws Throwable {
		printAndWait();
	}

	@Test
	public void test7() throws Throwable {
		printAndWait();
	}

	@Test
	public void test8() throws Throwable {
		printAndWait();
	}

	@Test
	public void test9() throws Throwable {
		printAndWait();
	}

	void printAndWait() throws Throwable {
		System.out.println(String.format("[%s] %s %s %s", Thread
				.currentThread().getName(), getClass().getName(),
				new Throwable().getStackTrace()[1].getMethodName(), 5000));
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {

		}
	}
}

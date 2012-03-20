package com.anand.sandbox.junit.runner;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerScheduler;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * Added to the {@code ConcurrentJunitRunner} provided. Still WIP.
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 * @author Anand Rao
 */
public class ConcurrentJunitRunner extends BlockJUnit4ClassRunner {
	public ConcurrentJunitRunner(final Class<?> klass)
			throws InitializationError {
		super(klass);
		setScheduler(new RunnerScheduler() {
			ListeningExecutorService executorService = MoreExecutors
					.listeningDecorator(Executors.newFixedThreadPool(
							klass.isAnnotationPresent(Concurrent.class) ? klass
									.getAnnotation(Concurrent.class).threads()
									: (int) (Runtime.getRuntime()
											.availableProcessors() * 1.5),
							new NamedThreadFactory(klass.getSimpleName())));

			Queue<ListenableFuture<Void>> tasks = new LinkedList<ListenableFuture<Void>>();

			@Override
			public void schedule(Runnable childStatement) {

				ListenableFuture<Void> listenableFuture = executorService
						.submit(childStatement, (Void) null);
				Futures.addCallback(listenableFuture,
						new FutureCallback<Void>() {
							@Override
							public void onSuccess(Void arg0) {
								System.out.println("Yay I am finished!!!");

							}

							@Override
							public void onFailure(Throwable arg0) {
								System.out.println(String.format(
										"[%s] %s %s %s", Thread.currentThread()
												.getName(), getClass()
												.getName(), arg0.getStackTrace()[0], "Fail!!!"));
							}

						});
				tasks.offer(listenableFuture);
			}

			@Override
			public void finished() {
				try {
					while (!tasks.isEmpty() && tasks.peek().isDone())
						tasks.remove();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					while (!tasks.isEmpty())
						tasks.poll().cancel(true);
					executorService.shutdownNow();
				}
			}
		});
	}

	static final class NamedThreadFactory implements ThreadFactory {
		static final AtomicInteger poolNumber = new AtomicInteger(1);
		final AtomicInteger threadNumber = new AtomicInteger(1);
		final ThreadGroup group;

		NamedThreadFactory(String poolName) {
			group = new ThreadGroup(poolName + "-"
					+ poolNumber.getAndIncrement());
		}

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(group, r, group.getName() + "-thread-"
					+ threadNumber.getAndIncrement(), 0);
		}
	}
}
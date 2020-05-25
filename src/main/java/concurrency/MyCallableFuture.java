package concurrency;

import java.util.concurrent.*;

public class MyCallableFuture {
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<String> future = service.submit(() -> {
            System.out.println("Sub thread is working...");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("Sub thread is done");
            return "The result from sub thread.";
        });

        System.out.println("Main thread: " + Thread.currentThread().getName());

        String result = future.get(3, TimeUnit.SECONDS);
        System.out.println(result);


        service.shutdown();
    }
}

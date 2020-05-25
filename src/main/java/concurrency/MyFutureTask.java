package concurrency;

import java.util.concurrent.*;

public class MyFutureTask {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Callable<String> callable = () -> {
            System.out.println("Sub thread is working...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw e;
            }
            return "This result string from sub thread.";
        };
        FutureTask<String> futureTask = new FutureTask<String>(callable) {
            @Override
            protected void done() {
                try {
                    if (isCancelled()) {
                        System.out.println("Task is cancelled/interrupted, work may not be correctly done.");
                    } else {
                        System.out.println("Normally completed");
                        System.out.println(get());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void setException(Throwable t) {
                System.out.println("Cancel returns " + cancel(false));
                super.setException(t);
            }
        };

        service.execute(futureTask);

        System.out.println("Main thread is working...");
        TimeUnit.SECONDS.sleep(1);
//        futureTask.cancel(true);
        service.shutdownNow();
//        service.shutdown();
    }
}

package gatewayServer.ThreadPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.*;
import java.lang.Runnable;

public class ThreadPool<V> implements Executor {
    private Collection<WorkerThread> threadList = new ArrayList<>();
    private WaitablePQueue<Task> taskPQ;
    private boolean isShutdown;
    private boolean isPaused;
    private int numberOfThreads = 0;
    private final Object lock;
    private final Object awaitLock = new Object();
    public Semaphore sem;

    public ThreadPool(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        this.taskPQ = new WaitablePQueue<>();
        this.isShutdown = false;
        this.isPaused = false;
        this.lock = new Object();
        this.sem = new Semaphore(0);

        for (int i = 0; i < numberOfThreads; ++i) {
            WorkerThread workerThread = new WorkerThread();
            threadList.add(workerThread);
        }

        for (WorkerThread workerThread : threadList) {
            workerThread.start();
        }
    }
    @Override
    public void execute(Runnable task) {
        try {
            this.submit(task, Priority.MEDIUM);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //this submits convert Runnable to Callable
    public <V> Future<V> submit(Runnable runnable, Priority priority) throws ExecutionException, InterruptedException {
        return (Future<V>) submit(Executors.callable(runnable), priority);
    }

    //this submits convert Runnable to Callable
    public <V> Future<V> submit(Runnable runnable, Priority priority, V value) {
        Callable<V> callable = () -> {
            runnable.run();
            return value;
        };
        return submit(callable, priority);
    }

    public <V> Future<V> submit(Callable<V> c) {
        return submit(c, Priority.MEDIUM);
    }

    public synchronized <V> Future<V> submit(Callable<V> callable, Priority priority) {
        Task<V> task = new Task<>(callable, priority.getValue());
        if (this.isShutdown) {
            throw new RejectedExecutionException("ThreadPool has been shutdown");
        }
        this.taskPQ.enqueue(task);

        return task.getFuture();
    }

    public void setNumOfThreads(int numThreads) {
        int remainThreads = numberOfThreads - numThreads;

        if (remainThreads > 0) {
            Callable<V> callable = suicide();
            Task<V> suicide = new Task<>(callable, Priority.HIGH.getValue());
            for (int i = 0; i < remainThreads; ++i) {
                taskPQ.enqueue(suicide);
            }
        } else {
            for (int i = 0; i < (-remainThreads); ++i) {
                WorkerThread workerThread = new WorkerThread();
                threadList.add(workerThread);
                workerThread.start();
            }
        }
        this.numberOfThreads = numThreads;
    }

    public Callable<V> suicide() {
        Callable<V> callable = () -> {
            Thread currThread = Thread.currentThread();
            ((WorkerThread) currThread).setStatus(false);
            return null;
        };
        return callable;
    }

    public void pause() throws InterruptedException {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
        sem.release(numberOfThreads);
    }

    public void shutdown () {
        isShutdown = true;
        if (isPaused) {
            resume();
        }
        Task<V> shutDowner = new Task<>(suicide(), Priority.HIGH.getValue());
        for (int i = 0; i < numberOfThreads; ++i) {
            taskPQ.enqueue(shutDowner);
        }
    }

    public boolean isShutdown () {
            return isShutdown;
        }
    public void awaitTermination () throws InterruptedException {
        for (WorkerThread workerThread : threadList) {
            workerThread.join(8000);
        }
        if (!isShutdown){
            System.out.println("The shutdown need to be call before");
        }
        else {

        }
    }

    public int getSize(){
            return threadList.size();
        }

        public class Task<V> implements Comparable<Task<V>> {


            private Callable<V> callable = null;
            private int priority = 0;
            private final Future<V> future;

            private Task(Callable<V> callable, int priority) {
                this.callable = callable;
                this.priority = priority;
                this.future = new Taskfuture(this);
            }

            @Override
            public int compareTo(Task<V> task) {
                return this.priority - task.priority;
            }

            private Future<V> getFuture() {
                return future;
            }

            private Callable<V> getCallable() {
                return callable;
            }

            public int getPriority(){
                return priority;
            }

            private class Taskfuture implements Future<V> {
                V retFuture = null;
                boolean isTaskCancelled = false;
                boolean isDone = false;
                Task<V> futTask;
                final Object lock;

                private Taskfuture(Task<V> newTask) {
                    this.futTask = newTask;
                   lock = new Object();
                }

                @Override
                public boolean cancel(boolean b) {
                    if (isTaskCancelled || isDone) {
                        return true;
                    }

                    if (taskPQ.remove(futTask)) {
                        System.out.println("Wait for cancel");
                        isTaskCancelled = true;
                        isDone = true;
                        return true;
                    }

                    return false;
                }
                @Override
                public boolean isCancelled() {
                    return isTaskCancelled;
                }
                @Override
                public boolean isDone() {
                    return isDone;
                }
                @Override
                public V get() throws InterruptedException, ExecutionException {
                    if (isDone || isTaskCancelled) {
                        return retFuture;
                    }
                    sem.acquire();
                    return retFuture;
                }

                @Override
                public V get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
                    if (!sem.tryAcquire(l, timeUnit)) {
                        throw new TimeoutException();
                    }

                    return retFuture;
                }

                public void setFuture(V res) {
                    this.retFuture = res;
                }

                public void done() {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                    sem.release();
                    isDone = true;
                }
            }
        }

        /*-----------------------------------------------------------------------------*/

        private class WorkerThread extends Thread {
            private Thread thread = null;
            private boolean isRunning = true;
            Callable<V> callable = null;
            Task<V>.Taskfuture future = null;
            V returnValue = null;

            public void setStatus(boolean newStatus) {
                synchronized (ThreadPool.this) {
                    isRunning = newStatus;
                    if (isRunning) {
                        ThreadPool.this.notifyAll();
                    }
                }
            }

            public void run() {
                this.thread = Thread.currentThread();
                while (isRunning) {
                    try {
                        if (!isPaused){
                            Task<V> task = taskPQ.dequeue();
                            callable = task.getCallable();
                            future = (Task<V>.Taskfuture) task.getFuture();
                            returnValue = callable.call();
                            future.setFuture(returnValue);
                            future.done();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
   
}


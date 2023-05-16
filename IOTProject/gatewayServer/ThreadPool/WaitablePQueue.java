package gatewayServer.ThreadPool;

import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

public class WaitablePQueue<T> {

    Semaphore sem = new Semaphore(0);
    private final Lock lock = new ReentrantLock();
    private final PriorityQueue<T> queue;
    public WaitablePQueue(Comparator<? super T> comparator) {
        this.queue = new PriorityQueue<>(comparator);
    }
    public WaitablePQueue() {
        this.queue = new PriorityQueue<>();
    }

    public void enqueue(T element){
        lock.lock();
        queue.add(element);
        lock.unlock();
        sem.release();
    }

    public T dequeue() throws InterruptedException {
        try{
            sem.acquire();
            lock.lock();
        }
        catch (InterruptedException e){
            throw new InterruptedException();
        }
        T ret = queue.poll();
        lock.unlock();

        return ret;
    }
    public T dequeue(int timeout) throws TimeoutException{
        try {
            long startTime = System.currentTimeMillis();
            if(sem.tryAcquire( timeout, TimeUnit.MILLISECONDS)){
                long remainTime = timeout - (System.currentTimeMillis() - startTime);
                if(!lock.tryLock(remainTime, TimeUnit.MILLISECONDS)) {
                    throw new TimeoutException("Time is Out");
                }
            }
            else {
                throw new TimeoutException("Time is Out");
            }

        }catch (InterruptedException e) {
            throw new RuntimeException();
        }
        T ret = queue.poll();
        lock.unlock();

        return ret;
    }
    public boolean remove(T element) {
        boolean isRemoved = false;
        if(sem.tryAcquire()) {
            lock.lock();
            isRemoved = queue.remove(element);
            if (!isRemoved) {
                sem.release();
            }
            lock.unlock();
        }

        return isRemoved;
    }

    public int size(){
        return queue.size();
    }

}

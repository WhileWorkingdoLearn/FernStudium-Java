package pp.counter;

public class BoundedCounter
{
    private int max;

    private int min;

    private int count;

    public BoundedCounter(int min, int max)
    {
        if (min >= max)
        {
            throw new IllegalArgumentException();
        }
        this.min = min;
        this.max = max;
        this.count = min;
    }

    public synchronized int getMax()
    {
        return this.max;
    }

    public synchronized int getMin()
    {
        return this.min;
    }

    public synchronized void up()
    {
        while (!(get() < getMax()))
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
            }
        }
        this.count++;
        // System.out.println(Thread.currentThread() + "COUNT:" + count);
        notifyAll();
    }

    public synchronized void down()
    {
        while (!(get() > getMin()))
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
            }
        }
        this.count--;
        /// System.out.println(Thread.currentThread() + "COUNT:" + count);
        notifyAll();
    }

    public synchronized int get()
    {
        return count;
    }
}
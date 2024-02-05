package pp.producerconsumer;

public class Buffer
{
    private int head;

    private int tail;

    private int numberOfElements;

    private int[] data;

    public Buffer(int length)
    {
        if (length < 1)
        {
            throw new IllegalArgumentException();
        }
        data = new int[length];
        head = 0;
        tail = 0;
        numberOfElements = 0;
    }

    public synchronized void put(int x)
    {
        while (numberOfElements == data.length)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        data[tail++] = x;
        tail = tail == data.length ? 0 : tail;
        numberOfElements++;
        notifyAll();
    }

    public synchronized int get()
    {
        while (numberOfElements == 0)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        int result = data[head++];
        head = head == data.length ? 0 : head;
        numberOfElements--;
        notifyAll();
        return result;
    }

}

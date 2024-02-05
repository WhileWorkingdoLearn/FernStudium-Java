package pp.producerconsumersem;

public class Buffer
{
    private int head;

    private int tail;

    private int numberOfElements;

    private int[] data;

    private Semaphore mutex;

    private Semaphore full;

    private Semaphore empty;

    public Buffer(int size)
    {
        // TODO Auto-generated constructor stub
        if (size < 1)
        {
            throw new IllegalArgumentException();
        }

        this.mutex = new Semaphore(1);
        this.full = new Semaphore(0);
        this.empty = new Semaphore(size);
        data = new int[size];
        tail = 0;
    }

    public void put(int value)
    {
        empty.p();
        mutex.p();
        data[tail++] = value;
        tail = tail == data.length ? 0 : tail;
        mutex.v();
        full.v();
    }

    public int get()
    {
        full.p();
        mutex.p();
        int result = data[head++];
        head = head == data.length ? 0 : head;
        mutex.v();
        empty.v();
        return result;
    }
}

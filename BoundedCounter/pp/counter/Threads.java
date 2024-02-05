package pp.counter;

public class Threads
{

    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        final BoundedCounter ct = new BoundedCounter(0, 40);
        for (int i = 0; i < 3; i++)
        {
            Thread up = new Thread("Down Thread " + i)
            {
                @Override
                public void run()
                {
                    while (true)
                    {
                        ct.up();
                    }
                }
            };
            up.start();
        }
        for (int i = 0; i < 3; i++)
        {
            Thread down = new Thread("Down Thread " + i)
            {
                @Override
                public void run()
                {
                    while (true)
                    {
                        ct.down();
                    }
                }
            };
            down.start();
        }
    }

}

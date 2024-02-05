package pp.filelocking;

import java.util.ArrayList;
import java.util.List;

public class File
{
    private int fileLength;

    private List<Lock> lockedList;

    public File(int length)
    {
        if (length < 0)
        {
            throw new IllegalArgumentException();
        }
        this.fileLength = length;
        this.lockedList = new ArrayList<>();
        System.out.println("Created File " + fileLength);
    }

    private boolean isAlreadyLocked(int start, int end)
    {
        for (Lock locked : lockedList)
        {
            if (start < locked.getEnd() || end > locked.getStart())
            {
                return true;
            }
        }
        return false;
    }

    public synchronized void lock(int begin, int end)
    {
        if (begin < 0 || begin > end)
        {
            throw new IllegalArgumentException();
        }
        else if (end > fileLength - 1)
        {
            throw new IllegalArgumentException();
        }
        System.out.println("check if already locked " + isAlreadyLocked(begin, end));
        while (isAlreadyLocked(begin, end))
        {
            try
            {
                this.wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        Lock temp = new Lock(begin, end);
        System.out.println("added " + begin + " " + end + " to List");
        lockedList.add(temp);
    }

    private boolean removeFromList(int start, int end)
    {
        int index = 0;
        for (Lock lock : lockedList)
        {
            if (start == lock.getStart() && end == lock.getEnd())
            {
                this.lockedList.remove(0);
                return true;
            }
        }
        return false;
    }

    public synchronized void unlock(int begin, int end)
    {
        if (!this.removeFromList(begin, end))
        {
            throw new IllegalArgumentException("Not in List");
        }
        System.out.println("removed " + begin + " " + end);
        this.notifyAll();
    }

    public void printLocked()
    {
        for (Lock lock : lockedList)
        {
            System.out.println("[" + lock.getStart() + "," + lock.getEnd() + "]");
        }
    }

    public class Lock
    {
        private int lockStart;

        private int lockEnd;

        public Lock(int start, int end)
        {
            this.lockStart = start;
            this.lockEnd = end;
        }

        public int getStart()
        {
            return this.lockStart;
        }

        public int getEnd()
        {
            return this.lockEnd;
        }

        public int getLockLength()
        {
            return this.lockEnd - this.lockStart;
        }

    }
}

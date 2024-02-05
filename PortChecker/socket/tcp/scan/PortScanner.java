package socket.tcp.scan;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class PortScanner
{

    private ArrayList<Integer> availablePorts;

    public PortScanner()
    {
        availablePorts = new ArrayList<>();
    }

    public void start(String rechner, int scanRangeS, int scanRangeE)
    {
        if (scanRangeS > scanRangeE)
        {
            throw new IllegalArgumentException("start > end is not allowed");
        }
        if (scanRangeS < 0)
        {
            throw new IllegalArgumentException("start < 0 is not allowed");
        }
        Thread scanPorts = new Thread(() ->
        {
            System.out.println("Starte ScanPort " + rechner + " " + scanRangeS + " " + scanRangeE);

            for (int port = scanRangeS; port < scanRangeE; port++)
            {
                try
                {
                    System.out.println("" + port);
                    Socket clientSocket = new Socket();
                    clientSocket.connect(new InetSocketAddress(rechner, port), 1000);
                    availablePorts.add(clientSocket.getPort());
                    clientSocket.close();
                }
                catch (IOException e)
                {

                }
            }
            System.out.println("Left Scanner");
        });
        scanPorts.start();
    }

    public ArrayList<Integer> getPorts()
    {
        return availablePorts;
    }

    public static void main(String[] args)
    {
        if (args.length < 3)
        {
            throw new IllegalArgumentException();
        }

        int portTestStart = Integer.parseInt(args[1]);
        int portTestEnd = Integer.parseInt(args[2]);
        System.out.println("Starte Port Scanner");
        PortScanner portScanner = new PortScanner();
        portScanner.start(args[0], portTestStart, portTestEnd);
        System.out.println("Left Main");
    }
}

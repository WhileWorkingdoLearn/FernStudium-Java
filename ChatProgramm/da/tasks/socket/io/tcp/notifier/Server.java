package da.tasks.socket.io.tcp.notifier;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server
{
    private ServerSocket serverSocket;

    private ArrayList<ClientOnServer> clientsOnServer;

    private Scanner typeWriter;

    private Thread managementThread;

    public Server(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
        this.clientsOnServer = new ArrayList<>();
    }

    public void start() throws IOException
    {
        managementThread = new Thread(() -> checkForNewClients());
        managementThread.setName("ManagementThread");
        managementThread.start();
        typeWriter = new Scanner(System.in);
        while (!serverSocket.isClosed())
        {
            String serverMsg = typeWriter.nextLine();
            if (serverMsg.equalsIgnoreCase("Quit"))
            {
                this.close();
                break;
            }
        }
    }

    public void close() throws IOException
    {
        if (clientsOnServer.size() > 0)
        {
            for (ClientOnServer clientOnServer : clientsOnServer)
            {
                clientOnServer.close();
            }
            clientsOnServer.clear();
        }
        if (typeWriter != null)
        {
            typeWriter.close();
        }
        if (serverSocket != null)
        {
            serverSocket.close();
        }
        if (managementThread != null)
        {
            managementThread.interrupt();
        }
    }

    public ArrayList<ClientOnServer> getAllClientsOnServer()
    {
        return clientsOnServer;
    };

    private void checkForNewClients()
    {
        System.out.println("Server gestarted");
        while (!Thread.currentThread().isInterrupted())
        {

            try
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Neuen Client Aktzeptiert");
                ClientOnServer clientOnServer = new ClientOnServer(clientSocket, this);
                this.clientsOnServer.add(clientOnServer);

            }
            catch (IOException e)
            {
                break;
            }
        }
        System.out.println("Server Thread ended");
    }

    public String getRegisteredClients()
    {
        ArrayList<String> regNames = new ArrayList<>();

        for (ClientOnServer cos : clientsOnServer)
        {
            if (cos.getClientName() != null)
            {
                regNames.add(cos.getClientName());
            }
        }
        String regNamesString = String.join(",", regNames).replaceAll(",", ", ");
        regNames.clear();
        return "all#clients: " + regNamesString;
    }

    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            throw new IllegalArgumentException();
        }
        try
        {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
            System.out.println("ServerSocket created");
            Server server = new Server(serverSocket);
            server.start();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Done with main");
    }
}

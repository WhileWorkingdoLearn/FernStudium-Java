package da.tasks.socket.io.tcp.notifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientOnServer implements Runnable
{
    private Socket clientSocket;

    private Server server;

    private BufferedReader input;

    private BufferedWriter output;

    private String nameOfClient;

    private Thread clientListener;

    public ClientOnServer(Socket clientSocket, Server server)
    {
        this.clientSocket = clientSocket;
        this.server = server;
        try
        {
            this.start();
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }

    }

    private void start() throws IOException
    {
        this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
        this.output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
        clientListener = new Thread(this);
        clientListener.setName("ServerInputThread" + server.getAllClientsOnServer().size());
        clientListener.start();
    };

    private ClientOnServer getRegisteredClient(String name)
    {
        for (ClientOnServer clientOnServer : server.getAllClientsOnServer())
        {
            if (clientOnServer.toString().equalsIgnoreCase(name))
            {
                return clientOnServer;
            }
        }
        return null;
    }

    public void close()
    {
        server.getAllClientsOnServer().remove(this);

        try
        {
            if (nameOfClient != null)
            {
                sendToAllRegClients(server.getRegisteredClients());
            }
            if (clientSocket != null)
            {
                clientSocket.close();
            }
            if (input != null)
            {
                input.close();

            }
            if (output != null)
            {
                output.close();
            }
            if (clientListener != null)
            {
                clientListener.interrupt();
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Client Beendet");

    }

    private String receive() throws IOException
    {
        return this.input.readLine();
    }

    private synchronized boolean setClientName(String name)
    {
        if (name.length() < 1)
        {
            return false;
        }
        if (name.equalsIgnoreCase("all"))
        {
            return false;
        }
        if (name.equalsIgnoreCase("server"))
        {
            return false;
        }
        if (getRegisteredClient(name) != null)
        {
            return false;
        }

        this.nameOfClient = name;

        return true;

    }

    public String getClientName()
    {
        return this.nameOfClient;
    }

    private synchronized void send(String msg) throws IOException
    {
        this.output.write(msg);
        this.output.newLine();
        this.output.flush();
    }

    private synchronized void sendToAllClients(String msg) throws IOException
    {
        for (ClientOnServer clientOnServer : server.getAllClientsOnServer())
        {
            if (clientOnServer.equals(this))
            {
                continue;
            }
            clientOnServer.send(msg);
        }
    }

    private synchronized void sendToAllRegClients(String msg) throws IOException
    {
        for (ClientOnServer clientOnServer : server.getAllClientsOnServer())
        {
            if (clientOnServer.toString().equals(""))
            {
                continue;
            }
            clientOnServer.send(msg);
        }
    }

    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        if (this.nameOfClient == null)
        {
            return "null";
        }
        ;
        return this.nameOfClient;
    }

    @Override
    public void run()
    {
        System.out.println("Server slave gestartet");
        while (!Thread.currentThread().isInterrupted())
        {
            try
            {
                String msgToServer = receive();
                if (msgToServer == null)
                {

                    break;
                }
                if (msgToServer.contains("#"))
                {
                    String[] splitMessage = msgToServer.split("#");
                    String receiver = splitMessage[0].trim();
                    String msg = splitMessage[1] == null ? "" : splitMessage[1];

                    if (receiver.equalsIgnoreCase("server"))
                    {

                        if (this.setClientName(msg.trim()))
                        {
                            String registeredClients = server.getRegisteredClients();
                            this.send(registeredClients);
                            this.sendToAllClients(registeredClients);
                            continue;
                        }
                        else
                        {
                            this.send("???#name invalid");
                        }

                    }
                    else if (receiver.equalsIgnoreCase("all"))
                    {
                        this.sendToAllClients(msgToServer);
                        continue;
                    }
                    ClientOnServer cos = getRegisteredClient(receiver);

                    if (cos != null)
                    {
                        cos.send(msgToServer);
                    }

                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                break;
            }
        }
        this.close();
        System.out.println("Client von Server entfernt, Server thread beendet");
    }

}

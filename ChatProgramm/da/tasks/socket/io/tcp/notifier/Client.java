package da.tasks.socket.io.tcp.notifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    private Socket clientSocket;

    private BufferedReader input;

    private BufferedWriter output;

    private Scanner typeWriter;

    // private volatile boolean exit = false;

    private Thread clientThread;

    private Thread clientInputThread;

    public Client(Socket socket)
    {
        this.clientSocket = socket;
    }

    public void start() throws IOException
    {
        this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
        this.output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
        // clientThread = new Thread(this);
        // clientThread.start();
        clientInputThread = new Thread(() -> listener());
        clientInputThread.setName("ClientInputThread");
        clientInputThread.start();
    }

    private boolean send(String msg) throws IOException
    {
        if (!msg.contains("#"))
        {
            System.out.println("Wrong Syntax! -> use [adsress: all,server,user] # [message]");
            return false;
        }
        long msgContainsHash = msg.chars().filter(ch -> ch == '#').count();
        if (msgContainsHash > 1)
        {
            System.out.println("Wrong Syntax! -> use [adsress: all,server,user] # [message]");
            return false;
        }
        String[] msgParts = msg.split("#");
        if (msgParts.length > 2 && msgParts[1].trim().equals("#"))
        {
            return false;
        }

        this.output.write(msg);
        this.output.newLine();
        this.output.flush();

        return true;
    }

    private String receive() throws IOException
    {
        return this.input.readLine();
    }

    private void listener()
    {
        while (!Thread.currentThread().isInterrupted())
        {
            try
            {
                String msg = receive();
                System.out.println(msg);
            }
            catch (Exception e)
            {
                break;
                // TODO: handle exception
            }
        }
        System.out.println("Client input beended");
        stop();
    }

    public void stop()
    {
        try
        {
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
            if (typeWriter != null)
            {
                typeWriter.close();
            }
            if (clientThread != null)
            {
                clientThread.interrupt();
            }
            if (clientInputThread != null)
            {
                clientInputThread.interrupt();
            }
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args)
    {
        if (args.length < 2)
        {
            throw new IllegalArgumentException();
        }
        try
        {
            Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
            System.out.println("Starte Socket");
            Client client = new Client(socket);
            client.start();

            Scanner typeWriter = new Scanner(System.in);
            System.out.println("Enter User Name-> server # username");
            while (socket.isConnected())
            {
                try
                {
                    String typedMsg = typeWriter.nextLine();
                    System.out.println(typedMsg);
                    if (typedMsg.equalsIgnoreCase("Quit"))
                    {
                        // send(" " );
                        client.stop();
                        break;
                    }
                    boolean sended = client.send(typedMsg);
                    System.out.println(sended);
                }
                catch (Exception e)
                {
                    // TODO: handle exception
                }
            }
            System.out.println("Client Thread Beended");
            typeWriter.close();

        }
        catch (IOException e)
        {
            System.out.println("Es konnte keine Verbindung Hergesestellt werden");
            e.printStackTrace();
        }
        System.out.println("Done With Main");
    }

}

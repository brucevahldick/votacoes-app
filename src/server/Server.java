package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);

        Server server = new Server(serverSocket);

        InetAddress ip = InetAddress.getLocalHost();
        String hostname = ip.getHostName();

        System.out.println("Your current IP address : " + ip);
        System.out.println("Your current Hostname : " + hostname);

        Scanner s = new Scanner(System.in);
        System.out.println("Escreva qual o tema a ser votado");
        String tema = s.nextLine();


        server.startServer(tema);
    }

    public void startServer(String tema) {
        try {
            while (!serverSocket.isClosed()) {
                // New server socket for the recently connection created.
                Socket socket = serverSocket.accept();
                System.out.println("New client has connected");

                // Runnable object instance.
                ClientHandler clientHandler = new ClientHandler(socket, tema);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            closeServerSocket();
            e.printStackTrace();
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

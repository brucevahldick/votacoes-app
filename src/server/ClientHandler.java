package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    // Responsible to save the instance of all current connected clients
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUser;

    public ClientHandler(Socket socket, String tema) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUser = bufferedReader.readLine();
            clientHandlers.add(this);
            sendData("Client: " + this.clientUser + " connected");



//            this.bufferedWriter.write(tema);
//            this.bufferedWriter.newLine();
//            this.bufferedWriter.flush();
        } catch (IOException e) {
            closeConnection(this.socket, bufferedWriter, bufferedReader);
            e.printStackTrace();
        }
    }

    public String getClientUser() {
        return clientUser;
    }

    @Override
    public void run() {
        String dataFromClients;

        while (socket.isConnected()) {
            try {
                // data from client to save the vote
                dataFromClients = bufferedReader.readLine();
                if (dataFromClients != null) {
                    VotesDatasource.getInstance().processVote(dataFromClients);

                    System.out.println(dataFromClients);
                    // atualiza os demais clientes
                    sendData(VotesDatasource.getInstance().getVotesData());

                }

            } catch (IOException e) {
                closeConnection(socket, bufferedWriter, bufferedReader);
                break;
            }
        }
    }

    public void sendData(String data) {
        for (ClientHandler clientHandler: clientHandlers) {
            try {
                if (!clientHandler.clientUser.equals(clientUser)) {
                    clientHandler.bufferedWriter.write(data);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeConnection(socket, bufferedWriter, bufferedReader);
                e.printStackTrace();
                break;
            }
        }
    }

    public void closeConnection(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        removeClientHandler();
        try {
            if (bufferedReader != null && socket != null && bufferedWriter != null) {
                bufferedReader.close();
                bufferedWriter.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        sendData("client: " + clientUser + " disconnected");
    }

}

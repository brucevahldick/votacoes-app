package controller;

import model.Vote;
import observer.Observer;
import observer.Subject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Client implements Subject {

    Map<String, String> inputInfo;

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUser;

    private String messageServer;

    public String getMessageServer() {
        return messageServer;
    }

    public void conectar() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ip");
        String ip = scanner.nextLine();
        System.out.println("Enter your name");
        String clientName = scanner.nextLine();
        Socket socket = new Socket(ip, 80);
        Client voteThread = new Client(socket, clientName);
        voteThread.listenForData();
    }

    public Client(Socket socket, String clientUser) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUser = clientUser;
            bufferedWriter.write(clientUser);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        } catch (IOException e) {
//            closeConnection(this.socket, bufferedWriter, bufferedReader);
            e.printStackTrace();
        }
        observerList = new ArrayList<>();
    }

    public void sendData(Vote vote) {
        try {
            while (socket.isConnected() && !socket.isClosed()) {
                bufferedWriter.write(clientUser+";"+ vote.isParecer());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
//            closeConnection(socket, bufferedWriter, bufferedReader);
            e.printStackTrace();
        }
    }

    public void listenForData() {
        new Thread(() -> {
            while (socket.isConnected() && !socket.isClosed()) {
                try {
                    // FORMATO > nome:voto;nome:voto;nome:voto
                    messageServer = bufferedReader.readLine();
                    notifyObservers();
                    System.out.println(messageServer);
                } catch (IOException e) {
//                    closeConnection(socket, bufferedWriter, bufferedReader);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void closeConnection(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
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

    private List<Observer> observerList;

    @Override
    public void notifyObservers() {
        for (Observer o : observerList) {
            o.update();
        }
    }

    @Override
    public void addObserver(Observer o) {
        observerList.add(o);
    }
}

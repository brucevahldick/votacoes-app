package controller;

import model.Config;
import model.Usuario;
import model.Vote;
import observer.Observer;
import observer.Subject;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectionFacade implements Subject, Observer {
    private Client client;
    private Config config;
    private List<Observer> observers;
    private List<Vote> votos;

    public List<Vote> getVotos() {
        return votos;
    }

    public ConnectionFacade() {
        observers = new ArrayList<>();
    }
    public void connectWithConfig(String ip, String nome) throws IOException {
        try (Socket socket = new Socket(ip, 80)) {
            config = new Config(new Usuario(nome), ip);
            client = new Client(socket, nome);
            client.listenForData();
            client.addObserver(this);
            notifyObservers();
        }
    }

    public void votar(boolean voto)
    {
        Vote votoObjeto = new Vote();
        votoObjeto.setUsuario(config.getUsuario());
        votoObjeto.setParecer(voto);
        client.sendData(votoObjeto);
    }

    public void atualizarVotacao(List<Vote> votes)
    {
        votos = votes;
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void update() {
        System.out.println("teste ConnectionFacade");
        String message = client.getMessageServer();
        String[] userVotes = message.split(";");
        List<Vote> votos = new ArrayList<>();
        System.out.println(userVotes);
        for (String userVote : userVotes) {
            String[] voto = userVote.split(":");
            Vote vote = new Vote();
            vote.setUsuario(new Usuario(voto[0]));
            vote.setParecer(voto[1].equals("favor"));
            votos.add(vote);
        }
        atualizarVotacao(votos);
    }
}

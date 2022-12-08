package view;

import controller.ConnectionFacade;
import observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TelaInicial extends JFrame implements Observer {

    private ConnectionFacade createConnection;

    public TelaInicial() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        createConnection = new ConnectionFacade();
        createConnection.addObserver(this);
        createComponents();
    }

    private void createComponents()
    {
        JPanel jp_painelPrincipal = new JPanel(new BorderLayout());
        JPanel jp_especificacoesPanel = new JPanel();

        JLabel jl_nomeUsuario = new JLabel("Nome: ");
        JTextField jt_nomeUsuario = new JTextField(20);
        jp_especificacoesPanel.add(jl_nomeUsuario);
        jp_especificacoesPanel.add(jt_nomeUsuario);
        JLabel jl_enderecoIP = new JLabel("Endereço de IP: ");
        JTextField jt_enderecoIP = new JTextField(20);
        jp_especificacoesPanel.add(jl_enderecoIP);
        jp_especificacoesPanel.add(jt_enderecoIP);
        JPanel jp_painelBotoes = new JPanel();
        JButton jb_confirmar = new JButton("Confirmar");
        jp_painelBotoes.add(jb_confirmar);
        jp_painelPrincipal.add(jp_especificacoesPanel, BorderLayout.CENTER);
        jp_painelPrincipal.add(jp_painelBotoes, BorderLayout.SOUTH);
        add(jp_painelPrincipal);

        jb_confirmar.addActionListener(actionEvent -> {
            String ip = jt_enderecoIP.getText();
            String nome = jt_nomeUsuario.getText();
            jb_confirmar.setText("Carregando...");
            try {
                createConnection.connectWithConfig(ip, nome);
            } catch (IOException e) {
                jp_painelBotoes.add(new Label("Falha na conexao"));
                jb_confirmar.setText("Confirmar");
            }
        });
    }

    @Override
    public void update() {
        this.setVisible(false);
        TelaDeVotacao telaDeVotacao = new TelaDeVotacao(createConnection);
        telaDeVotacao.setVisible(true);
        createConnection.removeObserver(this);
    }
}

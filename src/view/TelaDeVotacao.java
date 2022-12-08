package view;

import controller.ConnectionFacade;
import model.Vote;
import observer.Observer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TelaDeVotacao extends JFrame implements Observer {
    private ConnectionFacade connectionFacade;

    private JTable jt_votos;

    class VotosModel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return connectionFacade.getVotos().size();
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public Object getValueAt(int i, int i1) {
            return null;
        }
    }

    class VotoRender extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            setText(((Vote) value).render());
            return this;
        }
    }
    public TelaDeVotacao(ConnectionFacade connectionFacade) throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        this.connectionFacade = connectionFacade;
        this.connectionFacade.addObserver(this);
        createComponents();
    }

    private void createComponents()
    {
        JPanel jp_botoesVoto = new JPanel();
        JButton jb_favor = new JButton("Favor");
        jb_favor.addActionListener(actionEvent -> {
            connectionFacade.votar(true);
        });
        JButton jb_contra = new JButton("Contra");
        jb_contra.addActionListener(actionEvent -> {
            connectionFacade.votar(false);
        });
        jp_botoesVoto.add(jb_favor);
        jp_botoesVoto.add(jb_contra);
        add(jp_botoesVoto);
        JPanel jp_votos = new JPanel();
        buildTable();
        jp_votos.add(jt_votos);
    }

    private void buildTable()
    {

        jt_votos = new JTable();
        jt_votos.setBackground(Color.black);
        jt_votos.setModel(new VotosModel());
        for (int i = 0; i < jt_votos.getColumnModel().getColumnCount(); i++) {
            jt_votos.getColumnModel().getColumn(i).setWidth(35);
            jt_votos.getColumnModel().getColumn(i).setMaxWidth(45);
        }
        jt_votos.setRowHeight(32);
        jt_votos.setShowGrid(false);
        jt_votos.setDefaultRenderer(Object.class, new VotoRender());
    }

    @Override
    public void update() {
        System.out.println("teste");
        buildTable();
    }
}

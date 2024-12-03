package views; //indica que o arquivo esta na pasta views

import models.Item; //importa o modelo de dados

import javax.swing.*; //biblioteca swing (interfaces tambem)
import javax.swing.table.DefaultTableModel;
import java.awt.*; //importa classes para layout e gerenciamento de janelas.
import java.util.List;

public class ItemTableView extends JFrame { //a classe vai ter um frame que mostra uma tabela
    private JTable table; //objeto que representa a tabela
    private DefaultTableModel tableModel; //gerencia as linhas e as colunas

    //construtor
    public ItemTableView() {
        super("Gerenciamento de Itens");
        initializeComponents();
    }

    private void initializeComponents() {
        //nome das colunas que vao ficar na tabela
        String[] columnNames = {"ID", "Marca", "Borracha", "Tipo", "Esponja", "Preco"};
        tableModel = new DefaultTableModel(columnNames, 0); //no comeco nao vao ter linhas
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table); //cria uma barra de rolagem

        scrollPane.setBorder( //define a borda ao redor da barra de rolagem
            BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        this.setLayout(new BorderLayout()); //o layout vai ficar disposto em regioes (NORTH, SOUTH, ETC.)
        this.add(scrollPane, BorderLayout.CENTER);

        this.setSize(600, 400); //tamanho da janela
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); //centraliza na tela
    }

    public void atualizarTabela(List<Item> itens) { //cria o metodo que atualiza os dados da tabela
        tableModel.setRowCount(0); // Limpa a tabela
        for (Item item : itens) {
            Object[] row = {
                item.getId(),
                item.getMarca(),
                item.getBorracha(),
                item.getTipo(),
                item.getEsponja(),
                item.getPreco()
            };
            tableModel.addRow(row);
        }
    }

    public int getSelectedItemId() {
        int selectedRow = table.getSelectedRow(); //obtem o indice da tabela
        if (selectedRow != -1) {
            return (int) tableModel.getValueAt(selectedRow, 0);
        }
        return -1;
    }
}

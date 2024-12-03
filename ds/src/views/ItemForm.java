package views; //indica que o arquivo esta na pasta views

import models.Item; //importa o modelo de dados

import javax.swing.*; //biblioteca swing (interfaces tambem)
import java.awt.*; //importa classes para layout e gerenciamento de janelas.

import java.awt.event.ActionEvent; //esses dois sao relacionados ao o que acontece quando se pressiona algum botao
import java.awt.event.ActionListener;

public class ItemForm extends JDialog {

    //criacao dos elementos graficos (JTextField, JComboBox, JButton e JRadioButton)
    private JTextField marcaField;
    private JTextField borrachaField;

    private JComboBox<String> tipoComboBox; 
    private JRadioButton radioT, radioF;
    private ButtonGroup grupo; //grupo que vai reunir as duas opcoes (T e F)

    private JTextField precoField;
    private JButton salvarButton;
    private JButton cancelarButton;

    private Item item;
    private boolean isEditMode; //indica quando esta no modo edicao

    public ItemForm(Frame parent, String title) { //construtor para um novo frame
        super(parent, title, true);
        this.isEditMode = false; //diz que nao esta no modo de edicao
        initializeComponents(); //coloca os componentes na interface
    }

    public ItemForm(Frame parent, String title, Item item) { //construtor para quando for atualizar o banco
        super(parent, title, true);
        this.item = item;
        this.isEditMode = true; //diz que esta no modo de edicao
        initializeComponents(); //coloca os componentes na interface
        preencherCampos(); //chama o metodo que preenche os campos com as informacoes para editar
    }

    private void initializeComponents() {
        //parte do JRadioButton (coloco eles em um grupo e depois em um panel com FlowLayout)
        radioT = new JRadioButton("Sim");
        radioF = new JRadioButton("Não");
        grupo = new ButtonGroup();
        grupo.add(radioT);
        grupo.add(radioF);

        //criando os JTextField e JComboBox
        marcaField = new JTextField(20);
        borrachaField = new JTextField(20);
        tipoComboBox = new JComboBox<>();
        tipoComboBox.addItem("Lisa");
        tipoComboBox.addItem("Pino Curto");
        tipoComboBox.addItem("Antispin");
        tipoComboBox.addItem("Pino Longo");
        precoField = new JTextField(20);
        salvarButton = new JButton("Salvar");
        cancelarButton = new JButton("Cancelar");

        //criando o panel que vai ficar os RadioButtons
        JPanel panelRadio = new JPanel(new FlowLayout());
        panelRadio.add(radioT);
        panelRadio.add(radioF);

        //esse é o panel principal incluindo o panel com os RadioButtons
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.add(new JLabel("Marca:"));
        panel.add(marcaField);
        panel.add(new JLabel("Borracha:"));
        panel.add(borrachaField);
        panel.add(new JLabel("Tipo:"));
        panel.add(tipoComboBox);
        panel.add(new JLabel("Esponja:"));
        panel.add(panelRadio);
        panel.add(new JLabel("Preco:"));
        panel.add(precoField);
        panel.add(salvarButton);
        panel.add(cancelarButton);

        // Adicionando uma margem de 10 pixels nas boras laterais e verticais
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        salvarButton.addActionListener(new ActionListener() { //quando clicar no botao vai acontecer uma acao
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) { 
                    if (isEditMode) { //verifica se vai atualizar ou adicionar
                        atualizarItem();
                    } else {
                        adicionarItem();
                    }
                    dispose(); //fecha a janela
                }
            }
        });

        cancelarButton.addActionListener(e -> dispose()); //'e' amarelo?

        //adiciona os panels na tela e organiza eles
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(getParent());
    }

    private void preencherCampos() { //cria o metodo que preenche os campos no modo de edicao
        if (item != null) {
            marcaField.setText(item.getMarca());
            borrachaField.setText(item.getBorracha());
            String.valueOf(tipoComboBox.getSelectedItem());
            radioT.setSelected(item.getEsponja());
            radioF.setSelected(!item.getEsponja());
            precoField.setText(item.getPreco());
        }
    }

    private boolean validarCampos() { //cria o metodo que verifica se os campos foram preenchidos
        if (marcaField.getText().trim().isEmpty() || //ve se os campos marca, borracha e preco foram preenchidos
                borrachaField.getText().trim().isEmpty() ||
                precoField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Marca, Borracha e preço são obrigatórios.", //se algum nao tiver sido preenchido mostra esse alerta
                "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void adicionarItem() { //cria o metodo que adiciona um novo item
        item = new Item(
            marcaField.getText().trim(),
            borrachaField.getText().trim(),
            String.valueOf(tipoComboBox.getSelectedItem()),
            radioT.isSelected(),
            precoField.getText().trim()
        );
    }

    private void atualizarItem() { //ria o metodo que atualiza o item selecionado
        if (item != null) {
            item.setMarca(marcaField.getText().trim());
            item.setBorracha(borrachaField.getText().trim());
            item.setTipo(String.valueOf(tipoComboBox.getSelectedItem()));
            item.setEsponja(radioT.isSelected());
            item.setPreco(precoField.getText().trim());
        }
    }

    public Item getItens() { //retorna o item
        return item;
    }
}

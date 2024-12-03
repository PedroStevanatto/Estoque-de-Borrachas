package controllers; //indica que o arquivo esta na pasta controllers

import models.Item; //importa o modelo de dados
import repository.ItemRepository; //permite acessar o repositorio
import views.ItemForm; //esse e o de baixo sao relacionados a interface grafica
import views.ItemTableView;

import javax.swing.*; //biblioteca swing (interfaces tambem)

import java.awt.event.ActionEvent; //esses dois sao relacionados ao o que acontece quando se pressiona algum botao
import java.awt.event.ActionListener;
import java.util.List; //biblioteca de listas

public class ItemController { //cria a classe ItemController que fica entre o view e o models e repository
    private ItemRepository repository;
    private ItemTableView tableView;

    public ItemController() { //construtor do controller, inicializando o repositorio e a tabela
        repository = new ItemRepository(); 
        tableView = new ItemTableView();
        inicializar();
    }


private void inicializar() {
    // Atualizar a tabela com os Itens existentes
    atualizarTabela();

    // Criar a barra de ferramentas (toolbar) om botões
    JToolBar toolBar = new JToolBar();
    JButton adicionarButton = new JButton("Adicionar");
    JButton editarButton = new JButton("Editar");
    JButton deletarButton = new JButton("Deletar");

    toolBar.add(adicionarButton);
    toolBar.add(editarButton);
    toolBar.add(deletarButton);

    tableView.add(toolBar, java.awt.BorderLayout.NORTH);

    // Ações dos botões
    adicionarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            adicionarItem();
        }
    });

    editarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            editarItem();
        }
    });

    deletarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            deletarItem();
        }
    });

    tableView.setVisible(true); //torna possivel a visualizacao da tabela
}

private void atualizarTabela() { //metodo para atualizar as informacoes, pegando os itens do repositorio e exibindo na tabela
        List<Item> itens = repository.obterTodosItens();
        tableView.atualizarTabela(itens);
    }

    private void adicionarItem() { //metodo para se preencher novos itens na tabela
        ItemForm form = new ItemForm(tableView, "Adicionar Item");
        form.setVisible(true);
        Item novoItem = form.getItens();
        if (novoItem != null) {
            repository.adicionarItem(novoItem);
            atualizarTabela(); //depois de executar o codigo acima atualiza as informacoes com o metodo atualizarTabela()
        }
    }

private void editarItem() { //metodo para editar os itens
    int selectedId = tableView.getSelectedItemId(); //verifica o item selecionado
    if (selectedId != -1) {
        Item item = repository.obterItemPorId(selectedId);
        if (item != null) { //verifica se o id existe
            ItemForm form = new ItemForm(tableView,
                "Editar Item", item); //preenche o formulario com os dados previos
            form.setVisible(true);
            Item itemAtualizado = form.getItens();
            if (itemAtualizado != null) {
                itemAtualizado = new Item(
                    selectedId,
                    itemAtualizado.getMarca(),
                    itemAtualizado.getBorracha(),
                    itemAtualizado.getTipo(),
                    itemAtualizado.getEsponja(),
                    itemAtualizado.getPreco()
                );
                repository.atualizarItem(itemAtualizado);
                atualizarTabela(); //salva as alteracoes por meio do metodo atualizarTabela()
            }
        } else {
            JOptionPane.showMessageDialog(tableView,
                "Item não encontrado.",
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(tableView,
        "Selecione um item para editar.",
        "Aviso", JOptionPane.WARNING_MESSAGE);
    }
}

private void deletarItem() { //esse metodo funciona de forma semelhante ao anterior no quesito de verificar se o id foi selecionado e se ele existe
    int selectedId = tableView.getSelectedItemId();
    if (selectedId != -1) {
        int confirm = JOptionPane.showConfirmDialog(
            tableView,
            "Tem certeza que deseja deletar este item",
            "Confirmar Deleção",
            JOptionPane.YES_NO_OPTION); //para confirmar a decisao de deletar, surge um JOptionPane com opcao sim ou nao
        
        if (confirm == JOptionPane.YES_OPTION) {
            repository.deletarItem(selectedId);
            atualizarTabela(); //deleta e atualiza a tabela por meio do metodo atualizarTabela()
        }
    } else {
        JOptionPane.showMessageDialog(
            tableView,
            "Selecione um item para deletar",
            "Aviso",
            JOptionPane.WARNING_MESSAGE);
    }
}

    public void iniciar() {
        // Ações já são inicializadas on construtor
    }

}
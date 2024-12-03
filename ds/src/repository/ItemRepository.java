package repository; //indica que o arquivo esta na pasta repository

import models.Item; //importa o modelo de dados
import config.DbConnection; //permite usar a conexao com o banco de dados feita no DbConnection

import java.sql.*; //torna possivel manipular o banco
//os de baixo sao relacionados com as listas que ficam na tabela e vao armazenar os objetos do Item
import java.util.ArrayList;
import java.util.List;

public class ItemRepository {
    
    // Criar um novo Itens
    public void adicionarItem(Item item) {
        String sql = "INSERT INTO itens (marca, borracha, tipo, esponja, preco) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection(); //pega a conexao com o bd
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            //os devidos gets para cada placeholder ('?')
            stmt.setString( 1, item.getMarca());
            stmt.setString( 2, item.getBorracha());
            stmt.setString( 3, item.getTipo());
            stmt.setBoolean( 4, item.getEsponja());
            stmt.setString( 5, item.getPreco());



            int linhasAfetadas = stmt.executeUpdate(); //adiciona o item ao bd
            if (linhasAfetadas > 0) { //se afetou alguma linha significa que funcionou
                System.out.println("Item adicionado com sucesso!");
            }

        } catch (SQLException e) { //se der erro cai no 'catch'
            System.out.println("Erro ao adicionar item.");
            e.printStackTrace();;
        }
    }

 // Obter todos os itens
    public List<Item> obterTodosItens() {
        List<Item> itens = new ArrayList<>();
        String sql = "SELECT * FROM itens"; //vai pegar tudo que estiver na tabela itens

        try (Connection conn = DbConnection.getConnection(); //conecta o bd
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) { //para cada linha que retornar vai crair um objeto item e adicionar a lista
                Item item = new Item( 
                    rs.getInt("id"),
                    rs.getString("marca"),
                    rs.getString("borracha"),
                    rs.getString("tipo"),
                    rs.getBoolean("Esponja"),
                    rs.getString("preco")
                );
                itens.add(item);
            } 
        } catch (SQLException e) {  //se der erro cai no 'catch'
            System.out.println("Erro ao obter itens.");
            e.printStackTrace();;
        }

        return itens; //retorna a lista com o que foi encontrado
    }

 // Obter item por ID
    public Item obterItemPorId(int id) { //vai buscar um item especifico, por meio do ID
        String sql = "SELECT * FROM itens WHERE id = ?";
        Item item = null;

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery(); //configura o ID e o resultado vai ser obtido por um ResultSet

            if (rs.next()) { //se o item for encontrado vai criar essa instancia com os dados retornados
                item = new Item(
                    rs.getInt("id"),
                    rs.getString("marca"),
                    rs.getString("borracha"),
                    rs.getString("tipo"),
                    rs.getBoolean("Esponja"),
                    rs.getString("preco")
                );
            }
        } catch (SQLException e) { //se der erro cai no 'catch'
            System.out.println("Erro ao obter item por ID.");
            e.printStackTrace();
        }

        return item; //retorna a lista com o que foi encontrado
    }

 // Atualizar um item
    public void atualizarItem(Item item) {
        String sql =
          "UPDATE itens SET marca = ?, borracha = ?, tipo = ?, esponja = ?, preco = ? WHERE id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            
                //os devidos sets para cada placeholder ('?')
                stmt.setString( 1, item.getMarca());
                stmt.setString( 2, item.getBorracha());
                stmt.setString( 3, item.getTipo());
                stmt.setBoolean( 4, item.getEsponja());
                stmt.setString( 5, item.getPreco());
                stmt.setInt(6, item.getId());

            int linhasAfetadas = stmt.executeUpdate(); 
            if (linhasAfetadas > 0) { //se alterar alguma linha deu certo
                System.out.println("Item atualizado com sucesso!");
            } else { //se nao alterar deu errado
                System.out.println("Item não encontrado.");
            }
        } catch (SQLException e) { //se der erro cai no 'catch'
            System.out.println("Erro ao atualizar item.");
            e.printStackTrace();
        }
    }

 // Deletar um item
    public void deletarItem(int id) {
        String sql = "DELETE FROM itens WHERE id = ?"; //vai remover o item pelo id

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            //vai substituir o placholder ('?') pelo id
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) { //se alterar alguma linha deu certo
                System.out.println("Item deletado com sucesso!");
            } else {  //se nao alterar deu errado
                System.out.println("Item não encontrado.");
            }
        } catch (SQLException e) { //se der erro cai no 'catch'
            System.out.println("Erro ao deletar item.");
            e.printStackTrace();
        }
    }
}

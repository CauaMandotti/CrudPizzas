package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PizzaDAO {

    // Inicializa o Logger para esta classe
    private static final Logger logger = Logger.getLogger(PizzaDAO.class.getName());

    public void cadastrarPizza(PizzaDTO pizza) {
        String sql = "INSERT INTO pizzas (sabor, descricao, valor, disponivel) VALUES (?, ?, ?, ?)";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, pizza.getSabor());
            ps.setString(2, pizza.getDescricao());
            ps.setDouble(3, pizza.getValor());
            ps.setBoolean(4, pizza.isDisponivel());
            ps.executeUpdate();

            logger.info("Pizza gravada com sucesso!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao gravar pizza", e);
        }
    }

    public List<PizzaDTO> selecionarPizzas() {
        String sql = "SELECT * FROM pizzas ORDER BY id";
        List<PizzaDTO> lista = new ArrayList<>();

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PizzaDTO pizza = new PizzaDTO();
                pizza.setId(rs.getInt("id"));
                pizza.setSabor(rs.getString("sabor"));
                pizza.setDescricao(rs.getString("descricao"));
                pizza.setValor(rs.getDouble("valor"));
                pizza.setDisponivel(rs.getBoolean("disponivel"));

                lista.add(pizza);
            }

            logger.info("Pizzas listadas do banco com sucesso!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar pizzas", e);
        }

        return lista; // Retorna a lista para a Tabela do JavaFX
    }

    public void alterarPizza(PizzaDTO pizza) {
        String sql = "UPDATE pizzas SET sabor = ?, descricao = ?, valor = ?, disponivel = ? WHERE id = ?";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, pizza.getSabor());
            ps.setString(2, pizza.getDescricao());
            ps.setDouble(3, pizza.getValor());
            ps.setBoolean(4, pizza.isDisponivel());
            ps.setInt(5, pizza.getId());
            ps.executeUpdate();

            logger.info("Pizza alterada com sucesso!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao alterar pizza", e);
        }
    }

    public void excluirPizza(int id) {
        String sql = "DELETE FROM pizzas WHERE id = ?";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            logger.info("Pizza excluida com sucesso!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao excluir pizza", e);
        }
    }

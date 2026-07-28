package com.template.model;

import com.template.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PizzaDAO {

    public void cadastrarPizza(PizzaDTO pizza) {
        String sql = "INSERT INTO pizzas (sabor, descricao, valor, disponivel) VALUES (?, ?, ?, ?)";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, pizza.getSabor());
            ps.setString(2, pizza.getDescricao());
            ps.setDouble(3, pizza.getValor());
            ps.setBoolean(4, pizza.isDisponivel());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirPizza(int id) {
        String sql = "DELETE FROM pizzas WHERE id = ?";

        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
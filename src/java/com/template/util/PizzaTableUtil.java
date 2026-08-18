package com.template.util;

import com.template.model.PizzaDAO;
import com.template.model.PizzaDTO;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class PizzaTableUtil {

    public static void configurarColunas(TableColumn<PizzaDTO, Integer> colId,
                                         TableColumn<PizzaDTO, String> colSabor,
                                         TableColumn<PizzaDTO, String> colDescricao,
                                         TableColumn<PizzaDTO, Double> colValor,
                                         TableColumn<PizzaDTO, Boolean> colDisponivel) {
        if (colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSabor.setCellValueFactory(new PropertyValueFactory<>("sabor"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colDisponivel.setCellValueFactory(new PropertyValueFactory<>("disponivel"));
    }

    public static void carregarPizzas(TableView<PizzaDTO> tblPizza) {
        PizzaDAO objpizzadao = new PizzaDAO();
        List<PizzaDTO> listaPizzas = objpizzadao.selecionarPizzas();

        tblPizza.getItems().clear();
        if (listaPizzas != null) {
            tblPizza.getItems().addAll(listaPizzas);
        }
    }
}
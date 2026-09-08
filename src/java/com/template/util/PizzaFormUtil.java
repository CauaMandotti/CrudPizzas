package com.template.util;

import com.template.dao.IPizzaDAO;
import com.template.dto.PizzaDTO;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PizzaFormUtil {

    public static void preencherFormulario(PizzaDTO pizza, TextField txtId, TextField txtSabor,
                                           TextField txtDescricao, TextField txtValor, CheckBox chkDisponivel) {
        if (pizza != null) {
            if (txtId != null) txtId.setText(String.valueOf(pizza.getId()));
            txtSabor.setText(pizza.getSabor());
            txtDescricao.setText(pizza.getDescricao() != null ? pizza.getDescricao() : "");
            txtValor.setText(String.valueOf(pizza.getValor()));
            chkDisponivel.setSelected(pizza.isDisponivel());
        }
    }

    public static void limparCampos(TextField txtId, TextField txtSabor,
                                    TextField txtDescricao, TextField txtValor, CheckBox chkDisponivel) {
        if (txtId != null) txtId.clear();
        txtSabor.clear();
        txtDescricao.clear();
        txtValor.clear();
        chkDisponivel.setSelected(false);
        txtSabor.requestFocus();
    }

    public static void ajustarBotoes(Button btnAlterar, Button btnExcluir, boolean ativo) {
        btnAlterar.setDisable(!ativo);
        btnExcluir.setDisable(!ativo);
    }

    public static PizzaDTO criarDTOComDadosDoFormulario(Integer id, TextField txtSabor, TextField txtDescricao,
                                                        TextField txttValor, CheckBox chkDisponivel) {
        PizzaDTO dto = new PizzaDTO();
        if (id != null) {
            dto.setId(id);
        }
        dto.setSabor(txtSabor.getText());
        dto.setDescricao(txtDescricao.getText());
        dto.setValor(Double.parseDouble(txttValor.getText().replace(",", ".")));
        dto.setDisponivel(chkDisponivel.isSelected());
        return dto;
    }

    public static void atualizarTela(TextField txtId, TextField txtSabor, TextField txtDescricao,
                                     TextField txttValor, CheckBox chkDisponivel,
                                     TableView<PizzaDTO> tblPizza, Button btnAlterar,
                                     Button btnExcluir, IPizzaDAO pizzaDAO) {
        limparCampos(txtId, txtSabor, txtDescricao, txttValor, chkDisponivel);
        tblPizza.getSelectionModel().clearSelection();
        ajustarBotoes(btnAlterar, btnExcluir, false);
        PizzaTableUtil.carregarPizzas(tblPizza, pizzaDAO);
    }
}
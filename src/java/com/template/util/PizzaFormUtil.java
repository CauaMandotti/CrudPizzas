package com.template.util;

import com.template.model.PizzaDTO;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
}
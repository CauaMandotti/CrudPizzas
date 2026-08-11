package com.template.validator;


import com.template.util.ExibirMensagem;

public class PizzaValidator {

    public boolean validarPizza(String sabor, String descricao, String valorTexto) {
        if (!validarSabor(sabor)) {
            return false;
        }

        if (!validarDescricao(descricao)) {
            return false;
        }

        if (!validarValor(valorTexto)) {
            return false;
        }

        return true;
    }

    private boolean validarSabor(String sabor) {
        if (sabor == null || sabor.trim().isEmpty()) {
            ExibirMensagem.showError("O campo Sabor é obrigatório!");
            return false;
        }

        if (sabor.trim().length() < 3) {
            ExibirMensagem.showError("O Sabor deve ter pelo menos 3 caracteres!");
            return false;
        }

        return true;
    }

    private boolean validarDescricao(String descricao) {
        if (descricao != null && descricao.length() > 100) {
            ExibirMensagem.showError("A Descrição não pode ter mais de 100 caracteres!");
            return false;
        }
        return true;
    }

    private boolean validarValor(String valorTexto) {
        if (valorTexto == null || valorTexto.trim().isEmpty()) {
            ExibirMensagem.showError("O campo Valor é obrigatório!");
            return false;
        }

        try {
            double valor = Double.parseDouble(valorTexto.replace(",", "."));
            if (valor <= 0) {
                ExibirMensagem.showError("O valor da pizza deve ser maior que R$ 0,00!");
                return false;
            }
        } catch (NumberFormatException e) {
            ExibirMensagem.showError("Informe um valor numérico válido!");
            return false;
        }

        return true;
    }
}
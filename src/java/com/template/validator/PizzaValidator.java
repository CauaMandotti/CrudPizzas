package com.template.validator;

import com.template.util.ExibirMensagem;

public class PizzaValidator {

    public boolean validarPizza(String sabor, String descricao, String valorTexto) {
        // 1. Validação do Sabor (Obrigatório)
        Validador<String> validadorSabor = new CampoObrigatorioValidador("Sabor", sabor);
        if (!validadorSabor.validar(validadorSabor.getValor())) {
            ExibirMensagem.showError(validadorSabor.getMensagemErro());
            return false;
        }

        // 2. Validação da Descrição (Obrigatório)
        Validador<String> validadorDescricao = new CampoObrigatorioValidador("Descrição", descricao);
        if (!validadorDescricao.validar(validadorDescricao.getValor())) {
            ExibirMensagem.showError(validadorDescricao.getMensagemErro());
            return false;
        }

        // 3. Validação do Valor (Obrigatório)
        Validador<String> validadorValor = new CampoObrigatorioValidador("Valor", valorTexto);
        if (!validadorValor.validar(validadorValor.getValor())) {
            ExibirMensagem.showError(validadorValor.getMensagemErro());
            return false;
        }

        // 4. Validação Formato do Valor (Numérico e maior que zero)
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
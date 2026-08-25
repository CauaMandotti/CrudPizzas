package com.template.validator;

import com.template.util.ExibirMensagem;
import java.util.ArrayList;
import java.util.List;

public class PizzaValidator {

    public boolean validarPizza(String sabor, String descricao, String valorTexto) {
        // Lista de validadores polimórficos
        List<Validator<String>> validadores = new ArrayList<>();

        // Validações de campos obrigatórios
        validadores.add(new CampoObrigatorioValidador("Sabor", sabor));
        validadores.add(new CampoObrigatorioValidador("Descrição", descricao));
        validadores.add(new CampoObrigatorioValidador("Valor", valorTexto));

        // Validação específica para garantir valor numérico maior que zero
        validadores.add(new ValorPositivoValidador("Valor", valorTexto));

        // Loop polimórfico conforme o slide do OCP
        for (Validator<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                ExibirMensagem.showError(validador.getMensagemErro());
                return false;
            }
        }

        return true;
    }
}
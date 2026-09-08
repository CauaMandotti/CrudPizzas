package com.template.validator;

import com.template.util.ExibirMensagem;
import java.util.ArrayList;
import java.util.List;

public class PizzaValidador implements IPizzaValidador {

    @Override
    public boolean validarPizza(String sabor, String descricao, String valorTexto) {
        // Lista genérica contendo todos os validadores (Requisito 3 - OCP SOLID)
        List<Validador<String>> validadores = new ArrayList<>();

        // Validações para o campo Sabor
        validadores.add(new CamposObrigatoriosValidador("Sabor", sabor));
        validadores.add(new TamanhoMinimoValidador("Sabor", sabor, 3));
        validadores.add(new TamanhoMaximoValidador("Sabor", sabor, 30));

        // Validações para o campo Descrição
        validadores.add(new CamposObrigatoriosValidador("Descrição", descricao));
        validadores.add(new TamanhoMinimoValidador("Descrição", descricao, 5));
        validadores.add(new TamanhoMaximoValidador("Descrição", descricao, 100));

        // Validações para o campo Valor
        validadores.add(new CamposObrigatoriosValidador("Valor", valorTexto));
        validadores.add(new ValorPositivoValidador("Valor", valorTexto));
        validadores.add(new ValorMaximoValidador("Valor", valorTexto, 500.00));

        // Utilizando obrigatoriamente a estrutura foreach para percorrer a lista genérica
        for (Validador<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                ExibirMensagem.showError(validador.getMensagemErro());
                return false;
            }
        }

        return true;
    }
}

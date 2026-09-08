package com.template.dao;

import com.template.dto.PizzaDTO;
import java.util.List;

public interface IPizzaDAO {
    void cadastrarPizza(PizzaDTO pizza);
    List<PizzaDTO> selecionarPizzas();
    void alterarPizza(PizzaDTO pizza);
    void excluirPizza(int id);
}

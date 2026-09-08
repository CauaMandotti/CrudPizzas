package com.template.main;

import com.template.controller.MainController;
import com.template.dao.IPizzaDAO;
import com.template.dao.PizzaDAO;
import com.template.validator.IPizzaValidador;
import com.template.validator.PizzaValidador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Instanciação das dependências concretas fora do Controller (Requisito 7)
        IPizzaValidador pizzaValidador = new PizzaValidador();
        IPizzaDAO pizzaDAO = new PizzaDAO();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/template/main.fxml"));

        // Configuração da Fábrica de Controllers para Injeção de Dependências (Requisito 6 e 7)
        loader.setControllerFactory(clazz -> {
            if (clazz == MainController.class) {
                return new MainController(pizzaValidador, pizzaDAO);
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Erro ao instanciar Controller na fábrica: " + clazz.getName(), e);
            }
        });

        Scene scene = new Scene(loader.load(), 900, 500);

        stage.setTitle("Sistema CRUD de Pizzas");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

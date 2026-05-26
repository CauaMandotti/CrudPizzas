package com.template;

import javafx.event.ActionEvent; // <-- IMPORT CORRIGIDO: Faltava esse cara aqui!
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.List;

public class MainController {

    @FXML private TextField txtId; // <-- CORREÇÃO: Adicionado o campo do ID que faltava!
    @FXML private TextField txtSabor;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtValor;
    @FXML private CheckBox chkDisponivel;

    @FXML private TableView<PizzaDTO> tblProduto;
    @FXML private TableColumn<PizzaDTO, String> colSabor;
    @FXML private TableColumn<PizzaDTO, String> colDescricao;
    @FXML private TableColumn<PizzaDTO, Double> colValor;
    @FXML private TableColumn<PizzaDTO, String> colDisponivel;

    @FXML private Button btnAdicionar;
    @FXML private Button btnAlterar;
    @FXML private Button btnCadastrar;

    @FXML
    private void initialize() {
        // Método executado quando a tela abre
        carregarPizzas();
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        String sabor = txtSabor.getText();
        String descricao = txtDescricao.getText();
        Double valor = Double.parseDouble(txtValor.getText());
        Boolean disponivel = chkDisponivel.isSelected();

        PizzaDTO objpizzadto = new PizzaDTO();
        objpizzadto.setSabor(sabor);
        objpizzadto.setDescricao(descricao);
        objpizzadto.setValor(valor);
        objpizzadto.setDisponivel(disponivel);

        PizzaDAO objpizzadao = new PizzaDAO();
        objpizzadao.cadastrarPizza(objpizzadto);

        carregarPizzas();
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());
        String sabor = txtSabor.getText();
        String descricao = txtDescricao.getText();
        Double valor = Double.parseDouble(txtValor.getText());
        Boolean disponivel = chkDisponivel.isSelected();

        PizzaDTO objpizzadto = new PizzaDTO();
        objpizzadto.setId(id);
        objpizzadto.setSabor(sabor);
        objpizzadto.setDescricao(descricao);
        objpizzadto.setValor(valor);
        objpizzadto.setDisponivel(disponivel);

        PizzaDAO objpizzadao = new PizzaDAO();
        objpizzadao.alterarPizza(objpizzadto);

        carregarPizzas();
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        int id = Integer.parseInt(txtId.getText());

        PizzaDAO objpizzadao = new PizzaDAO();
        objpizzadao.excluirPizza(id);

        carregarPizzas();
    }

    // <-- CORREÇÃO: Criado o método que busca as pizzas e atualiza a tabela na tela!
    private void carregarPizzas() {
        PizzaDAO objpizzadao = new PizzaDAO();
        List<PizzaDTO> listaPizzas = objpizzadao.selecionarPizzas();

        // Limpa a tabela e adiciona a lista atualizada vinda do banco
        tblProduto.getItems().clear();
        tblProduto.getItems().addAll(listaPizzas);
    }
}
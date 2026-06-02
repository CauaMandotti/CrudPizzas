package com.template;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class MainController {

    @FXML private TextField txtId;
    @FXML private TextField txtSabor;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtValor;
    @FXML private CheckBox chkDisponivel;

    // Componentes da Tabela
    @FXML private TableView<PizzaDTO> tblProduto;
    @FXML private TableColumn<PizzaDTO, String> colSabor;
    @FXML private TableColumn<PizzaDTO, String> colDescricao;
    @FXML private TableColumn<PizzaDTO, Double> colValor;
    @FXML private TableColumn<PizzaDTO, Boolean> colDisponivel;

    @FXML private Button btnAdicionar;
    @FXML private Button btnAlterar;
    @FXML private Button btnCadastrar;
    @FXML private Button btnLimpar;

    @FXML
    private void initialize() {
        // Vincula as colunas da tabela com os atributos do seu PizzaDTO
        colSabor.setCellValueFactory(new PropertyValueFactory<>("sabor"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colDisponivel.setCellValueFactory(new PropertyValueFactory<>("disponivel"));

        // Apenas lista as pizzas ao iniciar a tela, sem travar o programa
        carregarPizzas();
    }

    // <-- O MÉTODO DA SUA PROFESSORA ADICIONADO AQUI!
    @FXML
    private void carregarCampos() {
        PizzaDTO pizzaDTO = tblProduto.getSelectionModel().getSelectedItem();

        if (pizzaDTO != null) {
            txtId.setText(String.valueOf(pizzaDTO.getId()));
            txtSabor.setText(pizzaDTO.getSabor());
            txtDescricao.setText(pizzaDTO.getDescricao());
            txtValor.setText(String.valueOf(pizzaDTO.getValor()));
            chkDisponivel.setSelected(pizzaDTO.isDisponivel());
        }
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        PizzaDTO objpizzadto = new PizzaDTO();
        objpizzadto.setSabor(txtSabor.getText());
        objpizzadto.setDescricao(txtDescricao.getText());
        objpizzadto.setValor(Double.parseDouble(txtValor.getText()));
        objpizzadto.setDisponivel(chkDisponivel.isSelected());

        PizzaDAO objpizzadao = new PizzaDAO();
        objpizzadao.cadastrarPizza(objpizzadto);

        carregarPizzas();
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        PizzaDTO objpizzadto = new PizzaDTO();
        objpizzadto.setId(Integer.parseInt(txtId.getText()));
        objpizzadto.setSabor(txtSabor.getText());
        objpizzadto.setDescricao(txtDescricao.getText());
        objpizzadto.setValor(Double.parseDouble(txtValor.getText()));
        objpizzadto.setDisponivel(chkDisponivel.isSelected());

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

    @FXML
    private void btnLimparAction(ActionEvent event) {
        txtId.clear();
        txtSabor.clear();
        txtDescricao.clear();
        txtValor.clear();
        chkDisponivel.setSelected(false);
    }

    private void carregarPizzas() {
        PizzaDAO objpizzadao = new PizzaDAO();
        List<PizzaDTO> listaPizzas = objpizzadao.selecionarPizzas();

        tblProduto.getItems().clear();
        tblProduto.getItems().addAll(listaPizzas);
    }
}
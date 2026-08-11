package com.template.controller;

import com.template.model.PizzaDAO;
import com.template.model.PizzaDTO;
import com.template.util.ExibirMensagem;
import com.template.validator.PizzaValidator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.util.List;

public class MainController {

    @FXML private TextField txtId;
    @FXML private TextField txtSabor;
    @FXML private TextField txtDescricao;
    @FXML private TextField txttValor;
    @FXML private CheckBox chkDisponivel;

    @FXML private TableView<PizzaDTO> tblPizza;
    @FXML private TableColumn<PizzaDTO, Integer> colId;
    @FXML private TableColumn<PizzaDTO, String> colSabor;
    @FXML private TableColumn<PizzaDTO, String> colDescricao;
    @FXML private TableColumn<PizzaDTO, Double> colValor;
    @FXML private TableColumn<PizzaDTO, Boolean> colDisponivel;

    @FXML private Button btnCadastrar;
    @FXML private Button btnAlterar;
    @FXML private Button btnExcluir;
    @FXML private Button btnLimpar;

    // Instância do validador
    private final PizzaValidator pizzaValidator = new PizzaValidator();

    @FXML
    private void initialize() {
        if (colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSabor.setCellValueFactory(new PropertyValueFactory<>("sabor"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colDisponivel.setCellValueFactory(new PropertyValueFactory<>("disponivel"));

        if (txtId != null) {
            txtId.setEditable(false);
        }

        txttValor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*([\\.,]\\d*)?")) {
                txttValor.setText(oldValue);
            }
        });

        ajustarBotoes(false);
        carregarPizzas();
    }

    @FXML
    private void carregarCampos(MouseEvent event) {
        PizzaDTO pizzaDTO = tblPizza.getSelectionModel().getSelectedItem();

        if (pizzaDTO != null) {
            if (txtId != null) txtId.setText(String.valueOf(pizzaDTO.getId()));
            txtSabor.setText(pizzaDTO.getSabor());
            txtDescricao.setText(pizzaDTO.getDescricao());
            txttValor.setText(String.valueOf(pizzaDTO.getValor()));
            chkDisponivel.setSelected(pizzaDTO.isDisponivel());

            ajustarBotoes(true);
            ExibirMensagem.showInfo("Pizza selecionada para edição.");
        }
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        // Validação enviada para o PizzaValidator
        if (!pizzaValidator.validarPizza(txtSabor.getText(), txtDescricao.getText(), txttValor.getText())) {
            return;
        }

        try {
            PizzaDTO objpizzadto = new PizzaDTO();
            objpizzadto.setSabor(txtSabor.getText());
            objpizzadto.setDescricao(txtDescricao.getText());
            objpizzadto.setValor(Double.parseDouble(txttValor.getText().replace(",", ".")));
            objpizzadto.setDisponivel(chkDisponivel.isSelected());

            PizzaDAO objpizzadao = new PizzaDAO();
            objpizzadao.cadastrarPizza(objpizzadto);

            ExibirMensagem.showInfo("Pizza cadastrada com sucesso!");
            carregarPizzas();
            limparCampos();
        } catch (Exception e) {
            ExibirMensagem.showError("Erro ao cadastrar pizza no banco de dados.");
        }
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        PizzaDTO pizzaSelecionada = tblPizza.getSelectionModel().getSelectedItem();

        if (pizzaSelecionada == null) {
            ExibirMensagem.showError("Erro: Selecione uma pizza na tabela para alterar.");
            return;
        }

        if (!pizzaValidator.validarPizza(txtSabor.getText(), txtDescricao.getText(), txttValor.getText())) {
            return;
        }

        try {
            PizzaDTO objpizzadto = new PizzaDTO();
            objpizzadto.setId(pizzaSelecionada.getId());
            objpizzadto.setSabor(txtSabor.getText());
            objpizzadto.setDescricao(txtDescricao.getText());
            objpizzadto.setValor(Double.parseDouble(txttValor.getText().replace(",", ".")));
            objpizzadto.setDisponivel(chkDisponivel.isSelected());

            PizzaDAO objpizzadao = new PizzaDAO();
            objpizzadao.alterarPizza(objpizzadto);

            ExibirMensagem.showInfo("Pizza atualizada com sucesso!");
            carregarPizzas();
            limparCampos();
        } catch (Exception e) {
            ExibirMensagem.showError("Erro ao atualizar a pizza.");
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        PizzaDTO pizzaSelecionada = tblPizza.getSelectionModel().getSelectedItem();

        if (pizzaSelecionada == null) {
            ExibirMensagem.showError("Erro: Selecione uma pizza para excluir.");
            return;
        }

        boolean confirmar = ExibirMensagem.showConfirmation("Tem certeza que deseja excluir a pizza " + pizzaSelecionada.getSabor() + "?");
        if (!confirmar) {
            return;
        }

        try {
            PizzaDAO objpizzadao = new PizzaDAO();
            objpizzadao.excluirPizza(pizzaSelecionada.getId());

            ExibirMensagem.showInfo("Pizza excluída com sucesso!");
            carregarPizzas();
            limparCampos();
        } catch (Exception e) {
            ExibirMensagem.showError("Erro ao excluir o registro.");
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
    }

    private void limparCampos() {
        if (txtId != null) txtId.clear();
        txtSabor.clear();
        txtDescricao.clear();
        txttValor.clear();
        chkDisponivel.setSelected(false);

        tblPizza.getSelectionModel().clearSelection();
        ajustarBotoes(false);
        txtSabor.requestFocus();
    }

    private void ajustarBotoes(boolean ativo) {
        btnAlterar.setDisable(!ativo);
        btnExcluir.setDisable(!ativo);
    }

    private void carregarPizzas() {
        PizzaDAO objpizzadao = new PizzaDAO();
        List<PizzaDTO> listaPizzas = objpizzadao.selecionarPizzas();

        tblPizza.getItems().clear();
        if (listaPizzas != null) {
            tblPizza.getItems().addAll(listaPizzas);
        }
    }
}
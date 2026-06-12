package com.template;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.util.List;

public class MainController {

    // ID temporário para controle interno (já que não há txtId no FXML)
    private int idSelecionado = -1;

    @FXML private TextField txtId;
    @FXML private TextField txtSabor;
    @FXML private TextField txtDescricao;
    @FXML private TextField txttValor;

    // LINHA CORRIGIDA: A declaração que estava faltando para sumir com os 4 erros!
    @FXML private CheckBox chkDisponivel;

    @FXML private Label lblMensagem;

    // Tabela e Colunas
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

        ajustarBotoes(false);
        carregarPizzas();
    }

    @FXML
    private void carregarCampos(MouseEvent event) {
        PizzaDTO pizzaDTO = tblPizza.getSelectionModel().getSelectedItem();

        if (pizzaDTO != null) {
            idSelecionado = pizzaDTO.getId();
            if (txtId != null) txtId.setText(String.valueOf(pizzaDTO.getId()));
            txtSabor.setText(pizzaDTO.getSabor());
            txtDescricao.setText(pizzaDTO.getDescricao());
            txttValor.setText(String.valueOf(pizzaDTO.getValor()));
            chkDisponivel.setSelected(pizzaDTO.isDisponivel());

            ajustarBotoes(true);
            exibirMensagem("Pizza selecionada para edição.", "-fx-text-fill: #0044ff;");
        }
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        if (txtSabor.getText().trim().isEmpty() || txttValor.getText().trim().isEmpty()) {
            exibirMensagem("Erro: Sabor e Valor são obrigatórios!", "-fx-text-fill: #bb0b0b;");
            return;
        }

        try {
            PizzaDTO objpizzadto = new PizzaDTO();
            objpizzadto.setSabor(txtSabor.getText());
            objpizzadto.setDescricao(txtDescricao.getText());

            String valorTexto = txttValor.getText().replace(",", ".");
            objpizzadto.setValor(Double.parseDouble(valorTexto));

            objpizzadto.setDisponivel(chkDisponivel.isSelected());

            PizzaDAO objpizzadao = new PizzaDAO();
            objpizzadao.cadastrarPizza(objpizzadto);

            exibirMensagem("Pizza cadastrada com sucesso!", "-fx-text-fill: #00aa00;");

            carregarPizzas();
            limparCampos();

        } catch (NumberFormatException e) {
            exibirMensagem("Erro: O valor informado é inválido.", "-fx-text-fill: #bb0b0b;");
        }
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        if (idSelecionado == -1) {
            exibirMensagem("Erro: Selecione uma pizza na tabela para alterar.", "-fx-text-fill: #bb0b0b;");
            return;
        }

        try {
            PizzaDTO objpizzadto = new PizzaDTO();
            objpizzadto.setId(idSelecionado);
            objpizzadto.setSabor(txtSabor.getText());
            objpizzadto.setDescricao(txtDescricao.getText());

            String valorTexto = txttValor.getText().replace(",", ".");
            objpizzadto.setValor(Double.parseDouble(valorTexto));

            objpizzadto.setDisponivel(chkDisponivel.isSelected());

            PizzaDAO objpizzadao = new PizzaDAO();
            objpizzadao.alterarPizza(objpizzadto);

            exibirMensagem("Pizza atualizada com sucesso!", "-fx-text-fill: #00aa00;");

            carregarPizzas();
            limparCampos();

        } catch (NumberFormatException e) {
            exibirMensagem("Erro: Verifique o preço digitado.", "-fx-text-fill: #bb0b0b;");
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        if (idSelecionado == -1) {
            exibirMensagem("Erro: Selecione uma pizza para excluir.", "-fx-text-fill: #bb0b0b;");
            return;
        }

        try {
            PizzaDAO objpizzadao = new PizzaDAO();
            objpizzadao.excluirPizza(idSelecionado);

            exibirMensagem("Pizza excluída com sucesso!", "-fx-text-fill: #00aa00;");

            carregarPizzas();
            limparCampos();

        } catch (Exception e) {
            exibirMensagem("Erro ao excluir o registro.", "-fx-text-fill: #bb0b0b;");
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
        if (lblMensagem != null) lblMensagem.setText("");
    }

    private void limparCampos() {
        if (txtId != null) txtId.clear();
        txtSabor.clear();
        txtDescricao.clear();
        txttValor.clear();
        chkDisponivel.setSelected(false);
        idSelecionado = -1;

        ajustarBotoes(false);
        txtSabor.requestFocus();
    }

    private void ajustarBotoes(boolean ativo) {
        btnAlterar.setDisable(!ativo);
        btnExcluir.setDisable(!ativo);
    }

    private void exibirMensagem(String texto, String estiloCss) {
        if (lblMensagem != null) {
            lblMensagem.setText(texto);
            lblMensagem.setStyle(estiloCss);
        }
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
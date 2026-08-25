package com.template.controller;

import com.template.model.PizzaDAO;
import com.template.model.PizzaDTO;
import com.template.util.ExibirMensagem;
import com.template.util.PizzaFormUtil;
import com.template.util.PizzaTableUtil;
import com.template.validator.PizzaValidator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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

    private final PizzaValidator pizzaValidator = new PizzaValidator();
    private final PizzaDAO pizzaDAO = new PizzaDAO();

    @FXML
    private void initialize() {
        PizzaTableUtil.configurarColunas(colId, colSabor, colDescricao, colValor, colDisponivel);

        if (txtId != null) {
            txtId.setEditable(false);
        }

        txttValor.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*([\\.,]\\d*)?")) {
                txttValor.setText(oldValue);
            }
        });

        atualizarTela();
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        salvarOuAtualizarPizza(null, "cadastrar", "Pizza cadastrada com sucesso!");
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        PizzaDTO selecionada = tblPizza.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            ExibirMensagem.showError("Selecione uma pizza na tabela para alterar.");
            return;
        }

        salvarOuAtualizarPizza(selecionada.getId(), "alterar", "Pizza atualizada com sucesso!");
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        PizzaDTO selecionada = tblPizza.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            ExibirMensagem.showError("Selecione uma pizza para excluir.");
            return;
        }

        if (ExibirMensagem.showConfirmation("Tem certeza que deseja excluir a pizza " + selecionada.getSabor() + "?")) {
            try {
                pizzaDAO.excluirPizza(selecionada.getId());
                ExibirMensagem.showInfo("Pizza excluída com sucesso!");
                atualizarTela();
            } catch (Exception e) {
                ExibirMensagem.showError("Erro ao excluir o registro do banco de dados.");
            }
        }
    }

    @FXML
    private void carregarCampos(MouseEvent event) {
        PizzaDTO pizzaDTO = tblPizza.getSelectionModel().getSelectedItem();
        if (pizzaDTO != null) {
            PizzaFormUtil.preencherFormulario(pizzaDTO, txtId, txtSabor, txtDescricao, txttValor, chkDisponivel);
            PizzaFormUtil.ajustarBotoes(btnAlterar, btnExcluir, true);
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        atualizarTela();
    }

    private void salvarOuAtualizarPizza(Integer id, String operacao, String mensagemSucesso) {
        // Validação desacoplada via OCP no PizzaValidator
        if (!pizzaValidator.validarPizza(txtSabor.getText(), txtDescricao.getText(), txttValor.getText())) {
            return;
        }

        try {
            PizzaDTO dto = criarDTOComDadosDoFormulario(id);

            if ("cadastrar".equals(operacao)) {
                pizzaDAO.cadastrarPizza(dto);
            } else if ("alterar".equals(operacao)) {
                pizzaDAO.alterarPizza(dto);
            }

            ExibirMensagem.showInfo(mensagemSucesso);
            atualizarTela();
        } catch (Exception e) {
            ExibirMensagem.showError("Erro ao processar a operação no banco de dados.");
        }
    }

    private PizzaDTO criarDTOComDadosDoFormulario(Integer id) {
        PizzaDTO dto = new PizzaDTO();
        if (id != null) {
            dto.setId(id);
        }
        dto.setSabor(txtSabor.getText());
        dto.setDescricao(txtDescricao.getText());
        dto.setValor(Double.parseDouble(txttValor.getText().replace(",", ".")));
        dto.setDisponivel(chkDisponivel.isSelected());
        return dto;
    }

    private void atualizarTela() {
        PizzaFormUtil.limparCampos(txtId, txtSabor, txtDescricao, txttValor, chkDisponivel);
        tblPizza.getSelectionModel().clearSelection();
        PizzaFormUtil.ajustarBotoes(btnAlterar, btnExcluir, false);
        PizzaTableUtil.carregarPizzas(tblPizza);
    }
}
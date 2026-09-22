package com.template.controller;

import com.template.dao.IPizzaDAO;
import com.template.dto.PizzaDTO;
import com.template.util.ExibirMensagem;
import com.template.util.PizzaFormUtil;
import com.template.util.PizzaTableUtil;
import com.template.validator.IPizzaValidador;

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

    // Dependências declaradas via Interfaces abstraídas (DIP - SOLID)
    private final IPizzaValidador pizzaValidador;
    private final IPizzaDAO pizzaDAO;

    // Injeção de Dependência por construtor (Requisito 6)
    public MainController(IPizzaValidador pizzaValidador, IPizzaDAO pizzaDAO) {
        this.pizzaValidador = pizzaValidador;
        this.pizzaDAO = pizzaDAO;
    }

    @FXML
    private void initialize() {
        PizzaTableUtil.configurarColunas(colId, colSabor, colDescricao, colValor, colDisponivel);

        if (txtId != null) {
            txtId.setEditable(false);
        }

        try {
            PizzaFormUtil.atualizarTela(txtId, txtSabor, txtDescricao, txttValor, chkDisponivel, tblPizza, btnAlterar, btnExcluir, pizzaDAO);
        } catch (Exception e) {
            ExibirMensagem.showError("Erro ao conectar ao banco de dados PostgreSQL.\nVerifique se o banco de dados está rodando.");
        }
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        if (!pizzaValidador.validarPizza(txtSabor.getText(), txtDescricao.getText(), txttValor.getText())) {
            return;
        }

        try {
            PizzaDTO dto = PizzaFormUtil.criarDTOComDadosDoFormulario(null, txtSabor, txtDescricao, txttValor, chkDisponivel);
            pizzaDAO.cadastrarPizza(dto);
            ExibirMensagem.showInfo("Pizza cadastrada com sucesso!");
            PizzaFormUtil.atualizarTela(txtId, txtSabor, txtDescricao, txttValor, chkDisponivel, tblPizza, btnAlterar, btnExcluir, pizzaDAO);
        } catch (Exception e) {
            ExibirMensagem.showError("Erro ao processar a operação no banco de dados.");
        }
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        PizzaDTO selecionada = tblPizza.getSelectionModel().getSelectedItem();

        if (!pizzaValidador.validarPizza(txtSabor.getText(), txtDescricao.getText(), txttValor.getText())) {
            return;
        }

        try {
            PizzaDTO dto = PizzaFormUtil.criarDTOComDadosDoFormulario(selecionada.getId(), txtSabor, txtDescricao, txttValor, chkDisponivel);
            pizzaDAO.alterarPizza(dto);
            ExibirMensagem.showInfo("Pizza atualizada com sucesso!");
            PizzaFormUtil.atualizarTela(txtId, txtSabor, txtDescricao, txttValor, chkDisponivel, tblPizza, btnAlterar, btnExcluir, pizzaDAO);
        } catch (Exception e) {
            ExibirMensagem.showError("Erro ao processar a operação no banco de dados.");
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        PizzaDTO selecionada = tblPizza.getSelectionModel().getSelectedItem();

        if (ExibirMensagem.showConfirmation("Tem certeza que deseja excluir a pizza " + selecionada.getSabor() + "?")) {
            try {
                pizzaDAO.excluirPizza(selecionada.getId());
                ExibirMensagem.showInfo("Pizza excluída com sucesso!");
                PizzaFormUtil.atualizarTela(txtId, txtSabor, txtDescricao, txttValor, chkDisponivel, tblPizza, btnAlterar, btnExcluir, pizzaDAO);
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
        PizzaFormUtil.atualizarTela(txtId, txtSabor, txtDescricao, txttValor, chkDisponivel, tblPizza, btnAlterar, btnExcluir, pizzaDAO);
    }
}
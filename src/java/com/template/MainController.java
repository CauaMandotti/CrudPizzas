package com.template;

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

    // ID temporário para controle interno (já que não há txtId no FXML)
    private int idSelecionado = -1;

    @FXML private TextField txtSabor;
    @FXML private TextField txtDescricao;

    // CORRIGIDO: Agora com dois 't's igualzinho ao seu FXML (txttValor)
    @FXML private TextField txttValor;

    @FXML private CheckBox chkDisponivel;

    // Tabela e Colunas (Adicionado colId que estava faltando)
    @FXML private TableView<PizzaDTO> tblPizza;
    @FXML private TableColumn<PizzaDTO, Integer> colId; // Adicionado!
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
        // Vincula TODAS as colunas com os atributos do PizzaDTO
        if (colId != null) colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSabor.setCellValueFactory(new PropertyValueFactory<>("sabor"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colDisponivel.setCellValueFactory(new PropertyValueFactory<>("disponivel"));

        // Carrega os dados do banco na tabela
        carregarPizzas();
    }

    @FXML
    private void carregarCampos(MouseEvent event) {
        // Pega a pizza que o usuário clicou na tabela
        PizzaDTO pizzaDTO = tblPizza.getSelectionModel().getSelectedItem();

        if (pizzaDTO != null) {
            idSelecionado = pizzaDTO.getId(); // Guarda o ID para Update e Delete
            txtSabor.setText(pizzaDTO.getSabor());
            txtDescricao.setText(pizzaDTO.getDescricao());
            txttValor.setText(String.valueOf(pizzaDTO.getValor()));
            chkDisponivel.setSelected(pizzaDTO.isDisponivel());
        }
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        try {
            if (txtSabor.getText().isEmpty() || txttValor.getText().isEmpty()) {
                System.out.println("Erro: Preencha pelo menos o Sabor e o Valor!");
                return;
            }

            PizzaDTO objpizzadto = new PizzaDTO();
            objpizzadto.setSabor(txtSabor.getText());
            objpizzadto.setDescricao(txtDescricao.getText());

            // Corrige vírgula caso o usuário digite errado
            String valorTexto = txttValor.getText().replace(",", ".");
            objpizzadto.setValor(Double.parseDouble(valorTexto));

            objpizzadto.setDisponivel(chkDisponivel.isSelected());

            PizzaDAO objpizzadao = new PizzaDAO();
            objpizzadao.cadastrarPizza(objpizzadto);

            carregarPizzas();
            btnLimparAction(event);

        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite um preço válido no formato 00.00");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        try {
            if (idSelecionado == -1) {
                System.out.println("Erro: Clique em uma pizza na tabela primeiro para poder alterar!");
                return;
            }

            PizzaDTO objpizzadto = new PizzaDTO();
            objpizzadto.setId(idSelecionado); // Usa o ID guardado no clique
            objpizzadto.setSabor(txtSabor.getText());
            objpizzadto.setDescricao(txtDescricao.getText());

            String valorTexto = txttValor.getText().replace(",", ".");
            objpizzadto.setValor(Double.parseDouble(valorTexto));

            objpizzadto.setDisponivel(chkDisponivel.isSelected());

            PizzaDAO objpizzadao = new PizzaDAO();
            objpizzadao.alterarPizza(objpizzadto);

            carregarPizzas();
            btnLimparAction(event);

        } catch (Exception e) {
            System.out.println("Erro ao alterar os dados.");
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        try {
            if (idSelecionado == -1) {
                System.out.println("Erro: Selecione uma pizza na tabela para excluir!");
                return;
            }

            PizzaDAO objpizzadao = new PizzaDAO();
            objpizzadao.excluirPizza(idSelecionado);

            carregarPizzas();
            btnLimparAction(event);

        } catch (Exception e) {
            System.out.println("Erro ao excluir.");
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        idSelecionado = -1; // Reseta o ID selecionado
        txtSabor.clear();
        txtDescricao.clear();
        txttValor.clear();
        chkDisponivel.setSelected(false);
    }

    private void carregarPizzas() {
        PizzaDAO objpizzadao = new PizzaDAO();
        List<PizzaDTO> listaPizzas = objpizzadao.selecionarPizzas();

        tblPizza.getItems().clear();
        if (listaPizzas != null) {
            tblPizza.getItems().addAll(listaPizzas); //comentario
        }
    }
}
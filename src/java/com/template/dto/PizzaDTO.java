package com.template.dto;

public class PizzaDTO {
    private int id;
    private String sabor;
    private String descricao;
    private Double valor;
    private boolean disponivel;

    public PizzaDTO() {}

    public PizzaDTO(int id, String sabor, String descricao, Double valor, boolean disponivel) {
        this.id = id;
        this.sabor = sabor;
        this.descricao = descricao;
        this.valor = valor;
        this.disponivel = disponivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}

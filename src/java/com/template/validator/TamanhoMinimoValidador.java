package com.template.validator;

public class TamanhoMinimoValidador implements Validador<String> {
    private final String nomeCampo;
    private final String valor;
    private final int tamanhoMinimo;

    public TamanhoMinimoValidador(String nomeCampo, String valor, int tamanhoMinimo) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
        this.tamanhoMinimo = tamanhoMinimo;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (valorAtual == null) {
            return false;
        }
        return valorAtual.trim().length() >= tamanhoMinimo;
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " deve ter no mínimo " + tamanhoMinimo + " caracteres.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}

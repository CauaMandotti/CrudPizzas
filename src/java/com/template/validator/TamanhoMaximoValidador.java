package com.template.validator;

public class TamanhoMaximoValidador implements Validador<String> {
    private final String nomeCampo;
    private final String valor;
    private final int tamanhoMaximo;

    public TamanhoMaximoValidador(String nomeCampo, String valor, int tamanhoMaximo) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
        this.tamanhoMaximo = tamanhoMaximo;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (valorAtual == null) {
            return true;
        }
        return valorAtual.trim().length() <= tamanhoMaximo;
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " não pode ter mais que " + tamanhoMaximo + " caracteres.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}

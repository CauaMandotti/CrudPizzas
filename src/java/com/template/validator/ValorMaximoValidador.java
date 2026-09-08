package com.template.validator;

public class ValorMaximoValidador implements Validador<String> {
    private final String nomeCampo;
    private final String valorTexto;
    private final double valorMaximo;

    public ValorMaximoValidador(String nomeCampo, String valorTexto, double valorMaximo) {
        this.nomeCampo = nomeCampo;
        this.valorTexto = valorTexto;
        this.valorMaximo = valorMaximo;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (valorAtual == null || valorAtual.trim().isEmpty()) {
            return true;
        }
        try {
            double valorNum = Double.parseDouble(valorAtual.replace(",", "."));
            return valorNum <= valorMaximo;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " não pode ultrapassar R$ " + String.format("%.2f", valorMaximo) + ".";
    }

    @Override
    public String getValor() {
        return valorTexto;
    }
}

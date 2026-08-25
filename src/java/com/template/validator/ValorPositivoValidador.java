package com.template.validator;

public class ValorPositivoValidador implements Validator<String> {
    private final String nomeCampo;
    private final String valorTexto;

    public ValorPositivoValidador(String nomeCampo, String valorTexto) {
        this.nomeCampo = nomeCampo;
        this.valorTexto = valorTexto;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (this.valorTexto == null || this.valorTexto.trim().isEmpty()) {
            return false;
        }
        try {
            double valorNum = Double.parseDouble(this.valorTexto.replace(",", "."));
            return valorNum > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " deve ser um número válido e maior que R$ 0,00.";
    }

    @Override
    public String getValor() {
        return valorTexto;
    }
}
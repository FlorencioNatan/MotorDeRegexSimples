package com.motorDeRegexSimples.EstruturaDeDados.Automato;

public class Caractere {

	private char valor = ' ';
	private boolean transicaoVazia = false;

	public Caractere(char valor, boolean transicaoVazia) {
		this.valor = valor;
		this.transicaoVazia = transicaoVazia;
	}

	public char getValor() {
		return this.valor;
	}

	public boolean isTransacaoVazia() {
		return this.transicaoVazia;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Caractere) {
			if (((Caractere) obj).transicaoVazia || this.transicaoVazia) {
				return true;
			}
			if (!((Caractere) obj).transicaoVazia && !this.transicaoVazia && ((Caractere) obj).valor == this.valor) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (this.transicaoVazia) {
			return super.hashCode();
		}
		return Character.getNumericValue(valor);
	}

}

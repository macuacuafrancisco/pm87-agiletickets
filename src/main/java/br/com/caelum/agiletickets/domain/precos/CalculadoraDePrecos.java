package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;

		int totalDisponivel = sessao.getTotalIngressos() - sessao.getIngressosReservados();
		double totalIngressos = sessao.getTotalIngressos().doubleValue();
		TipoDeEspetaculo tipoEspetaculo = sessao.getEspetaculo().getTipo();
		if (tipoEspetaculo.equals(TipoDeEspetaculo.CINEMA) || tipoEspetaculo.equals(TipoDeEspetaculo.SHOW)) {
			// quando estiver acabando os ingressos...
			if (totalDisponivel / totalIngressos <= 0.05) {
				preco = sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
			} else {
				preco = sessao.getPreco();
			}
		} else if (tipoEspetaculo.equals(TipoDeEspetaculo.BALLET)
				|| tipoEspetaculo.equals(TipoDeEspetaculo.ORQUESTRA)) {
			if (totalDisponivel / totalIngressos <= 0.50) {
				preco = sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(0.20)));
			} else {
				preco = sessao.getPreco();
			}

			if (sessao.getDuracaoEmMinutos() > 60) {
				preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
			}
		} else {
			// nao aplica aumento para teatro (quem vai é pobretão)
			preco = sessao.getPreco();
		}

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

}
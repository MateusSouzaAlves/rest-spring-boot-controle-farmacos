package br.com.farmacos.funcionario;

import br.com.farmacos.endereco.DadosEndereco;

public record DadosCadastroFuncionario(String nome, String email, String cpf, Especialidade especialidade,
		DadosEndereco endereço) {

}

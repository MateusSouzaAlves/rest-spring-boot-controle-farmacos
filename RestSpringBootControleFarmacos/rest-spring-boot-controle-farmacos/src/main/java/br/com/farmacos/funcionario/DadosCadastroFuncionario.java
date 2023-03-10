package br.com.farmacos.funcionario;

import org.hibernate.validator.constraints.br.CPF;

import br.com.farmacos.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroFuncionario(
		
		@NotBlank
		String nome,
		@NotBlank
		@Email
		String email,
		@CPF
		String cpf,
		@NotNull
		Especialidade especialidade,
		@NotNull
		@Valid
		DadosEndereco endereco) {

}

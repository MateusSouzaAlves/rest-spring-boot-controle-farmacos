package br.com.farmacos.remedio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroRemedio(
		
		@NotBlank
		String nome,
		@NotNull
		Via via,
		@NotBlank
		String lote,
		@NotBlank
		String validade,
		@NotNull
		Laboratorio laboratorio) {

}

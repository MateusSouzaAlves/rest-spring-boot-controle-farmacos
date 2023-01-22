package br.com.farmacos.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco( 
		
		@NotBlank
		@Pattern(regexp = "\\d{8}")
		String cep,
		@NotBlank
		String uf,
		@NotBlank
		String cidade,
		@NotBlank
		String bairro,
		
		String complemento,
		
		String numero) {

}

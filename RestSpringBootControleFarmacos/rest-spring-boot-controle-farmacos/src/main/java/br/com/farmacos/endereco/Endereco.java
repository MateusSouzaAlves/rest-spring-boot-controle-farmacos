package br.com.farmacos.endereco;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

	private String cep;
	private String uf;
	private String cidade;
	private String bairro;
	private String complemento;
	private String numero;
	
	public Endereco(DadosEndereco dados) {
		this.cep = dados.cep();
		this.uf =dados.uf();
		this.cidade =dados.cidade();
		this.bairro =dados.bairro();
		this.complemento =dados.complemento();
		this.numero =dados.numero();
		
	}
}

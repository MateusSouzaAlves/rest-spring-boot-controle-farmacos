package br.com.farmacos.remedio;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="remedios")
@Entity(name="remedio")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Remedio {
	
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nome;
	private String lote;
	private String validade;
	
	@Enumerated(EnumType.STRING)
	private Laboratorio laboratorio;
	@Enumerated(EnumType.STRING)
	private Via via;

	public Remedio(DadosCadastroRemedio dados) {
		this.nome = dados.nome();
		this.lote = dados.lote();
		this.validade = dados.validade();
		this.laboratorio = dados.laboratorio();
		this.via = dados.via();
	}

	
}

package br.com.farmacos.remedio;

public record DadosRemedio(Long id, String nome, Laboratorio laboratorio, Via via) {

	public DadosRemedio(Remedio remedio) {
		this(remedio.getId(),remedio.getNome(), remedio.getLaboratorio(), remedio.getVia());
		
	}
}

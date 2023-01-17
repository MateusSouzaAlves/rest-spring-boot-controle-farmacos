package br.com.farmacos.remedio;

public record DadosListagemRemedios(Long id, String nome, Laboratorio laboratorio, Via via ) {
	
	public DadosListagemRemedios(Remedio remedio) {
		this(remedio.getId(),remedio.getNome(), remedio.getLaboratorio(), remedio.getVia());
	}

}

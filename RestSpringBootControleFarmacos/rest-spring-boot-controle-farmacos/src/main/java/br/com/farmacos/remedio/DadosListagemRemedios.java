package br.com.farmacos.remedio;

public record DadosListagemRemedios(String nome, Laboratorio laboratorio, Via via ) {
	
	public DadosListagemRemedios(Remedio remedio) {
		this(remedio.getNome(), remedio.getLaboratorio(), remedio.getVia());
	}

}

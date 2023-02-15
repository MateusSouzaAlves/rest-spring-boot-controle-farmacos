package br.com.farmacos.usuario;

public record DadosListagemUsuarios(String nome, String email) {

	public DadosListagemUsuarios(Usuario usuario) {
		this(usuario.getNome(),usuario.getEmail());
	}
}

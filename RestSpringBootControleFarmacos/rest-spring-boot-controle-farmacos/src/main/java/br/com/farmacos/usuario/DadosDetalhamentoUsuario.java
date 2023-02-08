package br.com.farmacos.usuario;

import br.com.farmacos.endereco.Endereco;

public record DadosDetalhamentoUsuario(Long id, String nome, String login, String email, String cpf, Endereco endereco) {

	public DadosDetalhamentoUsuario(Usuario usuario) {
		this(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getEmail(), usuario.getCpf(), usuario.getEndereco());
	}
}

package br.com.farmacos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.farmacos.remedio.RemedioRepository;

@Service
public class RemedioService {

	@Autowired
	private RemedioRepository repository;
	
	public void validacaoQuantidade(Integer quantidade) {
		if (quantidade == null) {
			throw new RuntimeException("Parâmetro quantidade é obrigatório");
		}
	}
	
	public void adicionarQuantidade(Long id, int quantidade) {
		validacaoQuantidade(quantidade);
		var remedio = repository.getReferenceById(id);
		remedio.addQuantidade(quantidade);
		repository.save(remedio);
	}
	
	public void removerQuantidade(Long id, int quantidade) {
		validacaoQuantidade(quantidade);
		var remedio = repository.getReferenceById(id);
		remedio.removeQuantidade(quantidade);
		repository.save(remedio);
	}
}

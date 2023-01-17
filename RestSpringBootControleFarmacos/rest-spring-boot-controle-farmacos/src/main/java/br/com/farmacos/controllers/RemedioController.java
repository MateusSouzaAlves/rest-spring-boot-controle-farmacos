package br.com.farmacos.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.farmacos.remedio.DadosAtualizacaoRemedio;
import br.com.farmacos.remedio.DadosCadastroRemedio;
import br.com.farmacos.remedio.DadosListagemRemedios;
import br.com.farmacos.remedio.DadosRemedio;
import br.com.farmacos.remedio.Remedio;
import br.com.farmacos.remedio.RemedioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("remedios")
public class RemedioController {
	
	@Autowired
	private RemedioRepository repository;

	@PostMapping
	@Transactional
	public void cadastrar(@RequestBody @Valid DadosCadastroRemedio dados) {
		repository.save(new Remedio(dados));
		
	}
	
	@GetMapping
	public Page<DadosListagemRemedios> listar (Pageable paginacao){
		return repository.findAllByAtivoTrue(paginacao).map(DadosListagemRemedios::new);
	}
	
	@GetMapping("/{id}")
	public Optional<DadosRemedio> buscarPorId(@PathVariable Long id) {
		return repository.findById(id).map(DadosRemedio::new);
	}
	
	
	@PutMapping
	@Transactional
	public void atualizar(@RequestBody @Valid DadosAtualizacaoRemedio dados) {
		var remedio = repository.getReferenceById(dados.id());
		remedio.atualizarInformacoes(dados);
	}
	
	@DeleteMapping("/inativando/{id}")
	@Transactional
	public void Inativar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		remedio.excluir();
		
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public void Excluir (@PathVariable Long id) {
		repository.deleteById(id);
	}
	
	
	
}

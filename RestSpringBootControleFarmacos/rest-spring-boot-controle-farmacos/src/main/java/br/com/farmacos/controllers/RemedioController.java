package br.com.farmacos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.farmacos.remedio.DadosCadastroRemedio;
import br.com.farmacos.remedio.DadosListagemRemedios;
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
	public void Cadastrar(@RequestBody @Valid DadosCadastroRemedio dados) {
		repository.save(new Remedio(dados));
		
	}
	
	@GetMapping
	public Page<DadosListagemRemedios> listar (Pageable paginacao){
		return repository.findAll(paginacao).map(DadosListagemRemedios::new);
	}
	
}

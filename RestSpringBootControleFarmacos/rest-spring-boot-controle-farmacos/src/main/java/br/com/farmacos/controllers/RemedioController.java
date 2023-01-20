package br.com.farmacos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.farmacos.remedio.DadosAtualizacaoRemedio;
import br.com.farmacos.remedio.DadosCadastroRemedio;
import br.com.farmacos.remedio.DadosDetalhamentoRemedio;
import br.com.farmacos.remedio.DadosListagemRemedios;
import br.com.farmacos.remedio.Remedio;
import br.com.farmacos.remedio.RemedioRepository;
import br.com.farmacos.services.RemedioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("remedios")
public class RemedioController {

	@Autowired
	private RemedioRepository repository;
	@Autowired
	private RemedioService service;

	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroRemedio dados,
			UriComponentsBuilder uriBuilder) {
		var remedioExistente = repository.findByNome(dados.Getnome());
		if(remedioExistente != null) {
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		var remedio = new Remedio(dados);
		
		repository.save(remedio);

		var uri = uriBuilder.path("/remedios/{id}").buildAndExpand(remedio.getId()).toUri();

		return ResponseEntity.created(uri).body(new DadosDetalhamentoRemedio(remedio));

	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemRemedios>> listar(Pageable paginacao) {
		var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemRemedios::new);
		return ResponseEntity.ok(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoRemedio> buscarPorId(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<DadosDetalhamentoRemedio> buscarPorNome(@PathVariable String nome) {
		var remedio = repository.findByNome(nome);
		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoRemedio> atualizar(@RequestBody @Valid DadosAtualizacaoRemedio dados) {
		var remedio = repository.getReferenceById(dados.id());
		remedio.atualizarInformacoes(dados);
		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}

	@PutMapping("/removequantidade/{id}")
	@Transactional
	public ResponseEntity<?> retiradaDeEstoque(@PathVariable Long id, @RequestBody Integer quantidade) {
		if (quantidade == null) {
			return new ResponseEntity<>("Parâmetro quantidade é obrigatório", HttpStatus.BAD_REQUEST);
		}
		service.removerQuantidade(id, quantidade.intValue());
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/adicionaquantidade/{id}")
	@Transactional
	public ResponseEntity<?> AdicionaEstoque(@PathVariable long id, @RequestBody Integer quantidade) {
	    if (quantidade == null) {
	        return new ResponseEntity<>("Parâmetro quantidade é obrigatório", HttpStatus.BAD_REQUEST);
	    }
		service.adicionarQuantidade(id, quantidade.intValue());
		return ResponseEntity.noContent().build();
		
	}

	@PutMapping("/reativando/{id}")
	@Transactional
	public ResponseEntity<?> Reativar(@PathVariable Long id) {
		var remedio = repository.findById(id);
		remedio.get().setAtivo();
		repository.save(remedio.get());
		return ResponseEntity.ok(remedio.get());
	}

	@DeleteMapping("/inativando/{id}")
	@Transactional
	public ResponseEntity<?> Inativar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		remedio.setInativo();
		return ResponseEntity.noContent().build();

	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> Excluir(@PathVariable Long id) {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}

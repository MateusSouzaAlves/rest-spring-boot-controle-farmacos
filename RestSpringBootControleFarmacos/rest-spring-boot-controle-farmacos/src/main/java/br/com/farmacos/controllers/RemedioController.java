package br.com.farmacos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("remedios")
@Tag(name = "Remédios", description = "EndPoints para controle de remédios")
public class RemedioController {

	@Autowired
	private RemedioRepository repository;
	@Autowired
	private RemedioService service;

	@PostMapping
	@Transactional
	@Operation(summary = "Cadastre um remédio",
	description ="Cadastre um remédio", 
	tags = {"Remédios"},
	responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content(
					mediaType = "application/json")),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<DadosDetalhamentoRemedio> cadastrar(@RequestBody @Valid DadosCadastroRemedio dados,
			UriComponentsBuilder uriBuilder) {
		var remedioExistente = repository.findByNome(dados.Getnome());
		if (remedioExistente != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		var remedio = new Remedio(dados);

		repository.save(remedio);

		var uri = uriBuilder.path("/remedios/{id}").buildAndExpand(remedio.getId()).toUri();

		return ResponseEntity.created(uri).body(new DadosDetalhamentoRemedio(remedio));

	}

	@GetMapping
	@Operation(summary = "Busque todos os remédios cadrastados",
	description ="Busque todos os remédios cadrastados", 
	tags = {"Remédios"},
	
	responses = {
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = DadosListagemRemedios.class),
							examples = @ExampleObject())),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public List<DadosListagemRemedios> listar() {
		return repository.findAllByAtivoTrue().stream().map(DadosListagemRemedios::new).toList();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Busque por um detalhamento de um remédio por id",
	description ="Busque por um detalhamento de um remédio por id", 
	tags = {"Remédios"},
	responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content(
					mediaType = "application/json",
					schema = @Schema(implementation = DadosDetalhamentoRemedio.class),
					examples = @ExampleObject())),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "204",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<DadosDetalhamentoRemedio> buscarPorId(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}

	@GetMapping("/nome/{nome}")
	@Operation(summary = "Busque por um detalhamento de um remédio por nome",
	description ="Busque por um detalhamento de um remédio por nome", 
	tags = {"Remédios"},
			responses = {
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = DadosDetalhamentoRemedio.class),
							examples = @ExampleObject())),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "204",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<DadosDetalhamentoRemedio> buscarPorNome(@PathVariable String nome) {
		var remedio = repository.findByNome(nome);
		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}

	@PutMapping
	@Transactional
	@Operation(summary = "Atualize o cadastro de algum remédio",
	description ="Atualize o cadastro de algum remédio", 
	tags = {"Remédios"},
			responses = {
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content(
							mediaType = "application/json")),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<DadosDetalhamentoRemedio> atualizar(@RequestBody @Valid DadosAtualizacaoRemedio dados) {
		var remedio = repository.getReferenceById(dados.id());
		remedio.atualizarInformacoes(dados);
		return ResponseEntity.ok(new DadosDetalhamentoRemedio(remedio));
	}

	@PutMapping("/removequantidade/{id}")
	@Transactional
	@Operation(summary = "Remova um valor à quantidade em estoque",
	description ="Remova a quantidade em estoque", 
	tags = {"Remédios"},
	responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "204",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<?> retiradaDeEstoque(@PathVariable Long id, @RequestBody Integer quantidade) {
		service.validacaoQuantidade(quantidade);
		service.removerQuantidade(id, quantidade.intValue());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/adicionaquantidade/{id}")
	@Transactional
	@Operation(summary = "Adicione um valor à quantidade em estoque",
	description ="Adicione um valor à quantidade em estoque", 
	tags = {"Remédios"},
	responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "204",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<?> AdicionaEstoque(@PathVariable long id, @RequestBody Integer quantidade) {
		service.validacaoQuantidade(quantidade);
		service.adicionarQuantidade(id, quantidade.intValue());
		return ResponseEntity.noContent().build();

	}

	@PutMapping("/reativando/{id}")
	@Transactional
	@Operation(summary = "Reative o cadastro de um remédio pelo Id",
	description ="Reative o cadastro de um remédio pelo id", 
	tags = {"Remédios"},
	responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "204",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<?> Reativar(@PathVariable Long id) {
		var remedio = repository.findById(id);
		remedio.get().setAtivo();
		repository.save(remedio.get());
		return ResponseEntity.ok(remedio.get());
	}

	@DeleteMapping("/inativando/{id}")
	@Transactional
	@Operation(summary = "Desative o cadastro de um remédio pelo id",
	description ="Desative o cadastro de um remédio pelo id", 
	tags = {"Remédios"},
	responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "204",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<?> Inativar(@PathVariable Long id) {
		var remedio = repository.getReferenceById(id);
		remedio.setInativo();
		return ResponseEntity.noContent().build();

	}

	@DeleteMapping("/{id}")
	@Transactional
	@Operation(summary = "Exclua um remédio",
	description ="Exclua um remédio", 
	tags = {"Remédios"},
	responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "204",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<?> Excluir(@PathVariable Long id) {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}

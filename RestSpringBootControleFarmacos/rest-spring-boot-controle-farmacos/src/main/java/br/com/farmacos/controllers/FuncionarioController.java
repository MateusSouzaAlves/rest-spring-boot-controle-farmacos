package br.com.farmacos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.farmacos.funcionario.DadosAtualizacaoFuncionario;
import br.com.farmacos.funcionario.DadosCadastroFuncionario;
import br.com.farmacos.funcionario.DadosDetalhamentoFuncionario;
import br.com.farmacos.funcionario.DadosListagemFuncionarios;
import br.com.farmacos.funcionario.Funcionario;
import br.com.farmacos.funcionario.FuncionarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("funcionarios")
@Tag(name = "Funcionários", description = "EndPoints para controle de funcionários.")
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository repository;

	@PostMapping
	@Transactional
	@Operation(summary = "Cadastre um funcionário",
	description ="Cadastre um funcionário", 
	tags = {"Funcionários"},
			responses = {
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content(
							mediaType = "application/json")),
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
			})
	public ResponseEntity<DadosDetalhamentoFuncionario> cadastrar(@RequestBody @Valid DadosCadastroFuncionario dados,
			UriComponentsBuilder uriBuilder) {

		var funcionario = new Funcionario(dados);

		repository.save(funcionario);

		var uri = uriBuilder.path("/remedios/{id}").buildAndExpand(funcionario.getId()).toUri();

		return ResponseEntity.created(uri).body(new DadosDetalhamentoFuncionario(funcionario));
	}

	@GetMapping
	@Operation(summary = "Busque todos os funcionários cadrastados",
	description ="Busque todos os funcionários cadrastados", 
	tags = {"Funcionários"},
			responses = {
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = DadosListagemFuncionarios.class),
							examples = @ExampleObject())),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public List<DadosListagemFuncionarios> listar() {
		return repository.findAll().stream().map(DadosListagemFuncionarios::new).toList();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Busque por um detalhamento de um funcionário por id",
	description ="Busque por um detalhamento de um funcionário por id", 
	tags = {"Funcionários"},
			responses = {
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = DadosDetalhamentoFuncionario.class),
							examples = @ExampleObject())),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "204",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<DadosDetalhamentoFuncionario> buscarPorIdDetalhado(@PathVariable Long id) {
		var funcionario = repository.getReferenceById(id);
		return ResponseEntity.ok(new DadosDetalhamentoFuncionario(funcionario));
	}

	@GetMapping("/nome/{nome}")
	@Operation(summary = "Busque por um detalhamento de um funcionário por nome",
	description ="Busque por um detalhamento de um funcionário por nome", 
	tags = {"Funcionários"},
			responses = {
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = DadosDetalhamentoFuncionario.class),
							examples = @ExampleObject())),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "204",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<DadosDetalhamentoFuncionario> buscarPorNome(@PathVariable String nome) {
		var funcionario = repository.findByNome(nome);
		return ResponseEntity.ok(new DadosDetalhamentoFuncionario(funcionario));
	}

	@PutMapping
	@Transactional
	@Operation(summary = "Atualize o cadastro de algum funcionário",
	description ="Atualize o cadastro de algum funcionário", 
	tags = {"Funcionários"},
	responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<DadosDetalhamentoFuncionario> atualizar(
			@RequestBody @Valid DadosAtualizacaoFuncionario dados) {
		var funcionario = repository.getReferenceById(dados.id());
		funcionario.atualizarInformacoes(dados);
		return ResponseEntity.ok(new DadosDetalhamentoFuncionario(funcionario));
	}
	
	@PutMapping("/reativando/{id}")
	@Transactional
	@Operation(summary = "Reative o cadastro de um funcionário pelo Id",
	description ="Reative o cadastro de um funcionário pelo id", 
	tags = {"Funcionários"},
	responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "204",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<?> Reativar(@PathVariable Long id) {
		var funcionario = repository.findById(id);
		funcionario.get().setAtivo();
		repository.save(funcionario.get());
		return ResponseEntity.ok(funcionario.get());
	}
	
	@DeleteMapping("/inativando/{id}")
	@Transactional
	@Operation(summary = "Desative o cadastro de um funcionário pelo id",
	description ="Desative o cadastro de um funcionário pelo id", 
	tags = {"Funcionários"},
	responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "204",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<?> Inativar(@PathVariable Long id) {
		var funcionario = repository.getReferenceById(id);
		funcionario.setInativo();
		return ResponseEntity.noContent().build();

	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@Operation(summary = "Exclua um funcionário",
	description ="Exclua um funcionário", 
	tags = {"Funcionários"},
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

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
	public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroFuncionario dados,
			UriComponentsBuilder uriBuilder) {

		var funcionario = new Funcionario(dados);

		repository.save(funcionario);

		var uri = uriBuilder.path("/remedios/{id}").buildAndExpand(funcionario.getId()).toUri();

		return ResponseEntity.created(uri).body(new DadosDetalhamentoFuncionario(funcionario));
	}

	@GetMapping
	public List<DadosListagemFuncionarios> listar() {
		return repository.findAll().stream().map(DadosListagemFuncionarios::new).toList();
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoFuncionario> buscarPorIdDetalhado(@PathVariable Long id) {
		var funcionario = repository.getReferenceById(id);
		return ResponseEntity.ok(new DadosDetalhamentoFuncionario(funcionario));
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<DadosDetalhamentoFuncionario> buscarPorNome(@PathVariable String nome) {
		var funcionario = repository.findByNome(nome);
		return ResponseEntity.ok(new DadosDetalhamentoFuncionario(funcionario));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoFuncionario> atualizar(
			@RequestBody @Valid DadosAtualizacaoFuncionario dados) {
		var funcionario = repository.getReferenceById(dados.id());
		funcionario.atualizarInformacoes(dados);
		return ResponseEntity.ok(new DadosDetalhamentoFuncionario(funcionario));
	}
	
	@PutMapping("/reativando/{id}")
	@Transactional
	public ResponseEntity<?> Reativar(@PathVariable Long id) {
		var funcionario = repository.findById(id);
		funcionario.get().setAtivo();
		repository.save(funcionario.get());
		return ResponseEntity.ok(funcionario.get());
	}
	
	@DeleteMapping("/inativando/{id}")
	@Transactional
	public ResponseEntity<?> Inativar(@PathVariable Long id) {
		var funcionario = repository.getReferenceById(id);
		funcionario.setInativo();
		return ResponseEntity.noContent().build();

	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> Excluir(@PathVariable Long id) {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}

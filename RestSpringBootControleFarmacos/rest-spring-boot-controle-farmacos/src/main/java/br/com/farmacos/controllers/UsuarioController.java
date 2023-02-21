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

import br.com.farmacos.services.PasswordEncryptionService;
import br.com.farmacos.usuario.DadosAtualizacaoUsuario;
import br.com.farmacos.usuario.DadosCadastroUsuario;
import br.com.farmacos.usuario.DadosDetalhamentoUsuario;
import br.com.farmacos.usuario.DadosListagemUsuarios;
import br.com.farmacos.usuario.Usuario;
import br.com.farmacos.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("usuarios")
@Tag(name= "Usuários", description = "Endpoints para controle de usuários")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PasswordEncryptionService senha;
	
	@PostMapping
	@Transactional
	@Operation(summary = "Cadastre um usuário",
	description ="Cadastre um usuário", 
	tags = {"Usuários"},
			responses = {
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content(
							mediaType = "application/json")),
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
			})
	public ResponseEntity<DadosDetalhamentoUsuario> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, 
			UriComponentsBuilder uriBuilder){
		
		var usuario = new Usuario(dados);
		
		String senhaCriptografada = senha.encryptPassword(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		
		repository.save(usuario);
		
		var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
	}
	
	@GetMapping
	@Operation(summary = "Busque todos os usuários cadrastados",
	description ="Busque todos os usuários cadrastados", 
	tags = {"Usuários"},
	
	responses = {
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = DadosListagemUsuarios.class),
							examples = @ExampleObject())),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public List<br.com.farmacos.usuario.DadosListagemUsuarios> listar() {
		return repository.findAll().stream().map(DadosListagemUsuarios::new).toList();
	}
	@GetMapping("/{id}")
	@Operation(summary = "Busque por um detalhamento de um usuário por id",
	description ="Busque por um detalhamento de um usuário por id", 
	tags = {"Usuários"},
			responses = {
					@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = DadosDetalhamentoUsuario.class),
							examples = @ExampleObject())),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "204",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<DadosDetalhamentoUsuario> buscarPorIdDetalhado(@PathVariable Long id) {
		var usuario = repository.getReferenceById(id);
		return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
	}
	
	@PutMapping
	@Transactional
	@Operation(summary = "Atualize o cadastro de algum usuário",
	description ="Atualize o cadastro de algum usuário", 
	tags = {"Usuários"},
	responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<DadosDetalhamentoUsuario> atualizar(
			@RequestBody @Valid DadosAtualizacaoUsuario dados) {
		var usuario = repository.getReferenceById(dados.id());
		usuario.atualizarInformacoes(dados);
		return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
	}
	
	@PutMapping("/reativando/{id}")
	@Transactional
	@Operation(summary = "Reative o cadastro de um usuário pelo Id",
	description ="Reative o cadastro de um usuário pelo id", 
	tags = {"Usuários"},
	responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "204",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<?> Reativar(@PathVariable Long id) {
		var usuario = repository.findById(id);
		usuario.get().setAtivo();
		repository.save(usuario.get());
		return ResponseEntity.ok(usuario.get());
	}
	
	@DeleteMapping("/inativando/{id}")
	@Transactional
	@Operation(summary = "Desative o cadastro de um usuário pelo id",
	description ="Desative o cadastro de um usuário pelo id", 
	tags = {"Usuários"},
	responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "204",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "404",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	public ResponseEntity<?> Inativar(@PathVariable Long id) {
		var usuario = repository.getReferenceById(id);
		usuario.setInativo();
		return ResponseEntity.noContent().build();

	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@Operation(summary = "Exclua um usuário",
	description ="Exclua um usuário", 
	tags = {"Usuários"},
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

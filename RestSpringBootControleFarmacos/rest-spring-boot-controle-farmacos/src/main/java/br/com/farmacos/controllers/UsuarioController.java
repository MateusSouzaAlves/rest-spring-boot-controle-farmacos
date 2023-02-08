package br.com.farmacos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.farmacos.services.PasswordEncryptionService;
import br.com.farmacos.usuario.DadosCadastroUsuario;
import br.com.farmacos.usuario.DadosDetalhamentoUsuario;
import br.com.farmacos.usuario.Usuario;
import br.com.farmacos.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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

}

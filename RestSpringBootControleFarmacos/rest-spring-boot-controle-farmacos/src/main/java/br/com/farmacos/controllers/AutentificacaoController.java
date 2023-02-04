package br.com.farmacos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.farmacos.infra.security.TokenService;
import br.com.farmacos.infra.security.dadosTokenJWT;
import br.com.farmacos.usuario.DadosAutentificacao;
import br.com.farmacos.usuario.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
@Tag(name = "Login", description =  "Endpoint de controle de acesso ao token")
public class AutentificacaoController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	@Operation(summary = "Endpoint para login e validação de acesso",
	description = "Endpoint para login e validação de acesso",
	tags = {"Login"},
	responses = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "200",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "400",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "401",content = @Content),
			@io.swagger.v3.oas.annotations.responses.ApiResponse( responseCode = "500",content = @Content),
	})
	
	public ResponseEntity<dadosTokenJWT> EfetuarLogin(@RequestBody @Valid DadosAutentificacao dados) {
		var autentificacaoToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		var autentificacao = manager.authenticate(autentificacaoToken);
		var tokenJWT = tokenService.gerarToken((Usuario) autentificacao.getPrincipal());
		
		return ResponseEntity.ok(new dadosTokenJWT(tokenJWT));
	}
}

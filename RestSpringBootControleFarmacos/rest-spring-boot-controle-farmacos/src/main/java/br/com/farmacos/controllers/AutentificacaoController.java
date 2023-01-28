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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutentificacaoController {
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<dadosTokenJWT> EfetuarLogin(@RequestBody @Valid DadosAutentificacao dados) {
		var autentificacaoToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		var autentificacao = manager.authenticate(autentificacaoToken);
		var tokenJWT = tokenService.gerarToken((Usuario) autentificacao.getPrincipal());
		
		return ResponseEntity.ok(new dadosTokenJWT(tokenJWT));
	}
}

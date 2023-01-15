package br.com.farmacos.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.farmacos.remedio.DadosCadastroRemedio;


@RequestMapping("remedios")
@RestController
public class RemedioController {

	@PostMapping
	public void Cadastrar(@RequestBody DadosCadastroRemedio dados) {
		System.out.println(dados);
		
	}
	
}

package br.com.farmacos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.farmacos.funcionario.DadosCadastroFuncionario;
import br.com.farmacos.funcionario.Funcionario;
import br.com.farmacos.funcionario.FuncionarioRepository;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository repository;

	@PostMapping
	@Transactional
	public void cadastrarFuncionario(@RequestBody DadosCadastroFuncionario dados) {
		repository.save(new Funcionario(dados));
	}
}

package br.com.farmacos.services;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.auth0.jwt.interfaces.Payload;

import jakarta.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidationCPFUserExists.class)
public @interface CpfJaCadastrado{
	
	String message() default "Cpf já cadastrado";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] 	payload() default{};
}


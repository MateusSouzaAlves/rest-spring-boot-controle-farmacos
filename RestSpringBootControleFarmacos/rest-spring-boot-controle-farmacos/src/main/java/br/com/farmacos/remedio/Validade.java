package br.com.farmacos.remedio;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Validade {
//Como implementar esse método no validation Springboot?
	
	public boolean dataValidation (String data) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			sdf.parse(data);
			return true;
		} catch(ParseException ex) {
			return false;
		}
	}
}

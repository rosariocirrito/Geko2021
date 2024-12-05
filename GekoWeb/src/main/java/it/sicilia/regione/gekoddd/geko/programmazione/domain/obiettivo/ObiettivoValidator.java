package it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo;


import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

public class ObiettivoValidator {
	public void validate(Obiettivo obiettivo, Errors errors) {
		if (!StringUtils.hasLength(obiettivo.getCodice())) {
			errors.rejectValue("codice", "required", "required");
		}
		if (!StringUtils.hasLength(obiettivo.getDescrizione())) {
			errors.rejectValue("descrizione", "required", "required");
		}
                if (obiettivo.getPeso()<0 || obiettivo.getPeso()>100) {
			errors.rejectValue("peso", "peso compreso tra 0 e 100", "peso compreso tra 0 e 100");
		}
		//
	}
}

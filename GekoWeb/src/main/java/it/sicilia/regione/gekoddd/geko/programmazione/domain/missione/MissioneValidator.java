package it.sicilia.regione.gekoddd.geko.programmazione.domain.missione;


import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

public class MissioneValidator {
	public void validate(Missione missione, Errors errors) {
		if (!StringUtils.hasLength(missione.getCodice())) {
			errors.rejectValue("codice", "required", "required");
		}
		if (!StringUtils.hasLength(missione.getDescrizione())) {
			errors.rejectValue("descrizione", "required", "required");
		}
                
		//
	}
}

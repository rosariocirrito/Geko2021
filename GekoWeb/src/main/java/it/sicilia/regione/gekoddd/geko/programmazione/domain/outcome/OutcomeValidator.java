package it.sicilia.regione.gekoddd.geko.programmazione.domain.outcome;


import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 *
 * @author Rosario Cirrito
 */
public class OutcomeValidator {

	public void validate(Outcome outcome, Errors errors) {
		
		if (!StringUtils.hasLength(outcome.getDescrizione())) {
			errors.rejectValue("descrizione", "required", "required");
		}
               
                
	}

 
}
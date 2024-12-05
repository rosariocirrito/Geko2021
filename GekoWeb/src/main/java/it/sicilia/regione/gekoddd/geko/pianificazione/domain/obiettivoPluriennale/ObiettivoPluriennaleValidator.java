package it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoPluriennale;


import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 *
 * @author Rosario Cirrito
 */
public class ObiettivoPluriennaleValidator {

	public void validate(ObiettivoPluriennale obiettivoPluriennale, Errors errors) {
		
		if (!StringUtils.hasLength(obiettivoPluriennale.getDescrizione())) {
			errors.rejectValue("descrizione", "required", "required");
		}
                
                
	}

 
}
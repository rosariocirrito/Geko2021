package it.sicilia.regione.gekoddd.geko.pianificazione.domain.obiettivoStrategico;


import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 *
 * @author Rosario Cirrito
 */
public class ObiettivoStrategicoValidator {

	public void validate(ObiettivoStrategico obiettivoStrategico, Errors errors) {
		
		if (!StringUtils.hasLength(obiettivoStrategico.getDescrizione())) {
			errors.rejectValue("descrizione", "required", "required");
		}
                
                
	}

 
}
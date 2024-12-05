package it.sicilia.regione.gekoddd.geko.programmazione.domain.azione;


import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 *
 * @author Rosario Cirrito
 */
public class AzioneValidator {

	public void validate(Azione azione, Errors errors) {
		if (!StringUtils.hasLength(azione.getDenominazione())) {
			errors.rejectValue("denominazione", "required", "required");
		}
		if (!StringUtils.hasLength(azione.getDescrizione())) {
			errors.rejectValue("descrizione", "required", "required");
		}
                if (!StringUtils.hasLength(azione.getProdotti())) {
			errors.rejectValue("prodotti", "required", "required");
		}
                if (azione.getPeso()<0 || azione.getPeso()>100) {
			errors.rejectValue("peso", "peso compreso tra 0 e 100", "peso compreso tra 0 e 100");
		}
                
	}

 
}
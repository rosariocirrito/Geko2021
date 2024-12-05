package it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut;


import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 *
 * @author rc
 */
public class CriticValutValidator {

	public void validate(CriticValut criticita, Errors errors) {
            if (!StringUtils.hasLength(criticita.getDescrizione())) {
			errors.rejectValue("descrizione", "required", "required");
		} /*
            if (!StringUtils.hasLength(criticita.getProposte())) {
			errors.rejectValue("proposte", "required", "required");
		}
            if (!StringUtils.hasLength(criticita.getIndicazioni())) {
			errors.rejectValue("indicazioni", "required", "required");
		}
            // da completare
           
		if (!StringUtils.hasLength(azione.getDenominazione())) {
			errors.rejectValue("denominazione", "required", "required");
		}
		
                if (!StringUtils.hasLength(azione.getProdotti())) {
			errors.rejectValue("prodotti", "required", "required");
		}
                * 
                */
                
	}

 
}
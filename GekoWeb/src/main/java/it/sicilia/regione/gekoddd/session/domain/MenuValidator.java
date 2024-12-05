package it.sicilia.regione.gekoddd.session.domain;


import org.springframework.validation.Errors;

/**
 * <code>Validator</code> for <code>Pet</code> forms.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 */
public class MenuValidator {

	public void validate(Menu menu, Errors errors) {
            if (menu.getAnno() <2012 ) {
			errors.rejectValue("anno" , "to low", "required");
		}
            
            /*
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
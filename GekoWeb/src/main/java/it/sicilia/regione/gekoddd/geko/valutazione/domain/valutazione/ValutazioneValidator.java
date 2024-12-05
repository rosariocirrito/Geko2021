package it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione;


import org.springframework.validation.Errors;

public class ValutazioneValidator {

	public void validate(Valutazione valutazione, Errors errors, int anno) {
		/*
		if (anno > 2018){
			int c1 = valutazione.getPdlAss();
			int c2 = valutazione.getAnalProgrAss();
			int c3 = valutazione.getRelazCoordAss();
			int c4 = valutazione.getGestRealAss();
			if (!(c1==0 || (c1>=5 && c1<=20))) {
				errors.rejectValue("Capacita' di individuazione ...", "deve essere 0 o compreso tra 5 e 20", "deve essere 0 o compreso tra 5 e 20");
			}
			if (!(c2==0 || (c2>=5 && c2<=20))) {
				errors.rejectValue("Promozione di strumenti di analisi ...", "deve essere 0 o compreso tra 5 e 20", "deve essere 0 o compreso tra 5 e 20");
			}
			if (!(c3==0 || (c3>=5 && c3<=20))) {
				errors.rejectValue("Capacita' di valorizzare ...", "deve essere 0 o compreso tra 5 e 20", "deve essere 0 o compreso tra 5 e 20");
			}
			if (!(c4==0 || (c4>=5 && c4<=20))) {
				errors.rejectValue("Capacita' di intercettare ...",  "deve essere 0 o compreso tra 5 e 20", "deve essere 0 o compreso tra 5 e 20");
			}
		}       
		*/         
	}
	
}
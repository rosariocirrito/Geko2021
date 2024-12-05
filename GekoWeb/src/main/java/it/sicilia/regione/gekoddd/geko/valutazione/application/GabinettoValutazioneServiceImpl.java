package it.sicilia.regione.gekoddd.geko.valutazione.application;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut.CriticValut;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut.CriticValutCmdService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneCmdService;
import it.sicilia.regione.gekoddd.log.model.journal.Journal;
import it.sicilia.regione.gekoddd.log.model.journal.JournalService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Cirrito
 */
@Service("GabinettoValutazioneService")
public class GabinettoValutazioneServiceImpl implements GabinettoValutazioneService{

	private Log log = LogFactory.getLog(GabinettoValutazioneServiceImpl.class);
	
	@Autowired
    private ValutazioneCmdService valutazioneCmdServizi;
	@Autowired
    private CriticValutCmdService critValutCmdServizi;
    @Autowired
    private JournalService journalServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
// ------- Casi d'uso del controller -----------------------------------------
    
    
   
   
    
    // aggiorna il campo stato di valutazione
    // 
   
    
    
    // -----------------------------------------------------------------------
    @Transactional
    @Secured({"ROLE_GABINETTO"})
    public void gabinettoCreateValutazioneCompOrg(Valutazione valutazione) {
    	
    		valutazioneCmdServizi.save(valutazione);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("crea Valutazione con id: "+valutazione.getId());
	        		
	        journal.setDove("Valutazione");
	        journal.setIdObj(valutazione.getId());
	        journal.setPerche("gabinettoCreateValutazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    	
    }
    @Transactional
    @Secured({"ROLE_GABINETTO"})
    public void gabinettoUpdateValutazioneCompOrg(Valutazione valutazione) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(valutazione.getIncaricoID());
    	//final Integer dipartimentoID = (fromOrganikoServizi.findDipartimentoByPersonaFisicaID(incarico.pfID)).idPersona;;
    	final PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
    	final PersonaGiuridicaGeko pg = fromOrganikoServizi.findPersonaGiuridicaById(incarico.pgID);
        //if (menu.getDipartimento().idPersona==dipartimentoID){
    		valutazioneCmdServizi.save(valutazione);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("update Valutazione con id: "+valutazione.getId()+ 
	        		", pf: "+pf.getCognomeNome()+
	        		", pg: "+pg.getDenominazione());
	        journal.setDove("Valutazione");
	        journal.setIdObj(valutazione.getId());
	        journal.setPerche("gabinettoUpdateValutazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    	//}
    }
   
    
    
} // ---------------------------------------------------------------------------------

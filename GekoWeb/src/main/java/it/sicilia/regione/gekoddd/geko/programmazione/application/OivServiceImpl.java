/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.sicilia.regione.gekoddd.geko.programmazione.application;


import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettivi;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.associazObiettivi.AssociazObiettiviCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.AzioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione.CompletamentoEnum;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazioneCmdService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.Obiettivo;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo.ObiettivoCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.Criticita;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.criticita.CriticitaCmdService;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;
import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.DocumentoCmdService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut.CriticValut;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.criticValut.CriticValutCmdService;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.Valutazione;
import it.sicilia.regione.gekoddd.geko.valutazione.domain.valutazione.ValutazioneCmdService;
import it.sicilia.regione.gekoddd.log.model.journal.Journal;
import it.sicilia.regione.gekoddd.log.model.journal.JournalService;
import it.sicilia.regione.gekoddd.session.domain.Menu;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Cirrito
 */
@Service("oivService")
public class OivServiceImpl implements OivService{

	private Log log = LogFactory.getLog(OivServiceImpl.class);
	
	@Autowired
    private ObiettivoCmdService objCmdServizi;
	
	@Autowired
    private AzioneCmdService actCmdServizi;
	@Autowired
    private AzioneAssegnazioneCmdService actAssCmdServizi;
	@Autowired
    private CriticitaCmdService critCmdServizi;
	@Autowired
    private ValutazioneCmdService valutazioneCmdServizi;
	@Autowired
    private CriticValutCmdService critValutCmdServizi;
    @Autowired
    private AssociazObiettiviCmdService associazObiettiviServizi;
    @Autowired
    private JournalService journalServizi;
    @Autowired
    private DocumentoCmdService docCmdServizi;
    
    @Autowired
    private FromOrganikoQryService fromOrganikoServizi;
    @Autowired
    private Menu menu;
    
// ------- Casi d'uso del sepicos -----------------------------------------
    
    // 1. Crea Obiettivo (con azione di default)
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosProponeObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID){
            objCmdServizi.save(obiettivo);
            //
        	Azione defaultAzione = new Azione();
        	defaultAzione.setObiettivo(obiettivo);
        	defaultAzione.setDenominazione(obiettivo.getCodice()+"_");
        	defaultAzione.setDescrizione("azione di default per "+obiettivo.getCodice());
        	defaultAzione.setPeso(0);
        	defaultAzione.setProdotti("?");
        	defaultAzione.setCompletamento(CompletamentoEnum.DA_VALUTARE);
        	actCmdServizi.save(defaultAzione);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("add Obiettivo proposto con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("sepicosProponiObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        }
    }
    
    // 1.b Richiede Obiettivo 
    // richiedi obiettivo solo se � nello stato Interlocutorio
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosRichiedeObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID && 
            obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO) 
            ){
        	obiettivo.setStatoApprov(Obiettivo.StatoApprovEnum.RICHIESTO);
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("richiede Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("sepicosRichiedeObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        }  
    }
    
    // 2. Modifica Obiettivo 
    // aggiorna obiettivo solo se � nello stato INTERLOCUTORIO, PROPOSTO o RICHIESTO
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosUpdateObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID){
        	objCmdServizi.save(obiettivo);
        	Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Update Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("sepicosUpdateObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        }  
    }
    
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosUpdateIncaricoObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID){
        	objCmdServizi.save(obiettivo);
        	Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Update Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("sepicosUpdateIncaricoObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        }  
    }
    
 // 2. Modifica Obiettivo 
    // aggiorna obiettivo solo se � nello stato INTERLOCUTORIO, PROPOSTO o RICHIESTO
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosUpdateObiettivoApicale(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID){
        	objCmdServizi.save(obiettivo);
        	Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Update Obiettivo Apicale con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("sepicosUpdateObiettivoApicale");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        }  
    }
    // 3. Cancella Obiettivo 
    // aggiorna obiettivo solo se � nello stato INTERLOCUTORIO o RICHIESTO
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosDeleteObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID && (
        		obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO) ||
        		obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.RICHIESTO)
        		)
            ){
        	objCmdServizi.delete(obiettivo);
        	Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("delete Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("sepicosDeleteObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        }  
    }
    
    // 4. Valida Obiettivo 
    // valida obiettivo solo se � nello stato PROPOSTO
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosValidaObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID){
        	obiettivo.setStatoApprov(Obiettivo.StatoApprovEnum.VALIDATO);
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("valida Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("sepicosValidaObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        }  
    }
    
    // 5. Rivedi Obiettivo 
    // valida obiettivo solo se � nello stato PROPOSTO
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosRivedeObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID && 
            obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.PROPOSTO) 
            ){
        	obiettivo.setStatoApprov(Obiettivo.StatoApprovEnum.RIVISTO);
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("rivedi Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("sepicosRivediObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        }  
    }
    
    // 6. Respingi Obiettivo 
    // valida obiettivo solo se � nello stato PROPOSTO
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosRespingeObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID && 
            obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.PROPOSTO) 
            ){
        	obiettivo.setStatoApprov(Obiettivo.StatoApprovEnum.RESPINTO);
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("respingi Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("sepicosRespingiObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        }  
    }
    
    // 7. Annulla Obiettivo 
    // valida obiettivo solo se � nello stato DEFINITIVO
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosAnnullaObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID && 
            obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.DEFINITIVO) 
            ){
        	obiettivo.setStatoApprov(Obiettivo.StatoApprovEnum.ANNULLATO);
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("annulla Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("sepicosAnnullaObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        }  
    }
    
    // aggiorna il campo stato di realizzazione
    // 
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosRealizzaObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID && 
            obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.DEFINITIVO) ){
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("aggiorna stato realizzazione di Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("sepicosRealizzaObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        }  
    }
    
    // aggiorna il campo stato di valutazione
    // 
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosValutaObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID && 
            obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.DEFINITIVO) ){
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("aggiorna stato valutazione Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("sepicosValutaObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        }  
    }
    
    public void sepicosRendiApicaleObiettivo(Obiettivo obiettivo) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(obiettivo.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID && 
            obiettivo.getStatoApprov().equals(Obiettivo.StatoApprovEnum.DEFINITIVO) ){
            objCmdServizi.save(obiettivo);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("rendi apicale Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("sepicosRendiApicaleObiettivo");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        }  
		
	}

    
    // -----------------------------------------------
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosCreateAzione(Azione azione) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azione.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID &&  (
        		azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO) ||
        		azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.RICHIESTO)
        		)){
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("crea Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("sepicosCreateAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }

    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosUpdateAzione(Azione azione) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azione.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID && (
    			azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO) ||
        		azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.RICHIESTO) ||
        		azione.getObiettivo().isApicale()
        		)){
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Update Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("sepicosUpdateAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }

    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosDeleteAzione(Azione azione) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azione.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID && (
    			azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO) ||
        		azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.RICHIESTO)
        		)){
        	actCmdServizi.delete(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Delete Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("sepicosDeleteAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }

    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosUpdateCompletamentoAzione(Azione azione) {
    	
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Completamento Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("sepicosUpdateCompletamentoAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
    
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosUpdatePesoAzione(Azione azione) {
    	
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Aggiornamento Peso Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("sepicosUpdatePesoAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
    // -------------------------------------------------------------------------------
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosUpdatePesoApicaleAzione(Azione azione) {
    	   	
        	actCmdServizi.save(azione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Aggiornamento Peso Apicale Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("sepicosUpdatePesoApicaleAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
    
    
    
    
    // -------------------------------------------------------------------------------
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosCreateCriticita(Criticita criticita) {
    	
        	critCmdServizi.save(criticita);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("crea Criticita con id: "+criticita.getId()+ ", "+criticita.getDescrizione());
	        journal.setDove("Criticita");
	        journal.setIdObj(criticita.getId());
	        journal.setPerche("sepicosCreateCriticita");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosUpdateCriticita(Criticita criticita) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(criticita.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID &&
                criticita.getAzione().getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.DEFINITIVO)
            	){
    		critCmdServizi.save(criticita);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("update Criticita con id: "+criticita.getId()+ ", "+criticita.getDescrizione());
	        journal.setDove("Criticita");
	        journal.setIdObj(criticita.getId());
	        journal.setPerche("sepicosUpdateCriticita");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosDeleteCriticita(Criticita criticita) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(criticita.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID) {
    		critCmdServizi.delete(criticita);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("delete Criticita con id: "+criticita.getId()+ ", "+criticita.getDescrizione());
	        journal.setDove("Criticita");
	        journal.setIdObj(criticita.getId());
	        journal.setPerche("sepicosDeleteCriticita");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        
        }
    }
    
 
 // -------------------------------------------------------------------------------
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosCreateCriticValut(CriticValut criticita) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(criticita.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID){
    		critValutCmdServizi.save(criticita);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("crea CriticValut con id: "+criticita.getId()+ ", "+criticita.getDescrizione());
	        journal.setDove("CriticValut");
	        journal.setIdObj(criticita.getId());
	        journal.setPerche("sepicosCreateCriticValut");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }
    
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosUpdateCriticValut(CriticValut criticita) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(criticita.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID){
    		critValutCmdServizi.save(criticita);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("update CriticValut con id: "+criticita.getId()+ ", "+criticita.getDescrizione());
	        journal.setDove("CriticValut");
	        journal.setIdObj(criticita.getId());
	        journal.setPerche("sepicosUpdateCriticValut");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        }
    }
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosDeleteCriticValut(CriticValut criticita) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(criticita.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID){
    		critValutCmdServizi.delete(criticita);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("delete Criticita con id: "+criticita.getId()+ ", "+criticita.getDescrizione());
	        journal.setDove("CriticValut");
	        journal.setIdObj(criticita.getId());
	        journal.setPerche("sepicosDeleteCriticValut");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
	        
        }
    }
    

    // --------------------- AssociazObiettivi -----------------------
    //
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosCreateAssociazObiettivi(AssociazObiettivi associazObiettivi) {
        
        	associazObiettiviServizi.save(associazObiettivi);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Create AssociazObiettivi con id: "+associazObiettivi.getId()
	        		);
	        journal.setDove("AssociazObiettivi");
	        journal.setIdObj(associazObiettivi.getId());
	        journal.setPerche("sepicosCreateAssociazObiettivi");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }

    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosUpdateAssociazObiettivi(AssociazObiettivi associazObiettivi) {
        
        	associazObiettiviServizi.save(associazObiettivi);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Update AssociazObiettivi con id: "+associazObiettivi.getId()
	        		);
	        journal.setDove("AssociazObiettivi");
	        journal.setIdObj(associazObiettivi.getId());
	        journal.setPerche("sepicosUpdateAssociazObiettivi");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }

    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosDeleteAssociazObiettivi(AssociazObiettivi associazObiettivi) {
    	
    		associazObiettiviServizi.delete(associazObiettivi);
        
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Delete AssociazObiettivi con id: "+associazObiettivi.getId()
	        		);
	        journal.setDove("AssociazObiettivi");
	        journal.setIdObj(associazObiettivi.getId());
	        journal.setPerche("sepicosDeleteAssociazObiettivi");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
        
    }

	public void sepicosCreaObiettivoApicale(Obiettivo obiettivo) {
		//final OpPersonaGiuridica dipartimento = userServizi.findDipartimentoOfLoggedUser();
    	//final OpPersonaGiuridica dip2 = obiettivo.getDipartimento();
        //if (dip2.equals(dipartimento)){
            objCmdServizi.save(obiettivo);
            //
        	Azione defaultAzione = new Azione();
        	defaultAzione.setObiettivo(obiettivo);
        	defaultAzione.setDenominazione(obiettivo.getCodice()+"_");
        	defaultAzione.setDescrizione("azione di default per "+obiettivo.getCodice());
        	defaultAzione.setPeso(0);
        	defaultAzione.setProdotti("?");
        	defaultAzione.setCompletamento(CompletamentoEnum.DA_VALUTARE);
        	actCmdServizi.save(defaultAzione);
            Journal journal = new Journal();
            journal.setChi(menu.getChi());
            journal.setCosa("Crea Obiettivo Apicale con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
            journal.setDove("Obiettivo");
            journal.setIdObj(obiettivo.getIdObiettivo());
            journal.setPerche("sepicosCreaObiettivoApicale");
            journal.setQuando(new Date());
            journalServizi.save(journal);
        //}
		
	}
	
	// --------------- AzioneAssegnazione --------------------------------
    //
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosCreateAzioneAssegnazione(AzioneAssegnazione azioneAssegnazione) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azioneAssegnazione.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
    	final PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(incarico.pfID);
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID		
        		&& (
        		azioneAssegnazione.getAzione().getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.INTERLOCUTORIO) ||
        		azioneAssegnazione.getAzione().getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.RICHIESTO)
        		)
        	)
        	actAssCmdServizi.save(azioneAssegnazione);
	        Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("Create AzioneAssegnazione con id: "+azioneAssegnazione.getId()+ 
	        		", "+azioneAssegnazione.getAzione().getDenominazione()+
	        		", pf = "+pf.getCognomeNome()
	        		);
	        journal.setDove("AzioneAssegnazione");
	        journal.setIdObj(azioneAssegnazione.getId());
	        journal.setPerche("sepicosCreateAzioneAssegnazione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    }

    
    
    
    @Transactional
    //@Secured({"ROLE_SEPICOS"})
    public void sepicosUpdateRisultatoApicaleAzione(Azione azione) {
    	final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(azione.getIncaricoID());
    	final Integer dipartimentoID = incarico.deptID;
        if (menu.getIdIncaricoDeptScelta()==dipartimentoID	 
    			&&
    		azione.getObiettivo().getStatoApprov().equals(Obiettivo.StatoApprovEnum.DEFINITIVO))
    		
            //System.out.println("ManagerServiceImpl.sepicosUpdateRisultatoAzione() id azione="+azione.getIdAzione());
        	actCmdServizi.save(azione);
	    	Journal journal = new Journal();
	        journal.setChi(menu.getChi());
	        journal.setCosa("UpdateRisultato Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
	        journal.setDove("Azione");
	        journal.setIdObj(azione.getIdAzione());
	        journal.setPerche("sepicosUpdateRisultatoAzione");
	        journal.setQuando(new Date());
	        journalServizi.save(journal);
    }

    @Transactional
    //@Secured({"ROLE_SEPICOS"})
	public void sepicosClonaObiettivo(Obiettivo obiettivo) {
		log.info("Cloning existing Obiettivo: "+ obiettivo.getDescrizione());
		 //
        objCmdServizi.save(obiettivo);
        //
    	Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Clona Obiettivo con id: "+obiettivo.getIdObiettivo()+ ", "+obiettivo.getDescrizione());
        journal.setDove("Obiettivo");
        journal.setIdObj(obiettivo.getIdObiettivo());
        journal.setPerche("sepicosClonaObiettivo");
        journal.setQuando(new Date());
        journalServizi.save(journal);
		
	}

    @Transactional
    //@Secured({"ROLE_SEPICOS"})
	public void sepicosClonaAzione(Azione azione) {
		log.info("Cloning existing Azione: "+azione.getDescrizione());
		actCmdServizi.save(azione);
        Journal journal = new Journal();
        journal.setChi(menu.getChi());
        journal.setCosa("Clona Azione con id: "+azione.getIdAzione()+ ", "+azione.getDescrizione());
        journal.setDove("Azione");
        journal.setIdObj(azione.getIdAzione());
        journal.setPerche("sepicosClonaAzione");
        journal.setQuando(new Date());
        journalServizi.save(journal);
		
	}

    
    
    
    
} // ---------------------------------------------------------------------------------

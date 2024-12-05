package it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.sicilia.regione.gekoddd.geko.acl.FromOrganikoQryService;
import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azione.Azione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.azioneAssegnazione.AzioneAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.obiettivoAssegnazione.ObiettivoAssegnazioneQryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("obiettivoQryService")
@Repository
@Transactional
public class ObiettivoQryServiceImpl implements ObiettivoQryService{
	

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private ObiettivoRepository repo;	

	@Autowired
    private FromOrganikoQryService fromOrganikoServizi;
	@Autowired
    private ObiettivoAssegnazioneQryService objAssServizi;
    
	
// ---------------------------------------------------------------------------------------------------------
	

	// --------- CQRS ---------------

	public List<Obiettivo> findObiettiviTotaliByIncaricoIDAndAnno(Integer incaricoID, int anno) {
		List<Obiettivo> listaObj = repo.findByIncaricoIDAndAnno(incaricoID, anno);
		PropertyComparator.sort(listaObj, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
		return listaObj;
	}
	
	public List<Obiettivo> findObiettiviPrioritariByIncaricoIDAndAnno(Integer incaricoID, int anno) {
		List<Obiettivo> listaObj = repo.findByIncaricoIDAndAnno(incaricoID, anno);
		List<Obiettivo> listObiettiviPrio = new ArrayList<Obiettivo>();
    	for(Obiettivo obj : listaObj){
    		if (obj.getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)) listObiettiviPrio.add(obj);
    	}
		PropertyComparator.sort(listObiettiviPrio, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
		return listObiettiviPrio;
	}
	
	//
	public List<Obiettivo> findObiettiviTotaliConPersoneByIncaricoIDAndAnno(Integer incaricoID, int anno) {
		List<Obiettivo> listaObj = repo.findByIncaricoIDAndAnno(incaricoID, anno);
		for (Obiettivo obj : listaObj){
    		
    		List<ObiettivoAssegnazione> lstAssegnazioni = objAssServizi.findByIdObiettivo(obj.getIdObiettivo());
    		if (!lstAssegnazioni.isEmpty()){
    			for(ObiettivoAssegnazione objAss :lstAssegnazioni){
    				PersonaFisicaGeko dip = fromOrganikoServizi.findPersonaFisicaById(objAss.getIdPersona());	
    				objAss.setOpPersonaFisica(dip);
    			}
    			PropertyComparator.sort(lstAssegnazioni, new MutableSortDefinition("opPersonaFisica.stringa",true,true));
    		}
    		obj.setAssegnazioni(lstAssegnazioni);
			
			for(Azione act : obj.getAzioni()){
				for(AzioneAssegnazione assegn : act.getAssegnazioni()){
					PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(assegn.getPfID());
					if (pf != null) assegn.setOpPersonaFisica(pf);
				}
			}
		}
		PropertyComparator.sort(listaObj, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
		return listaObj;
		
	}
	
	// //
	public List<Obiettivo> findObiettiviDirigenzialiConPersoneByIncaricoIDAndAnno(Integer incaricoID, int anno) {
		List<Obiettivo> listaObj = repo.findByIncaricoIDAndAnno(incaricoID, anno);
		List<Obiettivo> listObiettiviPrio = new ArrayList<Obiettivo>();
    	for(Obiettivo obj : listaObj){
    		if (obj.getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)) listObiettiviPrio.add(obj);
    	}
		PropertyComparator.sort(listObiettiviPrio, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
		//
		for (Obiettivo obj : listObiettiviPrio){
    		
    		List<ObiettivoAssegnazione> lstAssegnazioni = objAssServizi.findByIdObiettivo(obj.getIdObiettivo());
    		if (!lstAssegnazioni.isEmpty()){
    			for(ObiettivoAssegnazione objAss :lstAssegnazioni){
    				PersonaFisicaGeko dip = fromOrganikoServizi.findPersonaFisicaById(objAss.getIdPersona());	
    				objAss.setOpPersonaFisica(dip);
    			}
    			PropertyComparator.sort(lstAssegnazioni, new MutableSortDefinition("opPersonaFisica.stringa",true,true));
    		}
    		obj.setAssegnazioni(lstAssegnazioni);
			
			for(Azione act : obj.getAzioni()){
				for(AzioneAssegnazione assegn : act.getAssegnazioni()){
					if (assegn.getPfID()>0){
						PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(assegn.getPfID());
						if (pf != null) assegn.setOpPersonaFisica(pf);
					}
				}
			}
		}
		PropertyComparator.sort(listObiettiviPrio, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
		return listObiettiviPrio;
		
	}
	
	// //
		public List<Obiettivo> findObiettiviDirigenzialiSenzaPersoneByIncaricoIDAndAnno(Integer incaricoID, int anno) {
			List<Obiettivo> listaObj = repo.findByIncaricoIDAndAnno(incaricoID, anno);
			List<Obiettivo> listObiettiviPrio = new ArrayList<Obiettivo>();
	    	for(Obiettivo obj : listaObj){
	    		if (obj.getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)) listObiettiviPrio.add(obj);
	    	}
			//
			
			PropertyComparator.sort(listObiettiviPrio, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
			return listObiettiviPrio;
			
		}
		
	
	//
	public List<Obiettivo> findObiettiviCompartoConPersoneByIncaricoIDAndAnno(Integer incaricoID, int anno) {
		List<Obiettivo> listaObj = repo.findByIncaricoIDAndAnno(incaricoID, anno);
		List<Obiettivo> listObiettiviComparto = new ArrayList<Obiettivo>();
    	for(Obiettivo obj : listaObj){
    		if (!obj.getTipo().equals(Obiettivo.TipoEnum.DIRIGENZIALE)) listObiettiviComparto.add(obj);
    	}
		for (Obiettivo obj : listObiettiviComparto){
    		
    		List<ObiettivoAssegnazione> lstAssegnazioni = objAssServizi.findByIdObiettivo(obj.getIdObiettivo());
    		if (!lstAssegnazioni.isEmpty()){
    			for(ObiettivoAssegnazione objAss :lstAssegnazioni){
    				PersonaFisicaGeko dip = fromOrganikoServizi.findPersonaFisicaById(objAss.getIdPersona());	
    				objAss.setOpPersonaFisica(dip);
    			}
    			PropertyComparator.sort(lstAssegnazioni, new MutableSortDefinition("opPersonaFisica.stringa",true,true));
    		}
    		obj.setAssegnazioni(lstAssegnazioni);
			
			for(Azione act : obj.getAzioni()){
				for(AzioneAssegnazione assegn : act.getAssegnazioni()){
					PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(assegn.getPfID());
					if (pf != null) assegn.setOpPersonaFisica(pf);
				}
			}
		}
		PropertyComparator.sort(listObiettiviComparto, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
		return listObiettiviComparto;
		
	}
	
	//
	public List<Obiettivo> findObiettiviDirettiByIncaricoIDAndAnno(Integer incaricoID, int anno) {
		List<Obiettivo> listObiettivi = repo.findByIncaricoIDAndAnno(incaricoID, anno);
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(incaricoID);
		for (Obiettivo obj : listObiettivi){
    		IncaricoGeko incaricoGeko = fromOrganikoServizi.findIncaricoById(obj.getIncaricoID());
    		obj.setIncarico(incarico);
    	}
		PropertyComparator.sort(listObiettivi, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
		return listObiettivi;
	}
	public List<Obiettivo> findObiettiviApicaliDirettiByIncaricoIDAndAnno(Integer incaricoID, int anno) {
		List<Obiettivo> listObiettiviApicali = repo.findByIncaricoIDAndAnnoAndApicale(incaricoID, anno, true);
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(incaricoID);
		for (Obiettivo obj : listObiettiviApicali){
    		IncaricoGeko incaricoGeko = fromOrganikoServizi.findIncaricoById(obj.getIncaricoID());
    		obj.setIncarico(incarico);
    	}
		PropertyComparator.sort(listObiettiviApicali, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
		return listObiettiviApicali;
	}
	
	public List<Obiettivo> findObiettiviSubordinatiByIncaricoIDAndAnno(Integer incaricoID, int anno) {
		List<Obiettivo> listObiettivi = new ArrayList<Obiettivo>();
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(incaricoID);
		List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(incarico.pgID, anno);
		for(IncaricoGeko inc : listIncarichiDept){
        	List<Obiettivo> lstObjs = repo.findByIncaricoIDAndAnno(inc.idIncarico, anno);
        	for (Obiettivo obj : lstObjs){
        		obj.setIncarico(inc);
        	}
        	listObiettivi.addAll(lstObjs);
        }
		PropertyComparator.sort(listObiettivi, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
		return listObiettivi;
	}
	public List<Obiettivo> findObiettiviApicaliSubordinatiByIncaricoIDAndAnno(Integer incaricoID, int anno) {
		List<Obiettivo> listObiettiviApicali = new ArrayList<Obiettivo>();
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(incaricoID);
		List<IncaricoGeko> listIncarichiDept = fromOrganikoServizi.findIncarichiByDipartimentoIDAndAnno(incarico.pgID, anno);
		if (null!=listIncarichiDept && !listIncarichiDept.isEmpty()){
		for(IncaricoGeko inc : listIncarichiDept){
        	List<Obiettivo> lstObjs = repo.findByIncaricoIDAndAnnoAndApicale(inc.idIncarico, anno,true);
        	for (Obiettivo obj : lstObjs){
        		obj.setIncarico(inc);
        	}
        	listObiettiviApicali.addAll(lstObjs);
        }
		}
		PropertyComparator.sort(listObiettiviApicali, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
		return listObiettiviApicali;
	}

	public List<Obiettivo> findObiettiviDirettiAndSubordinatiByIncaricoIDAndAnno(Integer incaricoID, int anno) {
		List<Obiettivo> listObiettivi = new ArrayList<Obiettivo>();
		listObiettivi.addAll(this.findObiettiviDirettiByIncaricoIDAndAnno(incaricoID, anno));
		listObiettivi.addAll(this.findObiettiviSubordinatiByIncaricoIDAndAnno(incaricoID, anno));
		PropertyComparator.sort(listObiettivi, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
		return listObiettivi;
	}
	public List<Obiettivo> findObiettiviApicaliDirettiAndSubordinatiByIncaricoIDAndAnno(Integer incaricoID, int anno) {
		List<Obiettivo> listObiettiviApicali = new ArrayList<Obiettivo>();
		listObiettiviApicali.addAll(this.findObiettiviApicaliDirettiByIncaricoIDAndAnno(incaricoID, anno));
		listObiettiviApicali.addAll(this.findObiettiviApicaliSubordinatiByIncaricoIDAndAnno(incaricoID, anno));
		PropertyComparator.sort(listObiettiviApicali, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
		return listObiettiviApicali;
	}
	
	public Map<IncaricoGeko, List<Obiettivo>> mapObiettiviDipartimentoIDAndAnno(Integer dipartimentoID, int anno) {
		PersonaGiuridicaGeko dipartimento = fromOrganikoServizi.findPersonaGiuridicaById(dipartimentoID);
		Map<IncaricoGeko,List<Obiettivo>> mapObjDept = new TreeMap(new Comparator<IncaricoGeko>() {
        	public int compare(IncaricoGeko a, IncaricoGeko b)
            {
        		PersonaGiuridicaGeko struA = fromOrganikoServizi.findPersonaGiuridicaById(a.pgID);
        		PersonaFisicaGeko dirigA = fromOrganikoServizi.findPersonaFisicaById(a.pfID);
        		PersonaGiuridicaGeko struB = fromOrganikoServizi.findPersonaGiuridicaById(b.pgID);
        		PersonaFisicaGeko dirigB = fromOrganikoServizi.findPersonaFisicaById(b.pfID);
                String nomeA = struA.getDenominazione()+dirigA.getCognomeNome();
                String nomeB = struB.getDenominazione()+dirigB.getCognomeNome();
                return nomeA.compareTo(nomeB);
            }
        });
		// obiettivi diretti
		List<IncaricoGeko> incarichiApicali = fromOrganikoServizi.findIncarichiApicaliByDipartimentoIDAndAnno(dipartimentoID, anno);
		for(IncaricoGeko incarico : incarichiApicali){
			mapObjDept.put(incarico, this.findObiettiviTotaliByIncaricoIDAndAnno(incarico.idIncarico, anno));//incarico.getObiettiviTotAnno());
		}
		
		//PropertyComparator.sort(lstStruct, new MutableSortDefinition("order",true,true));
		// obiettivi della strutture intermedie
		List<PersonaGiuridicaGeko> strutture = fromOrganikoServizi.findStruttureByStrutturaPadreIDAndAnno(dipartimentoID, anno);
		if(strutture != null && !strutture.isEmpty()){
        for(PersonaGiuridicaGeko strDip : strutture) {
        	List<IncaricoGeko> incarichiStruttura = fromOrganikoServizi.findIncarichiByStrutturaIDAndAnno(strDip.idPersona, anno);//strDip.getIncarichiAnno();
        	if(incarichiStruttura != null && !incarichiStruttura.isEmpty()){
        	for(IncaricoGeko incarico : incarichiStruttura){
    			mapObjDept.put(incarico,this.findObiettiviTotaliByIncaricoIDAndAnno(incarico.idIncarico, anno));//incarico.getObiettiviTotAnno());
    		}
        	}
            // per ogni struttura vedo se c'� una sottostruttura e ripeto il procedimento
            List<PersonaGiuridicaGeko> subStrutture = fromOrganikoServizi.findStruttureByStrutturaPadreIDAndAnno(strDip.idPersona,anno);
                if(subStrutture != null && !subStrutture.isEmpty()){
                    for(PersonaGiuridicaGeko subStrDip : subStrutture){
                    	List<IncaricoGeko> incarichiSubStruttura = fromOrganikoServizi.findIncarichiByStrutturaIDAndAnno(subStrDip.idPersona, anno);//subStrDip.getIncarichiAnno();
                    	if(incarichiSubStruttura != null && !incarichiSubStruttura.isEmpty()){
	                    	for(IncaricoGeko incarico : incarichiSubStruttura){
	                			mapObjDept.put(incarico,this.findObiettiviTotaliByIncaricoIDAndAnno(incarico.idIncarico, anno));
	                			//incarico.getObiettiviTotAnno());
	                		}
                    	}
                    }
                }
            }
		}
        return mapObjDept;
	}



	public Map<IncaricoGeko, List<Obiettivo>> mapObiettiviApicaliDipartimentoIDAndAnno(Integer dipartimentoID, int anno) {
		PersonaGiuridicaGeko dipartimento = fromOrganikoServizi.findPersonaGiuridicaById(dipartimentoID);
		Map<IncaricoGeko,List<Obiettivo>> mapObjDept = new TreeMap(new Comparator<IncaricoGeko>() {
        	public int compare(IncaricoGeko a, IncaricoGeko b)
            {
        		PersonaGiuridicaGeko struA = fromOrganikoServizi.findPersonaGiuridicaById(a.pgID);
        		PersonaFisicaGeko dirigA = fromOrganikoServizi.findPersonaFisicaById(a.pfID);
        		PersonaGiuridicaGeko struB = fromOrganikoServizi.findPersonaGiuridicaById(b.pgID);
        		PersonaFisicaGeko dirigB = fromOrganikoServizi.findPersonaFisicaById(b.pfID);
                String nomeA = struA.getDenominazione()+dirigA.getCognomeNome();
                String nomeB = struB.getDenominazione()+dirigB.getCognomeNome();
                return nomeA.compareTo(nomeB);
            }
        });
		// obiettivi diretti
		List<IncaricoGeko> incarichiApicali = fromOrganikoServizi.findIncarichiApicaliByDipartimentoIDAndAnno(dipartimentoID, anno);
		for(IncaricoGeko incarico : incarichiApicali){
			mapObjDept.put(incarico, this.findObiettiviTotaliByIncaricoIDAndAnno(incarico.idIncarico, anno));//incarico.getObiettiviTotAnno());
		}
		
		//PropertyComparator.sort(lstStruct, new MutableSortDefinition("order",true,true));
		// obiettivi della strutture intermedie
		List<PersonaGiuridicaGeko> strutture = fromOrganikoServizi.findStruttureByStrutturaPadreIDAndAnno(dipartimentoID,anno);
		if(strutture != null && !strutture.isEmpty()){
        for(PersonaGiuridicaGeko strDip : strutture) {
        	List<IncaricoGeko> incarichiStruttura = fromOrganikoServizi.findIncarichiByStrutturaIDAndAnno(strDip.idPersona, anno);//strDip.getIncarichiAnno();
    		for(IncaricoGeko incarico : incarichiStruttura){
    			mapObjDept.put(incarico,this.findObiettiviTotaliByIncaricoIDAndAnno(incarico.idIncarico, anno));//incarico.getObiettiviTotAnno());
    		}
            
            // per ogni struttura vedo se c'� una sottostruttura e ripeto il procedimento
            List<PersonaGiuridicaGeko> subStrutture = fromOrganikoServizi.findStruttureByStrutturaPadreIDAndAnno(strDip.idPersona,anno);
                if(subStrutture != null && !subStrutture.isEmpty()){
                    for(PersonaGiuridicaGeko subStrDip : subStrutture){
                    	List<IncaricoGeko> incarichiSubStruttura = fromOrganikoServizi.findIncarichiByStrutturaIDAndAnno(subStrDip.idPersona, anno);//subStrDip.getIncarichiAnno();
                		for(IncaricoGeko incarico : incarichiSubStruttura){
                			mapObjDept.put(incarico,this.findObiettiviTotaliByIncaricoIDAndAnno(incarico.idIncarico, anno));//incarico.getObiettiviTotAnno());
                		}
                    }
                }
            }
		}
        return mapObjDept;
	}

	//
	public Map<Integer, String> mapSelectObiettiviApicaliByDipartimentoIDAndAnno(Integer dipartimentoID, int anno) {
		Map<Integer,String> mappa = new LinkedHashMap<Integer,String>();
		
		/* rivedere
		
		// obiettivi diretti
		List<IncaricoGeko> incarichiApicali = fromOrganikoServizi.findIncarichiApicaliByDipartimentoIDAndAnno(dipartimentoID, anno);
		for(IncaricoGeko incarico : incarichiApicali){
			for (Obiettivo obj : this.findObiettiviApicaliByIncaricoIDAndAnno(incarico.idIncarico, anno)){
				String str = obj.getStruttura().getCodice() +" - " +obj.getDescrizione();
				mappa.put(obj.getIdObiettivo(),str);
			}
		}
		for(OpPersonaGiuridica strDip : dipartimento.getOpPersonaGiuridicas()){
        	strDip.setAnno(anno);
        	List<Incarico> incarichiStruttura = strDip.getIncarichiAnno();
    		for(Incarico incarico : incarichiStruttura){
    			incarico.setAnno(anno);
    			for (Obiettivo obj : incarico.getObiettiviApicaliAnno()){
    				String str = obj.getStruttura().getCodice() +" - " +obj.getDescrizione();
    				mappa.put(obj.getIdObiettivo(),str);
    			}
    		}
            // per ogni struttura vedo se c'� una sottostruttura e ripeto il procedimento
            List<OpPersonaGiuridica> subStrutture = strDip.getOpPersonaGiuridicas();
                if(subStrutture != null && !subStrutture.isEmpty()){
                    for(OpPersonaGiuridica subStrDip : subStrutture){
                    	subStrDip.setAnno(anno);
                    	List<Incarico> incarichiSubStruttura = subStrDip.getIncarichiAnno();
                		for(Incarico incarico : incarichiSubStruttura){
                			incarico.setAnno(anno);
                			incarico.setAnno(anno);
                			for (Obiettivo obj : incarico.getObiettiviApicaliAnno()){
                				String str = obj.getStruttura().getCodice() +" - " +obj.getDescrizione();
                				mappa.put(obj.getIdObiettivo(),str);
                			}
                		}
                    }
                }
            }
		//
		*/
		
		// TODO Auto-generated method stub
		return mappa;
	}

	@Override
	public List<Obiettivo> findObiettiviApicaliDirettiConPersoneByIncaricoIDAndAnno(Integer incaricoID, int anno) {
		List<Obiettivo> listObiettiviApicali = repo.findByIncaricoIDAndAnnoAndApicale(incaricoID, anno, true);
		final IncaricoGeko incarico = fromOrganikoServizi.findIncaricoById(incaricoID);
		for (Obiettivo obj : listObiettiviApicali){
    		IncaricoGeko incaricoGeko = fromOrganikoServizi.findIncaricoById(obj.getIncaricoID());
    		obj.setIncarico(incarico);
    		List<ObiettivoAssegnazione> lstAssegnazioni = objAssServizi.findByIdObiettivo(obj.getIdObiettivo());
    		if (!lstAssegnazioni.isEmpty()){
    			for(ObiettivoAssegnazione objAss :lstAssegnazioni){
    				PersonaFisicaGeko dip = fromOrganikoServizi.findPersonaFisicaById(objAss.getIdPersona());	
    				objAss.setOpPersonaFisica(dip);
    			}
    			PropertyComparator.sort(lstAssegnazioni, new MutableSortDefinition("opPersonaFisica.stringa",true,true));
    		}
    		obj.setAssegnazioni(lstAssegnazioni);
			
			for(Azione act : obj.getAzioni()){
				for(AzioneAssegnazione assegn : act.getAssegnazioni()){
					PersonaFisicaGeko pf = fromOrganikoServizi.findPersonaFisicaById(assegn.getPfID());
					if (pf != null) assegn.setOpPersonaFisica(pf);
				}
			}
    	}
		PropertyComparator.sort(listObiettiviApicali, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
		return listObiettiviApicali;
	}

	@Override
	public List<Obiettivo> findByCodiceAndAnno(String codice, int anno) {
		List<Obiettivo> listaObj = repo.findByCodiceAndAnno(codice, anno);
		//PropertyComparator.sort(listaObj, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
		return listaObj;
	}

	@Override
	public List<Obiettivo> findObiettiviPopSenzaPersoneByIncaricoIDAndAnno(Integer incaricoID, int anno) {
            List<Obiettivo> listaObj = repo.findByIncaricoIDAndAnno(incaricoID, anno);
            List<Obiettivo> listObiettiviPop = new ArrayList<Obiettivo>();
            if(listaObj!=null && !listaObj.isEmpty()){
                for(Obiettivo obj : listaObj){
                    if (obj.getTipo().equals(Obiettivo.TipoEnum.POS_ORGAN)) listObiettiviPop.add(obj);
                }
            }
            //
            PropertyComparator.sort(listObiettiviPop, new MutableSortDefinition("order",true,true));// tipo + idobiettivo
            return listObiettiviPop;		
	}
	
} // --------------------------------

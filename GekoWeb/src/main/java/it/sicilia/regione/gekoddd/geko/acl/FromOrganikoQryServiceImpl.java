/**
 * FromOrganikoQryServiceImpl.java è l'implementazione della API FromOrganikoQryService
 * 
 */

package it.sicilia.regione.gekoddd.geko.acl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;
import it.sicilia.regione.gekoddd.session.domain.OrganikoCache;
import it.sicilia.regione.gekoddd.geko.acl.dto.IncaricoGekoDTO;
import it.sicilia.regione.gekoddd.geko.acl.dto.PersonaFisicaGekoDTO;
import it.sicilia.regione.gekoddd.geko.acl.dto.PersonaGiuridicaGekoDTO;
//import it.sicilia.regione.gekoddd.organiko.hs.pl.IncaricoDTO;
//import it.sicilia.regione.gekoddd.organiko.hs.pl.PersonaFisicaDTO;
//import it.sicilia.regione.gekoddd.organiko.hs.pl.PersonaGiuridicaDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


@Service("fromOrganikoQryService")
public class FromOrganikoQryServiceImpl implements FromOrganikoQryService {
	
	@Value("${msOrganiko.url}")
	private String serverMsOrganikoQueryIP;
	
	private Log log = LogFactory.getLog(FromOrganikoQryServiceImpl.class);
	
	@Autowired
    private OrganikoCache orgCache;
	
	
	
	// 1. Incarichi
	// 
	// 1.1
	@Override
	public IncaricoGeko findIncaricoById(Integer id) {
		//log.info("findIncaricoById:"+id);
		IncaricoGeko inc = null;
		if (orgCache.getCacheIncaricoById().isEmpty()){
			log.info("orgCache.cacheIncaricoById.isEmpty");
		}
		//
		if (orgCache.getCacheIncaricoById().containsKey(id)) {
			inc = orgCache.getCacheIncaricoById().get(id);
			log.info("findIncaricoByIdCached:"+id);
		}
		else {
			log.info("findIncaricoByIdRest:"+id);
			RestTemplate restTemplate = new RestTemplate();
			String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findIncaricoById/"+id;
			/*
                        RestTemplate restTemplate = new RestTemplate();
                        String fooResourceUrl = "http://localhost:8080/spring-rest/foos";
                        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);
                        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
                        
                        RestTemplate restTemplate = new RestTemplate();
                        HttpEntity<Foo> request = new HttpEntity<>(new Foo("bar"));
                        Foo foo = restTemplate.postForObject(fooResourceUrl, request, Foo.class);
                        assertThat(foo, notNullValue());
                        assertThat(foo.getName(), is("bar"));
                        */
                        
                        IncaricoGekoDTO incDTO = restTemplate.getForObject(resourceUri, IncaricoGekoDTO.class,id);
			inc = new IncaricoGeko(incDTO);
			orgCache.getCacheIncaricoById().put(id, inc);
                        
		}
		return inc;
	}
	
	// 1.2
	@Override
	public List<IncaricoGeko> findIncarichiApicaliByAssessoratoIDAndAnno(Integer assessoratoID, int anno) throws HttpStatusCodeException{
		List<IncaricoGeko> incarichiGeko = new ArrayList<IncaricoGeko>();			
		try {
		//log.info("findIncarichiApicaliByAssessoratoIDAndAnnoRest:"+assessoratoID+"/"+anno);
		RestTemplate restTemplate = new RestTemplate();
		ParameterizedTypeReference<List<IncaricoGekoDTO>> responseType = new ParameterizedTypeReference<List<IncaricoGekoDTO>>(){};
		String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findIncarichiApicaliByAssessoratoIDAndAnno/"+assessoratoID+"/"+anno;
		//log.info(resourceUri);
		ResponseEntity<List<IncaricoGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
		List<IncaricoGekoDTO> incarichiGekoDTO = response.getBody();		
		for(IncaricoGekoDTO incaricoDTO : incarichiGekoDTO){
			IncaricoGeko incaricoGeko = new IncaricoGeko(incaricoDTO);
			incarichiGeko.add(incaricoGeko);
			orgCache.getCacheIncaricoById().put(incaricoGeko.idIncarico,incaricoGeko); // aggiorno la cache	
		}
		//log.info("findIncarichiApicaliByAssessoratoIDAndAnno() incarico geko completato");
		PropertyComparator.sort(incarichiGeko, new MutableSortDefinition("order",true,true));
		//log.info("findIncarichiApicaliByAssessoratoIDAndAnno() sort completato");		
		} catch (HttpStatusCodeException ex){
			log.error(ex);
		}		
		
		return incarichiGeko;
	}
	
	// 1.3
	@Override
	public List<IncaricoGeko> findIncarichiApicaliByDipartimentoIDAndAnno(Integer dipartimentoID, int anno) throws HttpStatusCodeException{
		//
		List<IncaricoGeko> incarichiGeko = new ArrayList<IncaricoGeko>();
		try {
			log.info("findIncarichiByDipartimentoIDAndAnnoRest:"+dipartimentoID+"/"+anno);
			RestTemplate restTemplate = new RestTemplate();
			ParameterizedTypeReference<List<IncaricoGekoDTO>> responseType = new ParameterizedTypeReference<List<IncaricoGekoDTO>>(){};
			String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findIncarichiApicaliByDipartimentoIDAndAnno/"+dipartimentoID+"/"+anno;
			//log.info(resourceUri);
			ResponseEntity<List<IncaricoGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
			List<IncaricoGekoDTO> incarichiGekoDTO = response.getBody();
			//
			for(IncaricoGekoDTO incaricoDTO : incarichiGekoDTO){
				IncaricoGeko incaricoGeko = new IncaricoGeko(incaricoDTO);
				incarichiGeko.add(incaricoGeko);
				orgCache.getCacheIncaricoById().put(incaricoGeko.idIncarico,incaricoGeko); // aggiorno la cache					
			}
			//log.info("findIncarichiByDipartimentoIDAndAnno: incarichiGeko completati");
			PropertyComparator.sort(incarichiGeko, new MutableSortDefinition("order",true,true));
		} catch (HttpStatusCodeException ex){
					log.error(ex);
					return incarichiGeko;
		}
		return incarichiGeko;				
	}
	
	
	// 1.4
	@Override
	public List<IncaricoGeko> findIncarichiByDipartimentoIDAndAnno(Integer strutturaID, int anno) throws HttpStatusCodeException {
		//		
		List<IncaricoGeko> incarichiGeko = new ArrayList<IncaricoGeko>();
		try {
			log.info("findIncarichiByDipartimentoIDAndAnnoRest:"+strutturaID+"/"+anno);
			RestTemplate restTemplate = new RestTemplate();
			ParameterizedTypeReference<List<IncaricoGekoDTO>> responseType = new ParameterizedTypeReference<List<IncaricoGekoDTO>>(){};
			String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findIncarichiByDipartimentoIDAndAnno/"+strutturaID+"/"+anno;
			//log.info(resourceUri);
			ResponseEntity<List<IncaricoGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
			List<IncaricoGekoDTO> incarichiGekoDTO = response.getBody();
			//
			for(IncaricoGekoDTO incaricoDTO : incarichiGekoDTO){
				IncaricoGeko incaricoGeko = new IncaricoGeko(incaricoDTO);
				incarichiGeko.add(incaricoGeko);
				orgCache.getCacheIncaricoById().put(incaricoGeko.idIncarico,incaricoGeko); // aggiorno la cache					
			}
			//log.info("findIncarichiByDipartimentoIDAndAnno: incarichiGeko completati");
			PropertyComparator.sort(incarichiGeko, new MutableSortDefinition("order",true,true));
		} catch (HttpStatusCodeException ex){
					log.error(ex);
					return incarichiGeko;
		}
			return incarichiGeko;				
	}
	
        // 1.4
	@Override
	public List<IncaricoGeko> findIncarichiDirigenzialiByDipartimentoIDAndAnno(Integer strutturaID, int anno) throws HttpStatusCodeException {
		//		
		List<IncaricoGeko> incarichiGeko = new ArrayList<IncaricoGeko>();
		try {
			log.info("findIncarichiByDipartimentoIDAndAnnoRest:"+strutturaID+"/"+anno);
			RestTemplate restTemplate = new RestTemplate();
			ParameterizedTypeReference<List<IncaricoGekoDTO>> responseType = new ParameterizedTypeReference<List<IncaricoGekoDTO>>(){};
			String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findIncarichiDirigenzialiByDipartimentoIDAndAnno/"+strutturaID+"/"+anno;
			//log.info(resourceUri);
			ResponseEntity<List<IncaricoGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
			List<IncaricoGekoDTO> incarichiGekoDTO = response.getBody();
			//
			for(IncaricoGekoDTO incaricoDTO : incarichiGekoDTO){
				IncaricoGeko incaricoGeko = new IncaricoGeko(incaricoDTO);
				incarichiGeko.add(incaricoGeko);
				orgCache.getCacheIncaricoById().put(incaricoGeko.idIncarico,incaricoGeko); // aggiorno la cache					
			}
			//log.info("findIncarichiByDipartimentoIDAndAnno: incarichiGeko completati");
			PropertyComparator.sort(incarichiGeko, new MutableSortDefinition("order",true,true));
		} catch (HttpStatusCodeException ex){
					log.error(ex);
					return incarichiGeko;
		}
			return incarichiGeko;				
	}
	
	// 1.5
	@Override
	public List<IncaricoGeko> findIncarichiByStrutturaIDAndAnno(Integer strutturaID, int anno) throws HttpStatusCodeException {
		List<IncaricoGeko> incarichiGeko = null;
		try{
		
			//log.info("findIncarichiByStrutturaIDAndAnnoRest:"+strutturaID+"/"+anno);
			RestTemplate restTemplate = new RestTemplate();
			ParameterizedTypeReference<List<IncaricoGekoDTO>> responseType = new ParameterizedTypeReference<List<IncaricoGekoDTO>>(){};
			String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findIncarichiByStrutturaIDAndAnno/"+strutturaID+"/"+anno;
			//log.info(resourceUri);
			
			ResponseEntity<List<IncaricoGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
			List<IncaricoGekoDTO> incarichiGekoDTO = response.getBody();
			incarichiGeko = new ArrayList<IncaricoGeko>();
			for(IncaricoGekoDTO incaricoDTO : incarichiGekoDTO){
				IncaricoGeko incaricoGeko = new IncaricoGeko(incaricoDTO);
				incarichiGeko.add(incaricoGeko);
				orgCache.getCacheIncaricoById().put(incaricoGeko.idIncarico,incaricoGeko); // aggiorno la cache					
			}
			PropertyComparator.sort(incarichiGeko, new MutableSortDefinition("order",true,true));			
		}
		catch  (HttpStatusCodeException exception){
			log.error("rest exception: "+exception);
		}
		return incarichiGeko;
	}
		
	@Override
	public List<IncaricoGeko> findIncarichiPopByDipartimentoIDAndAnno(Integer strutturaID, int anno)
			throws HttpStatusCodeException {
		List<IncaricoGeko> incarichiGeko = null;
		try{		
			//log.info("findIncarichiByStrutturaIDAndAnnoRest:"+strutturaID+"/"+anno);
			RestTemplate restTemplate = new RestTemplate();
			ParameterizedTypeReference<List<IncaricoGekoDTO>> responseType = new ParameterizedTypeReference<List<IncaricoGekoDTO>>(){};
			String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findIncarichiPopByDipartimentoIDAndAnno/"+strutturaID+"/"+anno;
			//log.info(resourceUri);
			
			ResponseEntity<List<IncaricoGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
			List<IncaricoGekoDTO> incarichiGekoDTO = response.getBody();
			incarichiGeko = new ArrayList<IncaricoGeko>();
			for(IncaricoGekoDTO incaricoDTO : incarichiGekoDTO){
				IncaricoGeko incaricoGeko = new IncaricoGeko(incaricoDTO);
				incarichiGeko.add(incaricoGeko);
				orgCache.getCacheIncaricoById().put(incaricoGeko.idIncarico,incaricoGeko); // aggiorno la cache					
			}
			PropertyComparator.sort(incarichiGeko, new MutableSortDefinition("order",true,true));			
		}
		catch  (HttpStatusCodeException exception){
			log.error("rest exception: "+exception);
		}
		return incarichiGeko;
	}
        
        @Override
    public List<IncaricoGeko> findIncarichiPopByIntermediaIDAndAnno(Integer strutturaID, int anno) throws HttpStatusCodeException {
        List<IncaricoGeko> incarichiGeko = null;
		try{		
			//log.info("findIncarichiByStrutturaIDAndAnnoRest:"+strutturaID+"/"+anno);
			RestTemplate restTemplate = new RestTemplate();
			ParameterizedTypeReference<List<IncaricoGekoDTO>> responseType = new ParameterizedTypeReference<List<IncaricoGekoDTO>>(){};
			String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findIncarichiPopByIntermediaIDAndAnno/"+strutturaID+"/"+anno;
			//log.info(resourceUri);
			
			ResponseEntity<List<IncaricoGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
			List<IncaricoGekoDTO> incarichiGekoDTO = response.getBody();
			incarichiGeko = new ArrayList<IncaricoGeko>();
			for(IncaricoGekoDTO incaricoDTO : incarichiGekoDTO){
				IncaricoGeko incaricoGeko = new IncaricoGeko(incaricoDTO);
				incarichiGeko.add(incaricoGeko);
				orgCache.getCacheIncaricoById().put(incaricoGeko.idIncarico,incaricoGeko); // aggiorno la cache					
			}
			PropertyComparator.sort(incarichiGeko, new MutableSortDefinition("order",true,true));			
		}
		catch  (HttpStatusCodeException exception){
			log.error("rest exception: "+exception);
		}
		return incarichiGeko;
    }
	
	// 1.6
	@Override
	public List<IncaricoGeko> findIncarichiByDirigenteIDAndAnno(Integer dirigenteID, int anno) throws HttpStatusCodeException {
		List<IncaricoGeko> incarichiGeko = null;		
		//log.info("findIncarichiByDirigenteIDAndAnnoRest:"+dirigenteID+"/"+anno);
		RestTemplate restTemplate = new RestTemplate();
		ParameterizedTypeReference<List<IncaricoGekoDTO>> responseType = new ParameterizedTypeReference<List<IncaricoGekoDTO>>(){};
		String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findIncarichiByDirigenteIDAndAnno/"+dirigenteID+"/"+anno;
		//log.info(resourceUri);
		try{
		ResponseEntity<List<IncaricoGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
		List<IncaricoGekoDTO> incarichiGekoDTO = response.getBody();
		incarichiGeko = new ArrayList<IncaricoGeko>();
		for(IncaricoGekoDTO incaricoDTO : incarichiGekoDTO){
			IncaricoGeko incaricoGeko = new IncaricoGeko(incaricoDTO);
			incarichiGeko.add(incaricoGeko);
			orgCache.getCacheIncaricoById().put(incaricoGeko.idIncarico,incaricoGeko); // aggiorno la cache
		}
		PropertyComparator.sort(incarichiGeko, new MutableSortDefinition("order",true,true));
		
		}
		catch  (HttpStatusCodeException exception){
			log.error("rest exception: "+exception);
		}		
		return incarichiGeko;
	}

       
	
	// ********************************************************************************************
	
	// 2. Persona Fisica
	// 2.1
	@Override
	public PersonaFisicaGeko findPersonaFisicaById(Integer id) {
		PersonaFisicaGeko pf =null;
		if (orgCache.getCachePersonaFisicaById().isEmpty()){
			log.info("orgCache.getCachePersonaFisicaById.isEmpty");
		}
		if (orgCache.getCachePersonaFisicaById().containsKey(id)) {
			pf = orgCache.getCachePersonaFisicaById().get(id);
			//log.info("findPersonaFisicaByIdCached:"+id);
			log.info("persona trovata nella cache: "+id+" - "+pf.getStringa());
		}
		else {
			log.info("findPersonaFisicaByIdRest: "+id);
			RestTemplate restTemplate = new RestTemplate();
			String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findPersonaFisicaById/"+id;
			try{
				PersonaFisicaGekoDTO pfDTO = restTemplate.getForObject(resourceUri, PersonaFisicaGekoDTO.class,id);
				if (pfDTO !=null){
					pf = new PersonaFisicaGeko(pfDTO);
					log.info("persona trovata con REST: "+id+" - "+pfDTO.getStringa());
					orgCache.getCachePersonaFisicaById().put(id,pf);
				}
			}
			catch  (HttpStatusCodeException exception){
				log.error("rest exception: "+exception);
			}
		}
		return pf;
	}

	// 2.2
	@Override
	public List<PersonaFisicaGeko> findDipendentiStrictByStrutturaIDAndAnno(int idStruttura, int anno) throws HttpStatusCodeException {
		List<PersonaFisicaGeko> lstPersoneGeko = null;		
			//log.info("findDipendentiStrictByStrutturaIDAndAnnoRest:"+idStruttura+"/"+anno);
			RestTemplate restTemplate = new RestTemplate();
			ParameterizedTypeReference<List<PersonaFisicaGekoDTO>> responseType = new ParameterizedTypeReference<List<PersonaFisicaGekoDTO>>(){};
			String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findDipendentiStrictByStrutturaIDAndAnno/"+idStruttura+"/"+anno;
			//log.info(resourceUri);
			try {
			ResponseEntity<List<PersonaFisicaGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
			List<PersonaFisicaGekoDTO> lstPersoneGekoDTO = response.getBody();
			lstPersoneGeko = new ArrayList<PersonaFisicaGeko>();
			for (PersonaFisicaGekoDTO pfDTO : lstPersoneGekoDTO){
				PersonaFisicaGeko pfGeko = new PersonaFisicaGeko(pfDTO);
				lstPersoneGeko.add(pfGeko);
				orgCache.getCachePersonaFisicaById().put(pfGeko.getIdPersona(),pfGeko);
			}
			PropertyComparator.sort(lstPersoneGeko, new MutableSortDefinition("order",true,true));
			} catch  (HttpStatusCodeException exception){
				log.error("rest exception: "+exception);
		}
		return lstPersoneGeko;
	}

	// 2.3
	@Override
	public List<PersonaFisicaGeko> findDipendentiGlobalByStrutturaIDAndAnno(int idStruttura, int anno) {	
		List<PersonaFisicaGeko> lstPersoneGeko = null;
		//log.info("findDipendentiGlobalByStrutturaIDAndAnnoRest:"+idStruttura+"/"+anno);
		RestTemplate restTemplate = new RestTemplate();
		ParameterizedTypeReference<List<PersonaFisicaGekoDTO>> responseType = new ParameterizedTypeReference<List<PersonaFisicaGekoDTO>>(){};
		String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findDipendentiGlobalByStrutturaIDAndAnno/"+idStruttura+"/"+anno;
		//log.info(resourceUri);
		try {
		ResponseEntity<List<PersonaFisicaGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
		List<PersonaFisicaGekoDTO> lstPersoneGekoDTO = response.getBody();
		lstPersoneGeko = new ArrayList<PersonaFisicaGeko>();
		for (PersonaFisicaGekoDTO pfDTO : lstPersoneGekoDTO){
			PersonaFisicaGeko pfGeko = new PersonaFisicaGeko(pfDTO);
			lstPersoneGeko.add(pfGeko);
			orgCache.getCachePersonaFisicaById().put(pfGeko.getIdPersona(),pfGeko);
		}
		PropertyComparator.sort(lstPersoneGeko, new MutableSortDefinition("order",true,true));
		} catch  (HttpStatusCodeException exception){
			log.error("rest exception: "+exception);
	}
	return lstPersoneGeko;
}

	// 2.4
	@Override
	public List<PersonaFisicaGeko> findDipendentiByDipartimentoIDAndAnno(Integer idDipartimento, int anno) throws HttpStatusCodeException {	
		List<PersonaFisicaGeko> lstPersoneGeko = null;
		//log.info("findDipendentiGlobalByStrutturaIDAndAnnoRest:"+idStruttura+"/"+anno);
		RestTemplate restTemplate = new RestTemplate();
		ParameterizedTypeReference<List<PersonaFisicaGekoDTO>> responseType = new ParameterizedTypeReference<List<PersonaFisicaGekoDTO>>(){};
		String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findDipendentiByDipartimentoIDAndAnno/"+idDipartimento+"/"+anno;
		try {
		ResponseEntity<List<PersonaFisicaGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
		List<PersonaFisicaGekoDTO> lstPersoneGekoDTO = response.getBody();
		lstPersoneGeko = new ArrayList<PersonaFisicaGeko>();
		for (PersonaFisicaGekoDTO pfDTO : lstPersoneGekoDTO){
			PersonaFisicaGeko pfGeko = new PersonaFisicaGeko(pfDTO);
			lstPersoneGeko.add(pfGeko);
			orgCache.getCachePersonaFisicaById().put(pfGeko.getIdPersona(),pfGeko);
		}
		PropertyComparator.sort(lstPersoneGeko, new MutableSortDefinition("order",true,true));
		} catch  (HttpStatusCodeException exception){
			log.error("rest exception: "+exception);
	}
	return lstPersoneGeko;
	}

	
	// *****************************************************************************************
	// 3. PersonaGiuridica
	// 3.1
	@Override
	public PersonaGiuridicaGeko findPersonaGiuridicaById(Integer id) {
		PersonaGiuridicaGeko pg =null;		
		if (orgCache.getCachePersonaGiuridicaById().containsKey(id)) {
			pg = orgCache.getCachePersonaGiuridicaById().get(id);
			//log.info("findPersonaGiuridicaByIdCached:"+id);
		}
		else {
			//log.info("findPersonaGiuridicaByIdRest:"+id);
			RestTemplate restTemplate = new RestTemplate();
			String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findPersonaGiuridicaById/"+id;
			try {
				PersonaGiuridicaGekoDTO pgDTO = restTemplate.getForObject(resourceUri, PersonaGiuridicaGekoDTO.class,id);
				pg = new PersonaGiuridicaGeko(pgDTO);
				orgCache.getCachePersonaGiuridicaById().put(id,pg);
			} catch  (HttpStatusCodeException exception){
				log.error("rest exception: "+exception);
			}
		}
		return pg;
	}
	
	@Override
	public PersonaGiuridicaGeko findDipartimentoByPersonaFisicaID(Integer pfID) {
            PersonaGiuridicaGeko pg =null;			
            //log.info("findPersonaGiuridicaByIdRest:"+id);
            RestTemplate restTemplate = new RestTemplate();
            String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findDipartimentoByPersonaFisicaId/"+pfID;
            try {
                    PersonaGiuridicaGekoDTO pgDTO = restTemplate.getForObject(resourceUri, PersonaGiuridicaGekoDTO.class,pfID);
                    pg = new PersonaGiuridicaGeko(pgDTO);
                    orgCache.getCachePersonaGiuridicaById().put(pfID,pg);
            } catch  (HttpStatusCodeException exception){
                    log.error("rest exception: "+exception);
            }
            return pg;
	}
	
	@Override
	public PersonaGiuridicaGeko findAssessoratoByPersonaFisicaID(Integer pfID) {
            PersonaGiuridicaGeko assessorato =null;
            RestTemplate restTemplate = new RestTemplate();
            String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findAssessoratoByPersonaFisicaId/"+pfID;
            try {
                    PersonaGiuridicaGekoDTO pgDTO = restTemplate.getForObject(resourceUri, PersonaGiuridicaGekoDTO.class,pfID);
                    if (pgDTO != null) {
                            assessorato = new PersonaGiuridicaGeko(pgDTO);			
                            orgCache.getCachePersonaGiuridicaById().put(assessorato.getIdPersona(),assessorato);
                    } 
            }catch  (HttpStatusCodeException exception){
                    log.error("rest exception: "+exception);
            }
            return assessorato;
	}
	
	@Override	
	public List<PersonaGiuridicaGeko> listAssessoratiAndAnno(int anno) throws HttpStatusCodeException {
		List<PersonaGiuridicaGeko> lstPersoneGeko = null;		
		//log.info("listAssessoratiAndAnnoRest:"+anno);
		RestTemplate restTemplate = new RestTemplate();
		ParameterizedTypeReference<List<PersonaGiuridicaGekoDTO>> responseType = new ParameterizedTypeReference<List<PersonaGiuridicaGekoDTO>>(){};
		String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/listAssessoratiAndAnno/"+anno;
		//log.info(resourceUri);
		try {
		ResponseEntity<List<PersonaGiuridicaGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
		List<PersonaGiuridicaGekoDTO> lstPersoneGekoDTO = response.getBody();
		lstPersoneGeko = new ArrayList<PersonaGiuridicaGeko>();
		for (PersonaGiuridicaGekoDTO pgDTO : lstPersoneGekoDTO){
			PersonaGiuridicaGeko pgGeko = new PersonaGiuridicaGeko(pgDTO);
			lstPersoneGeko.add(pgGeko);
			orgCache.getCachePersonaGiuridicaById().put(pgGeko.getIdPersona(),pgGeko);
		}
		PropertyComparator.sort(lstPersoneGeko, new MutableSortDefinition("order",true,true));
		}catch  (HttpStatusCodeException exception){
			log.error("rest exception: "+exception);
		}
		return lstPersoneGeko;
	}
	
	@Override
	public List<PersonaGiuridicaGeko> listDipartimentiByAssessoratoIDAndAnno(Integer assID, int anno) throws HttpStatusCodeException {
		List<PersonaGiuridicaGeko> lstPersoneGeko = null;		
		//log.info("listDipartimentiByAssessoratoIDAndAnnoRest:"+anno);
		RestTemplate restTemplate = new RestTemplate();
		ParameterizedTypeReference<List<PersonaGiuridicaGekoDTO>> responseType = new ParameterizedTypeReference<List<PersonaGiuridicaGekoDTO>>(){};
		String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/listDipartimentiByAssessoratoIDAndAnno/"+assID+"/"+anno;
		try {
			ResponseEntity<List<PersonaGiuridicaGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
			List<PersonaGiuridicaGekoDTO> lstPersoneGekoDTO = response.getBody();
			lstPersoneGeko = new ArrayList<PersonaGiuridicaGeko>();
			for (PersonaGiuridicaGekoDTO pgDTO : lstPersoneGekoDTO){
				PersonaGiuridicaGeko pgGeko = new PersonaGiuridicaGeko(pgDTO);
				lstPersoneGeko.add(pgGeko);
				orgCache.getCachePersonaGiuridicaById().put(pgGeko.getIdPersona(),pgGeko);
			}
			PropertyComparator.sort(lstPersoneGeko, new MutableSortDefinition("order",true,true));
		} catch  (HttpStatusCodeException exception){
			log.error("rest exception: "+exception);
		}
		return lstPersoneGeko;
	}
	
	@Override
	public List<PersonaGiuridicaGeko> findStruttureByStrutturaPadreIDAndAnno(Integer padreID, int anno) throws HttpStatusCodeException {
		List<PersonaGiuridicaGeko> lstPersoneGeko = null;		
		RestTemplate restTemplate = new RestTemplate();
		ParameterizedTypeReference<List<PersonaGiuridicaGekoDTO>> responseType = new ParameterizedTypeReference<List<PersonaGiuridicaGekoDTO>>(){};
		String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findStruttureByStrutturaPadreIDAndAnno/"+padreID+"/"+anno;
		try {
			ResponseEntity<List<PersonaGiuridicaGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
			List<PersonaGiuridicaGekoDTO> lstPersoneGekoDTO = response.getBody();
			lstPersoneGeko = new ArrayList<PersonaGiuridicaGeko>();
			for (PersonaGiuridicaGekoDTO pgDTO : lstPersoneGekoDTO){
				PersonaGiuridicaGeko pgGeko = new PersonaGiuridicaGeko(pgDTO);
				lstPersoneGeko.add(pgGeko);
				orgCache.getCachePersonaGiuridicaById().put(pgGeko.getIdPersona(),pgGeko);
			}
			PropertyComparator.sort(lstPersoneGeko, new MutableSortDefinition("order",true,true));
		} catch  (HttpStatusCodeException exception){
			log.error("rest exception: "+exception);
		}
		return lstPersoneGeko;
	}

    

    @Override
    public PersonaGiuridicaGeko findDipartimentoByPersonaGiuridicaID(Integer pgID) throws HttpStatusCodeException {
        PersonaGiuridicaGeko pg =null;			
        //log.info("findPersonaGiuridicaByIdRest:"+id);
        RestTemplate restTemplate = new RestTemplate();
        String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/findDipartimentoByPersonaGiuridicaID/"+pgID;
        try {
                PersonaGiuridicaGekoDTO pgDTO = restTemplate.getForObject(resourceUri, PersonaGiuridicaGekoDTO.class,pgID);
                pg = new PersonaGiuridicaGeko(pgDTO);
                orgCache.getCachePersonaGiuridicaById().put(pgID,pg);
        } catch  (HttpStatusCodeException exception){
                log.error("rest exception: "+exception);
        }
        return pg;
    }

    @Override
    public List<PersonaGiuridicaGeko> listSubStruttureByDipartimentoIDAndAnno(Integer deptID, int anno) throws HttpStatusCodeException {
        List<PersonaGiuridicaGeko> lstPersoneGeko = null;		
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<PersonaGiuridicaGekoDTO>> responseType = new ParameterizedTypeReference<List<PersonaGiuridicaGekoDTO>>(){};
        String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/listSubStruttureByDipartimentoIDAndAnno/"+deptID+"/"+anno;
        try {
            ResponseEntity<List<PersonaGiuridicaGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
            List<PersonaGiuridicaGekoDTO> lstPersoneGekoDTO = response.getBody();
            lstPersoneGeko = new ArrayList<PersonaGiuridicaGeko>();
            for (PersonaGiuridicaGekoDTO pgDTO : lstPersoneGekoDTO){
                PersonaGiuridicaGeko pgGeko = new PersonaGiuridicaGeko(pgDTO);
                lstPersoneGeko.add(pgGeko);
                orgCache.getCachePersonaGiuridicaById().put(pgGeko.getIdPersona(),pgGeko);
            }
            PropertyComparator.sort(lstPersoneGeko, new MutableSortDefinition("order",true,true));
        } catch  (HttpStatusCodeException exception){
                log.error("rest exception: "+exception);
        }
        return lstPersoneGeko;
    }

    @Override
    public List<PersonaGiuridicaGeko> getStruttureFiglieAttiveOCancellateAnno(Integer padreID, int anno) throws HttpStatusCodeException {
        List<PersonaGiuridicaGeko> lstPersoneGeko = null;		
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<PersonaGiuridicaGekoDTO>> responseType = new ParameterizedTypeReference<List<PersonaGiuridicaGekoDTO>>(){};
        String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/getStruttureFiglieAttiveOCancellateAnno/"+padreID+"/"+anno;
        try {
            ResponseEntity<List<PersonaGiuridicaGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
            List<PersonaGiuridicaGekoDTO> lstPersoneGekoDTO = response.getBody();
            lstPersoneGeko = new ArrayList<PersonaGiuridicaGeko>();
            for (PersonaGiuridicaGekoDTO pgDTO : lstPersoneGekoDTO){
                PersonaGiuridicaGeko pgGeko = new PersonaGiuridicaGeko(pgDTO);
                lstPersoneGeko.add(pgGeko);
                orgCache.getCachePersonaGiuridicaById().put(pgGeko.getIdPersona(),pgGeko);
            }
            PropertyComparator.sort(lstPersoneGeko, new MutableSortDefinition("order",true,true));
        } catch  (HttpStatusCodeException exception){
                log.error("rest exception: "+exception);
        }
        return lstPersoneGeko;
    }

    @Override
    public List<PersonaGiuridicaGeko> getStruttureDiscendentiAttiveOCancellateAnno(Integer padreID, int anno) throws HttpStatusCodeException {
        List<PersonaGiuridicaGeko> lstPersoneGeko = null;		
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<PersonaGiuridicaGekoDTO>> responseType = new ParameterizedTypeReference<List<PersonaGiuridicaGekoDTO>>(){};
        String resourceUri = "http://"+serverMsOrganikoQueryIP+"/api/getStruttureDiscendentiAttiveOCancellateAnno/"+padreID+"/"+anno;
        try {
            ResponseEntity<List<PersonaGiuridicaGekoDTO>> response = restTemplate.exchange(resourceUri, HttpMethod.GET,null,responseType);
            List<PersonaGiuridicaGekoDTO> lstPersoneGekoDTO = response.getBody();
            lstPersoneGeko = new ArrayList<PersonaGiuridicaGeko>();
            for (PersonaGiuridicaGekoDTO pgDTO : lstPersoneGekoDTO){
                PersonaGiuridicaGeko pgGeko = new PersonaGiuridicaGeko(pgDTO);
                lstPersoneGeko.add(pgGeko);
                orgCache.getCachePersonaGiuridicaById().put(pgGeko.getIdPersona(),pgGeko);
            }
            PropertyComparator.sort(lstPersoneGeko, new MutableSortDefinition("order",true,true));
        } catch  (HttpStatusCodeException exception){
                log.error("rest exception: "+exception);
        }
        return lstPersoneGeko;
    }
	
	
} // ------------------------------------------------

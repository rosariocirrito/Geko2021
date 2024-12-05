package it.sicilia.regione.gekoddd.session.domain;


import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class OrganikoCache {
	
	public Map<Integer,IncaricoGeko> cacheIncaricoById = new HashMap<Integer,IncaricoGeko>();
	public Map<Integer,PersonaFisicaGeko> cachePersonaFisicaById = new HashMap<Integer,PersonaFisicaGeko>();
	public Map<Integer,PersonaGiuridicaGeko> cachePersonaGiuridicaById = new HashMap<Integer,PersonaGiuridicaGeko>();
	
	
	public OrganikoCache(){}

	public Map<Integer, PersonaFisicaGeko> getCachePersonaFisicaById() {return cachePersonaFisicaById;}
	public void setCachePersonaFisicaById(Map<Integer, PersonaFisicaGeko> cachePersonaFisicaById) {
		this.cachePersonaFisicaById = cachePersonaFisicaById;
	}

	public Map<Integer, PersonaGiuridicaGeko> getCachePersonaGiuridicaById() {
		return cachePersonaGiuridicaById;
	}
	public void setCachePersonaGiuridicaById(Map<Integer, PersonaGiuridicaGeko> cachePersonaGiuridicaById) {
		this.cachePersonaGiuridicaById = cachePersonaGiuridicaById;
	}

	public Map<Integer, IncaricoGeko> getCacheIncaricoById() {
		return cacheIncaricoById;
	}
	public void setCacheIncaricoById(Map<Integer, IncaricoGeko> cacheIncaricoById) {
		this.cacheIncaricoById = cacheIncaricoById;
	}

	
} // -------------

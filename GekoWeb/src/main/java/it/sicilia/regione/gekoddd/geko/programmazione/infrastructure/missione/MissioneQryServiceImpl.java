package it.sicilia.regione.gekoddd.geko.programmazione.infrastructure.missione;

import it.sicilia.regione.gekoddd.geko.programmazione.domain.missione.Missione;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.missione.MissioneQryService;
import it.sicilia.regione.gekoddd.geko.programmazione.domain.missione.MissioneRepository;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("missioneQryService")
@Repository
@Transactional
public class MissioneQryServiceImpl implements MissioneQryService{

	// vedi Spring Data Pro Spring 3 p380 al posto di em uso il Repository
	@Autowired 
	private MissioneRepository repo;

	@Transactional(readOnly=true)
	public List<Missione> findAll() {
		return (List<Missione>) repo.findAll();
	}

} // --------------------------------

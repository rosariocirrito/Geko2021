package it.sicilia.regione.gekoddd.geko.programmazione.domain.programma;


import java.util.List;
import java.util.Map;

public interface ProgrammaQryService {
	
	//-----------------------------------
	List<Programma> findAll();
	
	//
	Map<Integer,String> mapSelectProgramma();
}

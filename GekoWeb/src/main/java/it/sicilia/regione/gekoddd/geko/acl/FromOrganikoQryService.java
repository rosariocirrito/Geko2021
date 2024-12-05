/**
 * FromOrganikoQryService è la API di interrogazione del microservizio msOrganiko
 */

package it.sicilia.regione.gekoddd.geko.acl;

import it.sicilia.regione.gekoddd.geko.acl.model.IncaricoGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaFisicaGeko;
import it.sicilia.regione.gekoddd.geko.acl.model.PersonaGiuridicaGeko;

import java.util.List;

import org.springframework.web.client.HttpStatusCodeException;

public interface FromOrganikoQryService {

    // 1. Incarichi
    IncaricoGeko findIncaricoById(Integer id);
    List<IncaricoGeko> findIncarichiApicaliByAssessoratoIDAndAnno(Integer dipartimentoID, int anno) throws HttpStatusCodeException;
    List<IncaricoGeko> findIncarichiApicaliByDipartimentoIDAndAnno(Integer dipartimentoID, int anno) throws HttpStatusCodeException;
    List<IncaricoGeko> findIncarichiByDipartimentoIDAndAnno(Integer dipartimentoID, int anno) throws HttpStatusCodeException ;
    List<IncaricoGeko> findIncarichiDirigenzialiByDipartimentoIDAndAnno(Integer dipartimentoID, int anno) throws HttpStatusCodeException ;
    List<IncaricoGeko> findIncarichiPopByDipartimentoIDAndAnno(Integer strutturaID, int anno) throws HttpStatusCodeException;
    List<IncaricoGeko> findIncarichiPopByIntermediaIDAndAnno(Integer strutturaID, int anno) throws HttpStatusCodeException;
    List<IncaricoGeko> findIncarichiByStrutturaIDAndAnno(Integer strutturaID, int anno) throws HttpStatusCodeException;
    List<IncaricoGeko> findIncarichiByDirigenteIDAndAnno(Integer dirigenteID, int anno)throws HttpStatusCodeException;

    // 2. Persona Fisica
    PersonaFisicaGeko findPersonaFisicaById(Integer id) throws HttpStatusCodeException;
    List<PersonaFisicaGeko> findDipendentiStrictByStrutturaIDAndAnno(int idStruttura, int anno) throws HttpStatusCodeException;
    List<PersonaFisicaGeko> findDipendentiGlobalByStrutturaIDAndAnno(int idStruttura, int anno) throws HttpStatusCodeException;
    List<PersonaFisicaGeko> findDipendentiByDipartimentoIDAndAnno(Integer idDipartimento, int anno) throws HttpStatusCodeException;

    // 3. Persona Giuridica
    PersonaGiuridicaGeko findPersonaGiuridicaById(Integer id) throws HttpStatusCodeException;
    PersonaGiuridicaGeko findDipartimentoByPersonaGiuridicaID(Integer pfID) throws HttpStatusCodeException;
    PersonaGiuridicaGeko findDipartimentoByPersonaFisicaID(Integer pfID) throws HttpStatusCodeException;
    PersonaGiuridicaGeko findAssessoratoByPersonaFisicaID(Integer pfID) throws HttpStatusCodeException;
    List<PersonaGiuridicaGeko> listSubStruttureByDipartimentoIDAndAnno(Integer deptID, int anno) throws HttpStatusCodeException;
    List<PersonaGiuridicaGeko> listAssessoratiAndAnno(int anno) throws HttpStatusCodeException;        
    List<PersonaGiuridicaGeko> listDipartimentiByAssessoratoIDAndAnno(Integer assID, int anno) throws HttpStatusCodeException;
    List<PersonaGiuridicaGeko> findStruttureByStrutturaPadreIDAndAnno(Integer padreID, int anno)  throws HttpStatusCodeException;
    List<PersonaGiuridicaGeko> getStruttureFiglieAttiveOCancellateAnno(Integer padreID, int anno)  throws HttpStatusCodeException;
    List<PersonaGiuridicaGeko> getStruttureDiscendentiAttiveOCancellateAnno(Integer padreID, int anno)  throws HttpStatusCodeException;
}

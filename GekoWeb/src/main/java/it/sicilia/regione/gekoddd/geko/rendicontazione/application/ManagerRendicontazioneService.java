package it.sicilia.regione.gekoddd.geko.rendicontazione.application;

import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;

public interface ManagerRendicontazioneService {
    //
    void managerCreateDocumento(Documento documento);
    void managerUpdateDocumento(Documento documento);
    void managerDeleteDocumento(Documento documento);
    
}

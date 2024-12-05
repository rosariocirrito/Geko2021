package it.sicilia.regione.gekoddd.geko.rendicontazione.application;

import it.sicilia.regione.gekoddd.geko.rendicontazione.domain.documento.Documento;

public interface ResppopRendicontazioneService {
    //
    void popCreateDocumento(Documento documento);
    void popUpdateDocumento(Documento documento);
    void popDeleteDocumento(Documento documento);
    
}

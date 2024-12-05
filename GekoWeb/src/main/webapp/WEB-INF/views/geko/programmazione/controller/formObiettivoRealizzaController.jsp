<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Realizzazione Obiettivo</title>
        <link href="../../resources/css/mask.css" rel="stylesheet"/>
</head>

    <body>
        <div id="wrapper">
            


        <h1>Programmazione da realizzare per ${obiettivo.incarico.opPersonaGiuridica
        .denominazione}</h1>

<form:form  modelAttribute="obiettivo" method="POST">
        

    <fieldset>
        <h2 class="info"> Dati descrittivi  </h2>
    <table width="80%">
        
        
        <tr>
            <td width="20%"><form:label for="codice" path="codice">Codice</form:label></td>
            <td>${obiettivo.codice}</td>
        </tr>
        <tr>
            <td><form:label for="descrizione" path="descrizione">Descrizione</form:label></td>
            <td>${obiettivo.descrizione}</td>
        </tr>
        <tr>
            <td><form:label for="note" path="note">Note</form:label></td>
            <td>${obiettivo.note}</td>
        </tr>
    </table>  
    </fieldset>
        
    <fieldset>
        <h2 class="tipologia">Tipologia Programmazione</h2>
    <table width="80%">
        <tr>
            <td width="20%"><form:label for="anno" path="anno">Anno</form:label></td>
            <td>${obiettivo.anno}</td>
        </tr>
        
        <tr>
            <td><form:label for="tipo" path="tipo">Tipo</form:label></td>
            <td>${obiettivo.tipo}</td>
        </tr>
        </table>  
    </fieldset>
        
    <fieldset>
        <h2 class="stato">Stato della Programmazione / Realizzazione</h2>
        <table  width="80%">
        <tr>
                    <td width="20%"><form:label for="peso" path="peso">Peso</form:label></td>
                    <td>${obiettivo.peso}</td>
                </tr>
                
                <tr>
                <td width="20%"><form:label for="statoApprov" path="statoApprov">Approvazione</form:label></td>
                <td>${obiettivo.statoApprov}</td>
                
                </tr>
             
                
                
                <c:if test="${obiettivo.statoApprov == 'DEFINITIVO'}" >
                <tr>
                    <td ><form:label for="statoRealiz" path="statoRealiz">Realizzazione</form:label></td>
                    <td><form:select path="statoRealiz">
                            <form:option value="DA_REALIZZARE"/>
                            <form:option value="RIS_PARZIALE"/>
                            <form:option value="REALIZZATO"/>
                            <form:option value="MANCATO"/>
                        </form:select></td>
                    <td><form:errors path="statoRealiz" cssClass="error"/></td>
                </tr>
                </c:if>
                
                
        </table>  
    </fieldset>
        
    <fieldset>
        
        <table  width="80%">
            
        <tr>
            <td colspan="3">
                <p class="button"><input type="submit" name="update" value="Aggiorna Realizzazione Obiettivo"/></p>
            </td>
    
            <td>
            <p class="button"><input type="submit" name="cancel" value="Esci"/></p>
            </td>
      
        </tr>
    </table>  
        </fieldset>
      
</form:form>         
        
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - Servizio IX Sistemi di elaborazione dati di Palazzo d'Orleans e dei Siti Presidenziali 
      </td>
    </tr>
    <tr>
        <td><p>[view: formObiettivoRealizzaController.jsp]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>

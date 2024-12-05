<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Valutazione Obiettivo</title>
        <link href="../../../../resources/css/mask.css" rel="stylesheet"/>
</head>

    <body>
        <div id="wrapper">
            
<c:choose>
	<c:when test="${obiettivo.nuovo}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="put"/></c:otherwise>
</c:choose>

        <h1>Programmazione da valutare</h1>

<form:form  modelAttribute="obiettivo" method="${method}">
        

    <fieldset>
        <h2 class="info"> Dati descrittivi  </h2>
    <table width="80%">
        
        
        <tr>
            <td width="20%"><form:label for="denominazione" path="denominazione">Denominazione</form:label></td>
            <td>${obiettivo.denominazione}</td>
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
        <h2 class="stato">Stato della Programmazione</h2>
        <table  width="80%">
            
            
                <tr>
                    <td width="20%"><form:label for="peso" path="peso">Peso</form:label></td>
                    <td>${obiettivo.peso}</td>
                </tr>
                
                <tr>
                <td width="20%"><form:label for="statoApprov" path="statoApprov">Approvazione</form:label></td>
                <td>${obiettivo.statoApprov}</td>
                
                </tr>

                <tr>
                    <td ><form:label for="statoRealiz" path="statoRealiz">Realizzazione</form:label></td>
                    <td>${obiettivo.statoRealiz}</td>
                </tr>
                
                <c:if test="${obiettivo.statoApprov == 'VALIDATO'}" > 
                <tr>
                    <td ><form:label for="statoValut" path="statoValut">Valutazione</form:label></td>
                    <td><form:select path="statoValut">
                        <form:option value="DA_VALUTARE"/>
                        <form:option value="PARZIALE"/>
                        <form:option value="POSITIVA"/>
                        <form:option value="NEGATIVA"/>
                    </form:select></td>
                    <td><form:errors path="statoValut" cssClass="error"/></td>
                </tr>
                </c:if>
        </table>  
    </fieldset>
        
    <fieldset>
        
        <table  width="80%">
            
        <tr>
            <td colspan="3">
                <c:choose>
                    <c:when test="${obiettivo.nuovo}">
                         <p class="button"><input type="submit" name="add" value="Aggiungi Obiettivo"/></p>
                    </c:when>
                    <c:otherwise>
                        <p class="button"><input type="submit" name="update" value="Aggiorna Valutazione Obiettivo"/></p>
                    </c:otherwise>
                </c:choose>
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
        <td><p>[view: formObiettivoValutaController.jsp]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>

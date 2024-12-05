<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Accettazione Obiettivo</title>
        <link href="../../../resources/css/mask.css" rel="stylesheet"/>
</head>

    <body>
        <div id="wrapper">
            
<c:choose>
	<c:when test="${obiettivo.nuovo}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="put"/></c:otherwise>
</c:choose>

        <h1>Programmazione da accettare</h1>

<security:authorize access="hasRole('MANAGER')">
<form:form  modelAttribute="obiettivo" method="${method}">
        

    <fieldset>
        <h2 class="info"> Dati descrittivi  </h2>
    <table width="80%">
        
        
        <tr>
            <td width="20%"><form:label for="codice" path="codice">Codice (es. OPR_1)</form:label></td>
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
        </table>  
    </fieldset>
        
    <fieldset>
    	<h2 class="comandi">Comandi</h2>
        <table  width="80%">
        	<tr>
	            <td colspan="3">
	                <p class="button"><input type="submit" name="update" value="Accetta Obiettivo"/></p>
	            </td>
           		<td>
            		<p class="button"><input type="submit" name="cancel" value="Rinuncia all'accettazione"/></p>
            	</td>
      		</tr>
    	</table>  
     </fieldset>
      
</form:form>         
</security:authorize>
        
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_MANAGER" htmlEscape="true" />">Menu Manager</a>
      Ge.Ko. by Regione Siciliana - ing. R. Cirrito 
      </td>
    </tr>
    <tr>
        <td><p>[view: formObiettivoAccettaManager.jsp 27/07/2015]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>

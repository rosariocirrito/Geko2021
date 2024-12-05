<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Obiettivo</title>
        <link href="<spring:url value="/resources/css/mask.css" htmlEscape="true" />" rel="stylesheet"/>
</head>

    <body>
        <div id="wrapper">
            
<c:choose>
	<c:when test="${obiettivo.nuovo}"><c:set var="method" value="post"/></c:when>
	<c:otherwise><c:set var="method" value="put"/></c:otherwise>
</c:choose>

        <h1>Programmazione interlocutoria/definitiva da proporre/riproporre</h1>

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
	                <p class="button"><input type="submit" name="update" value="Rendi Interlocutorio Obiettivo"/></p>
	            </td>
	            <td>
	            	<p class="button"><input type="submit" name="cancel" value="Rinuncia"/></p>
	            </td>
	      	</tr>
    	</table>  
    </fieldset>
      
</form:form>              
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito 
      </td>
    </tr>
    <tr>
        <td><p>[view: formObiettivoRendiInterlocutorioController.jsp 2019/04/26]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>

<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Validazione Obiettivo</title>
        <link href="../../../resources/css/mask.css" rel="stylesheet"/>
</head>

    <body>
        <div id="wrapper">
            

        <h1>Programmazione apicale </h1>

<security:authorize access="hasRole('CONTROLLER')">
<form:form  modelAttribute="obiettivo" method="put">
        
<fieldset>
        <h2 class="info"> Dati descrittivi  </h2>
    <table width="80%">
        
        
        <tr>
            <td width="20%"><form:label for="codice" path="codice">codice</form:label></td>
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
	        <td width="20%">Apicale</td>
	        <td>
		    	<form:select path="apicale" >
		    	<form:option value="1" label="SI"/>
		    	<form:option value="0" label="NO"/>
		    	</form:select> 
	        </td>
	    	<td><form:errors path="apicale" cssClass="error"/></td>
	    </tr>
        
        <tr>
            <td width="20%"><form:label for="pesoApicale" path="pesoApicale">Peso Apicale</form:label></td>
            <td>
	            ${obiettivo.pesoApicale}
            </td>
        </tr>
        </table>  
        
    </fieldset>
        
<fieldset>
    <h2 class="stato">Stato della Programmazione</h2>
    <table  width="80%">      
        <tr>
            <td width="20%"><form:label for="note" path="note">Approvazione</form:label></td>
            <td>${obiettivo.statoApprov}</td>
        </tr>
     </table>  
</fieldset>
        
<fieldset>
    <h2 class="comandi">Comandi</h2>
    <table  width="80%">
        <tr>
            <td colspan="2">
                <p class="button"><input type="submit" name="update" value="Modifica Apicale"/></p>
            </td>
            <td>
            <p class="button"><input type="submit" name="cancel" value="Rinuncia "/></p>
            </td>
        </tr>
    </table>  
</fieldset>
      
</form:form>         
</security:authorize>        
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito 
      </td>
    </tr>
    <tr>
        <td><p>[view: formObiettivoApicaleController.jsp 2019/04/26]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>

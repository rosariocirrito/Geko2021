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
            

        <h1>Scelta Incarico Dirigenziale per ${obiettivo.descrizione}</h1>

<form:form  modelAttribute="obiettivo" method="put">
        
<fieldset>
        <h2 class="info"> Dati descrittivi  </h2>
    <table width="80%">
        
        
        
        <tr>
            <td><form:label for="descrizione" path="descrizione">Descrizione</form:label></td>
            <td>${obiettivo.descrizione}</td>
        </tr>
        
    </table>  
</fieldset>
        
    
        
<fieldset>
    <h2 class="stato">Scelta Incarico</h2>
    <table  width="80%">      
        <tr>
            <td width="20%"><form:label for="idIncaricoScelta" path="idIncaricoScelta">Incarico Dirigenziale</form:label></td>
            <td> <form:select path="idIncaricoScelta">
            <form:options items="${incarichiAnno}" itemValue="idIncarico" itemLabel="stringa"/>
            </form:select>
            </td>
        </tr>
     </table>  
</fieldset>
        
	
<fieldset>
    <h2 class="comandi">Comandi</h2>
    <table  width="80%">
        <tr>
            <td colspan="2">
                <p class="button"><input type="submit" name="update" value="Aggiorna Incarico Dirigenziale"/></p>
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
        <td><p>[view: formObiettivoAssegnaIncaricoController.jsp 2015/07/16]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>

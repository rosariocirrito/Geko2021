<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
    <title>Maschera Annullamento Obiettivo</title>
    <link href="../../../resources/css/mask.css" rel="stylesheet"/>
</head>

<body>
<div id="wrapper">

<h1>Annulla Obiettivo per ${obiettivo.incarico.opPersonaGiuridica.denominazione}</h1>

<form:form  modelAttribute="obiettivo" method="put">
        
<fieldset>
<h2 class="info"> Dati descrittivi  </h2>
<ul>
	<li>
	    <form:label for="codice" path="codice">codice</form:label>
	    ${obiettivo.codice}
	</li>
	<li>
	    <form:label for="descrizione" path="descrizione">Descrizione</form:label>
	    ${obiettivo.descrizione}
	</li>
	<li>
	    <form:label for="note" path="note">Note</form:label>
	    ${obiettivo.note}
	</li>
</ul>  
</fieldset>
        

<fieldset>
<h2 class="stato">Stato della Programmazione</h2>
<ul>      
    <li>
        <form:label for="note" path="note">Approvazione</form:label>
        ${obiettivo.statoApprov}
    </li>
</ul>  
</fieldset>
        
<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
    <li>
        <input type="submit" name="update" value="Annulla Obiettivo"/>
        <input type="submit" name="cancel" value="Rinuncia ad Annulla Obiettivo"/>
    </li>
</ul>  
</fieldset>
      
</form:form>         
        
<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - Servizio IX Sistemi di elaborazione dati di Palazzo d'Orleans e dei Siti Presidenziali 
      </td>
    </tr>
    <tr>
        <td><p>[view: formObiettivoRespingeController.jsp]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>

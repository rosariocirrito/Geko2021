<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
    <title>Maschera Assegnazione</title>
	<%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
</head>

<body>
<div id="wrapper">

<h3> Assegnazione da Modificare per
    Dipendente 
    ${azioneAssegnazione.opPersonaFisica.cognome}
    ${azioneAssegnazione.opPersonaFisica.nome}
    su ${azioneAssegnazione.azione.descrizione}</h3>

<security:authorize access="hasRole('MANAGER')">
<form:form modelAttribute="azioneAssegnazione" method="put">

<%@ include file="/WEB-INF/views/common/fieldsetAzioneAssegnazioneEdit.jsp" %>
    
<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>    
	   
	   <input type="submit" name="delete" value="Cancella Assegnazione"/>
	</li>
	<li> 
	   <input type="submit" name="cancel" value="Esci"/>
	</li>
</ul>
</fieldset>
   
        
</form:form>
</security:authorize>

<p>Ge.Ko by - Segreteria Generale - ing. R. Cirrito </p>      
<p>[view: formAzioneAssegnazioneEditManager.jsp 2019/05/27]</p>

</div>
</body>

</html>
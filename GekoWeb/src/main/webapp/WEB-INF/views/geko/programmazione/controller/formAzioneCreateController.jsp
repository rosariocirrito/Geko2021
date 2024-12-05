<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
<title>Maschera Azione</title>

<%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
<%@ include file="/WEB-INF/views/common/CKEditor.jsp" %>
    
</head>

<body>
<div id="wrapper">

<h2>Nuova Azione per ${azione.obiettivo.codice} / ${azione.obiettivo.descrizione} </h2>

<form:form modelAttribute="azione" method="post">
    
<%@ include file="/WEB-INF/views/common/fieldsetAzioneCreate.jsp" %>
    
<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>
		<input type="submit" name="add" value="Aggiungi Azione"/>
	</li>
	<li>
		<input type="submit" name="cancel" value="Rinuncia"/>
	</li>
</ul>
</fieldset>    
    

</form:form>

<p>Ge.Ko by - Segreteria Generale - Servizio IX Sistemi di elaborazione dati di Palazzo d'Orleans e dei Siti Presidenziali </p>      
<p>[view: formAzioneCreateController.jsp]</p>  

</div>

</body>

</html>
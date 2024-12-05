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



<security:authorize access="hasRole('MANAGER')">
<form:form modelAttribute="azioneAssegnazione" method="put">

<h3> Assegnazione Dipendente da associare all'obiettivo e non più all'azione
   ${azioneAssegnazione.azione.descrizione}</h3>
    
<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>    
	   <input type="submit" name="update" value="Sposta Assegnazione su Obiettivo"/>
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
<p>[view: formAzioneAssegnazioneSpostaSuObjManager.jsp 2019/05/27]</p>

</div>
</body>

</html>
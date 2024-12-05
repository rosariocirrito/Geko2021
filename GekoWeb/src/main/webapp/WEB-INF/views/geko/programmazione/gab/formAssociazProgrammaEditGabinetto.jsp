<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
    <title>Maschera Modifica Associazione</title>
	<%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
</head>

<body>
<div id="wrapper">

<h3> Associazione da Modificare per Obiettivo Apicale:${associazObiettivi.apicale.descrizione}</h3>

<security:authorize access="hasRole('ROLE_GABINETTO')">
<form:form modelAttribute="associazProgramma" method="put">


<fieldset>
<h2 class="info">Scelta Programma</h2>
<ul>

<li>
        <form:select path="idProgrammaScelto">
    		<form:options items="${mapSelectProgramma}" />
    	</form:select>
</li>


</ul>

</fieldset>
    
<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>    
	   <input type="submit" name="update" value="Modifica Associazione"/>
	   <input type="submit" name="delete" value="Cancella Associazione"/>
	</li>
	<li> 
	   <input type="submit" name="cancel" value="Esci"/>
	</li>
</ul>
</fieldset>
   
        
</form:form>
</security:authorize>

<p>Ge.Ko by - Segreteria Generale - ing. R. Cirrito </p>      
<p>[view: formAssociazProgrammaEditGabinetto.jsp 2019/07/23]</p>

</div>
</body>

</html>
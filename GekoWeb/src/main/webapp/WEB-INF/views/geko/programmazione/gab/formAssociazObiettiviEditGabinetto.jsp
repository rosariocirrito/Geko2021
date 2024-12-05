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
<form:form modelAttribute="associazObiettivi" method="put">


<fieldset>
<h2 class="info">Scelta Obiettivo Strategico</h2>
<ul>
<c:forEach items="${listAssocObjEsistenti}" var="associazEsist" varStatus ="status">
    <li> già esist.: ${associazEsist.strategico.descrizione}</li>
</c:forEach>
<li>
        <form:select path="idObiettivoStrategico">
    		<form:options items="${mapSelectObjStrateg}" />
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
<p>[view: formAssociazObiettiviEditGabinetto.jsp 2019/07/22]</p>

</div>
</body>

</html>
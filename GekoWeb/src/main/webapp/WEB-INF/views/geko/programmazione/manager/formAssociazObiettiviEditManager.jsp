<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
    <title>Maschera Cancella Associazione</title>
	<%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>
</head>

<body>
<div id="wrapper">

<h3> Associazione da Cancellare per Obiettivo: ${associazObiettivi.apicale.descrizione}</h3>

<security:authorize access="hasRole('MANAGER')">
<form:form modelAttribute="associazObiettivi" method="put">


<fieldset>
<h2 class="stato">Scelta Obiettivo Strategico</h2>
<table width="80%">
	<c:forEach items="${listAssocObjEsistenti}" var="associazEsist" varStatus ="status">
	    <tr>
	    
	        <td>${associazEsist.strategico.descrizione}</td>
	    </tr>
	</c:forEach>
    
</table>
</fieldset>
    
<fieldset>
<h2 class="comandi">Comandi</h2>
<ul>
	<li>    
	   
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
<p>[view: formAssociazObiettiviEditManager.jsp 11/07/14]</p>

</div>
</body>

</html>
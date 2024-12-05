<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
    <title>Maschera Associazione</title>
    
    <script type="text/javascript"
        src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <script type="text/javascript"
        src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.1/jquery-ui.min.js"></script>
 
    <link href="../../../resources/css/mask.css" rel="stylesheet"/>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
    
    
</head>

<body>
<div id="wrapper">

<h3>Nuova Associazione per Obiettivo Apicale:${associazObiettivi.apicale.descrizione} </h3>

<!-- security:authorize ifAnyGranted="ROLE_CONTROLLER"> -->
<security:authorize access="hasRole('CONTROLLER')">
<form:form modelAttribute="associazObiettivi" method="post">
    

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
<table  width="80%">
	<tr>
	    <td>             
	    	<p class="button"><input type="submit" name="add" value="Aggiungi Associazione"/></p>
	    </td>
        <td>
            <p class="button"><input type="submit" name="cancel" value="Esci"/></p>
        </td>
      
    </tr>
  </table>
    </fieldset>
        
</form:form>
</security:authorize>
</div>

<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
        <td><p>[view: formAssociazObiettiviCreateController.jsp 2017/02/17]</p></td>
    </tr>
</table>

</div>
</body>

</html>
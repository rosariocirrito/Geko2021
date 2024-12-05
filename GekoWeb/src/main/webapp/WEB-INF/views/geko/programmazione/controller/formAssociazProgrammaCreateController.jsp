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

<h3>Nuova Associazione per Obiettivo Apicale </h3>

<!-- security:authorize ifAnyGranted="ROLE_CONTROLLER"> -->
<security:authorize access="hasRole('CONTROLLER')">
<form:form modelAttribute="associazProgramma" method="post">
    

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
        <td><p>[view: formAssociazProgrammaCreateController.jsp 2019/05/27]</p></td>
    </tr>
</table>

</div>
</body>

</html>
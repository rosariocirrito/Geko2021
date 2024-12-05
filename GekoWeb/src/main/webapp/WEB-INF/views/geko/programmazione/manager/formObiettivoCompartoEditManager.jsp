<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<title>Maschera Modifica Obiettivo</title>

<!-- Bootstrap -->
<link href=<spring:url value="/resources/bootstrap/css/bootstrap.css"  htmlEscape="true" /> rel="stylesheet"/>
	
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
	
<!-- style locale per row e col -->	
<style>
	.row{
		margin-top:16px;
		margin-left:16px;
		background-color:lightgray;
	}
	.col{
		background-color:lightblue;
		padding:8px;
		border:2px solid darkgray;
	}
</style>

</head>


<security:authorize access="hasRole('MANAGER')">

<body>

<div class="container" >
<div class="row">
<div class="col-sm-12 col"> 

<h3 >Programmazione anno ${obiettivo.anno} da modificare </h3>

<form:form  class="form-horizontal" role ="form" modelAttribute="obiettivo" method="put">
        
<!-- Descrizione --> 
<fieldset>
<legend>Dati descrittivi</legend>        
<div class="control-group">
	<form:label class="control-label" for="codice" path="codice">Codice</form:label>
	<div class="controls">
       	<form:input class="form-control" path="codice" maxlength="16"/>
        <form:errors path="codice" cssClass="error"/> 
	</div>        
</div>


<div class="control-group">
	<form:label class="control-label" for="descrizione" path="descrizione">Descrizione</form:label>
	<div class="controls">
       	<form:textarea class="form-control" path="descrizione" maxlength="1000"/>
        <form:errors path="descrizione" cssClass="error"/> 
	</div>        
</div>
<div class="control-group">
	<form:label class="control-label" for="peso" path="peso">Peso</form:label>
	<div class="controls">
       	<form:input class="form-control" path="peso" maxlength="10"/>
        <form:errors path="peso" cssClass="error"/> 
	</div>  
</div>
<div class="control-group">
	<form:label class="control-label" for="note" path="note">Note</form:label>
	<div class="controls">
       	<form:textarea class="form-control" path="note" placeholder="note eventuali max 1000 char" maxlength="1000"/>
        <form:errors path="note" cssClass="error"/> 
	</div>        
</div>


<div class="control-group">
<p class="control-label">Stato</p>
<p class="controls">${obiettivo.statoApprov}</p>
</div>
</fieldset>


<!-- Comandi -->
<fieldset>
<legend>Comandi</legend>        
<div class="control-group">
<div class="controls">
	<button type="submit" class="btn btn-medium btn-primary" name="update">Aggiorna Obiettivo</button>
	<c:if test="${obiettivo.statoApprov == 'INTERLOCUTORIO'}">
		<button type="submit" class="btn btn-medium btn-primary" name="delete">Cancella Obiettivo</button>
	</c:if>
	<button type="submit" class="btn btn-medium btn-warning" name="cancel">Rinuncia ed esci</button>
</div>
</div>       
</fieldset>      

<p>[view: formObiettivoEditManager.jsp 2019/04/26]</p>      
</form:form>         
        


</div> <!-- div col -->
</div> <!-- div row -->
</div> <!-- div container -->

</security:authorize>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</body>

</html>

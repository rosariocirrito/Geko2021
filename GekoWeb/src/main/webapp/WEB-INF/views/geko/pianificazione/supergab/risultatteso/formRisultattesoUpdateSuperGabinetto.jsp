<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<title>Maschera Modifica Outcome</title>

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


<security:authorize access="hasRole('ROLE_SUPERGABINETTO')">

<body>

<div class="container">
<div class="row">
<div class="col-sm-12 col"> 

            
<h3 > Modifica Risultato Atteso per Obiettivo Specifico: ${risultatteso.obiettivoStrategico.descrizione}</h3>
<form:form  class="form-horizontal" role ="form" modelAttribute="risultatteso" method="put">

<!-- Descrizione --> 
<fieldset>
<legend>Dati descrittivi</legend>

<div class="control-group">
	<form:label class="control-label" for="codice" path="indicatore">Codice</form:label>
	<div class="controls">
       	<form:textarea class="form-control" path="codice" placeholder="RAx.y" maxlength="16"/>
        <form:errors path="codice" cssClass="error"/> 
	</div>        
</div>

<div class="control-group">
	<form:label class="control-label" for="anno" path="anno">Anno</form:label>
	<div class="controls">     	
    <form:input class="input-medium" type="text" path="anno"/>
    <form:errors path="anno" cssClass="error"/> 
	</div>        
</div>

<div class="control-group">
	<form:label class="control-label" for="indicatore" path="indicatore">Indicatore</form:label>
	<div class="controls">
       	<form:textarea class="form-control" path="indicatore" placeholder="SI/NO, nr, euro ..." maxlength="128"/>
        <form:errors path="indicatore" cssClass="error"/> 
	</div>        
</div>

<div class="control-group">
	<form:label class="control-label" for="target" path="target">Target</form:label>
	<div class="controls">
       	<form:textarea class="form-control" path="target" placeholder="max 150 char" maxlength="150"/>
        <form:errors path="target" cssClass="error"/> 
	</div>        
</div>

<div class="control-group">
	<form:label class="control-label" for="consuntivo" path="consuntivo">Consuntivo</form:label>
	<div class="controls">
       	<form:textarea class="form-control" path="consuntivo" placeholder="max 150 char" maxlength="150"/>
        <form:errors path="consuntivo" cssClass="error"/> 
	</div>        
</div>

<div class="control-group">
	<form:label class="control-label" for="analisi" path="analisi">Analisi</form:label>
	<div class="controls">
       	<form:textarea class="form-control" path="analisi" placeholder="max 250 char" maxlength="250"/>
        <form:errors path="analisi" cssClass="error"/> 
	</div>        
</div>

</fieldset>

<!-- Comandi -->
<fieldset>
<legend>Comandi</legend>
<div class="control-group">
	<div class="controls">
	<button type="submit" class="btn btn-medium btn-primary" name="update">Modifica Risultato Atteso</button>
	<button type="submit" class="btn btn-medium btn-primary" name="delete">Cancella Risultato Atteso</button>
	<button type="submit" class="btn btn-medium btn-warning" name="cancel">Rinuncia ed esci</button>
	</div>
</div>
</fieldset>

</form:form>         
        
<p>Ge.Ko by ing. R. Cirrito </p>      
<p>[view: formRisultattesoUpdateSuperGabinetto.jsp 2018/12/04]</p>


</div>
  
</security:authorize>
</body>

</html>

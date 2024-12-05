<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<title>Maschera Modifica Priorita Politica</title>

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

            
<h3 > Modifica PrioritaPolitica per Area ${prioritaPolitica.areaStrategica.descrizione}</h3>
<form:form  class="form-horizontal" role ="form" modelAttribute="prioritaPolitica" method="put">

<!-- Descrizione --> 
<fieldset>
<legend>Dati descrittivi</legend>

<div class="control-group">
	<form:label class="control-label" for="codiceAss" path="codiceAss">Fonte</form:label>
	<div class="controls">
       	<form:textarea class="form-control" path="codiceAss" placeholder="fonte" maxlength="512"/>
        <form:errors path="codiceAss" cssClass="error"/> 
	</div>        
</div>
<div class="control-group">
	<form:label class="control-label" for="descrizione" path="descrizione">Descrizione</form:label>
	<div class="controls">
       	<form:textarea class="form-control" path="descrizione" placeholder="descrizione estesa max 512 char" maxlength="512"/>
        <form:errors path="descrizione" cssClass="error"/> 
	</div>        
</div>
</fieldset>

<!-- Comandi -->
<fieldset>
<legend>Comandi</legend>
<div class="control-group">
	<div class="controls">
	<button type="submit" class="btn btn-medium btn-primary" name="update">Modifica la Priorit� Politica</button>
	<button type="submit" class="btn btn-medium btn-primary" name="delete">Cancella la Priorit� Politica</button>
	<button type="submit" class="btn btn-medium btn-warning" name="cancel">Rinuncia ed esci</button>
	</div>
</div>
</fieldset>

</form:form>         
        
<p>Ge.Ko by ing. R. Cirrito </p>      
<p>[view: formPrioritaPoliticaUpdateSuperGabinetto.jsp 2018/01/10]</p>


</div>
  
</security:authorize>
</body>

</html>

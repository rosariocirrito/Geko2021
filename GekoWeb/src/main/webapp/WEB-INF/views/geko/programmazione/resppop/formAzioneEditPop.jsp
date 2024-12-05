<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<title>Maschera Modifica Azione</title>

<%@ include file= "/WEB-INF/views/common/bootstrap4_css.jsp" %>

</head>

<security:authorize access="hasRole('RESP_POP')">

<body>
<div class="container">
<div class="row">
<div class="col-lg-12 col"> 



<h3>Azione da modificare per ${azione.obiettivo.codice} ${azione.obiettivo.descrizione} </h3>

<form:form class="form-horizontal" role ="form"  modelAttribute="azione" method="put">




    
<fieldset>
<legend>Dati descrittivi</legend>
<div class="control-group">
	<form:label class="control-label" for="denominazione" path="denominazione">Codice</form:label>
	<div class="controls">
       	<form:input class="form-control"  path="denominazione" placeholder="OPR_1_1" maxlength="16"/>
        <form:errors path="denominazione" cssClass="error"/> 
	</div>  
</div>

<div class="control-group control-group-lg">
	<form:label class="control-label" for="descrizione" path="descrizione">Descrizione</form:label>
	<div class="controls">
       	<form:textarea class="form-control" rows="3" path="descrizione" placeholder="descrizione estesa max 1000 char" maxlength="1000"/>
        <form:errors path="descrizione" cssClass="error"/> 
	</div>        
</div>

<div class="control-group">
	<form:label class="control-label" for="descrizione" path="descrizione">Descrizione proposta</form:label>
	<div class="controls">
       	${azione.descrizioneProp}
	</div>        
</div>

<div class="control-group">
	<form:label class="control-label" for="note" path="note">Note</form:label>
	<div class="controls">
       	<form:textarea class="form-control" path="note" placeholder="note eventuali max 1000 char" maxlength="1000"/>
        <form:errors path="note" cssClass="error"/> 
	</div>        
</div>

</fieldset> 
    
<fieldset>
<legend>Tipologia Azione</legend>


<div class="control-group">
	<form:label class="control-label" for="indicatore" path="indicatore">Indicatore</form:label>
	<div class="controls">
       	<form:select class="form-control" path="indicatore">
       		<form:option value="Numero"/>
            <form:option value="Euro"/>
            <form:option value="%"/>
            <form:option value="SI/NO"/>
        </form:select>    
        <form:errors path="indicatore" cssClass="error"/> 
	</div>        
</div>

<div class="control-group">
	<form:label class="control-label" for="prodotti" path="prodotti">Valore Obiettivo</form:label>
	<div class="controls">
       	<form:input class="form-control" path="prodotti" placeholder="100 - 500.000 - 20% - SI" maxlength="32"/>
        <form:errors path="prodotti" cssClass="error"/> 
	</div>  
</div>

<div class="control-group">
<form:label class="control-label" for="peso" path="peso">Peso azione</form:label>
	<div class="controls">
      	<form:input class="form-control" path="peso" placeholder="1-100" maxlength="3"/>
       <form:errors path="peso" cssClass="error"/> 
</div>  
</div>


<div class="control-group">
	<form:label class="control-label" for="scadenza" path="scadenza">Scadenza</form:label>
	<div class="controls">
       	<form:input class="datepicker1" data-date-format="dd/mm/yyyy" path="scadenza" maxlength="10"/>
        <form:errors path="scadenza" cssClass="error"/> 
        
	</div>  
</div>




</fieldset>
   

        
<!-- Comandi -->
<fieldset>
<legend>Comandi</legend>
<div class="control-group">
	<div class="controls">
	<button type="submit" class="btn btn-medium btn-primary" name="update">Modifica Azione</button>
	<button type="submit" class="btn btn-medium btn-warning" name="cancel">Rinuncia ed esci</button>
	<button type="submit" class="btn btn-medium btn-primary" name="delete">Cancella Azione</button>
	</div>
</div>
</fieldset>
        
<p>[view: formAzioneEditPop.jsp 2020/12/07 ing. R. Cirrito]</p>        
</form:form>


</div> <!-- div col -->

<script type="text/javascript">
            $(function () {
                $('#datetimepicker1').datetimepicker({
                    format: 'LT'
                });
            });
        </script>

</div> <!-- div row -->
</div> <!-- div container -->

<%@ include file= "/WEB-INF/views/common/bootstrap4_js.jsp" %>

</body>

</security:authorize>

</html>
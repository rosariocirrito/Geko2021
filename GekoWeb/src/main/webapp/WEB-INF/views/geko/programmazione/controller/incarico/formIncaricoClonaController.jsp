<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1" />
<title>Maschera Incarico</title>

<%@ include file="/WEB-INF/views/common/jquery_mask.jsp" %>

</head>

<body>
<div id="wrapper">
                
<c:set var="method" value="put"/>

<h3>
	Incarico da clonare
</h3>



<form:form modelAttribute="incaricoGeko" method="${method}">

    
<fieldset>
<h2 class="info"> Dati Incarico </h2>
<table >
<tr>
	<td>Struttura</td> 
	<td>${incaricoGeko.denominazioneStruttura}</td>
</tr>
<tr>
	<td>Dirigente</td>
	<td>${incaricoGeko.responsabile}</td>
</tr>
<tr>
    
    <td width="20%" ><form:label class="control-label" for="idIncaricoDaClonare" path="idIncaricoDaClonare">Incarico sorgente</form:label></td>
	<td width="80%"><form:select path="idIncaricoDaClonare" class="form-control">
		<form:options items="${incaricoGeko.listaIncarichiDaClonare}" itemValue="idIncarico" itemLabel="stringa"/>
	</form:select>
	</td>
</tr>

    
</table>
</fieldset>
        
  
        
    <fieldset>
        <table  width="80%">
    <tr>  
      
   
            <td>
           <p class="button"><input type="submit" name="update" value="Clona Incarico"/></p>
           </td>
           
      
           
        <td>
            <p class="button"><input type="submit" name="cancel" value="Esci"/></p>
        </td>
      
    </tr>
  </table>
    </fieldset>
        
</form:form>
</div>

<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_CONTROLLER" htmlEscape="true" />">Menu Controller</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
        <td><p>[view: formIncaricoClonaController.jsp 12/07/2019]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>
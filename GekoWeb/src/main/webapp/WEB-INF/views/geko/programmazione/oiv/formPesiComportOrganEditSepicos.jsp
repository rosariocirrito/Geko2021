<%@ include file="/WEB-INF/views/common/includes.jsp" %>

<!DOCTYPE html>
<html lang="it">
    <head>
    <meta charset="ISO-8859-1" />
        <title>Maschera Edit Pesi Comportamenti Organizzativi</title>
        
        <link href="../../resources/css/mask.css" rel="stylesheet"/>
</head>

<body>
<div id="wrapper">



<h3>
	Modifica Pesi Comportamenti Organizzativi per l'incarico di </br> 
	${valutazione.incarico.opPersonaFisica.order} /
	${valutazione.incarico.opPersonaGiuridica.denominazione} /
	Anno: ${valutazione.anno}
</h3>
            

<form:form modelAttribute="valutazione" method="PUT">
    
<fieldset>
<h2 class="info"> Pesi Attribuiti </h2>
<table width="50%">
  	
    
<tr>
	<td><form:label for="analProgrAss" path="analProgrAss">Capacità di Analisi e Programmazione</form:label></td>
	<td><form:input path="analProgrAss" size="4" maxlength="4"/> </td>
	<td><form:errors path="analProgrAss" cssClass="errors"/></td>
	
	<td>range (5-10)</td>
</tr>
    
<tr>
    <td><form:label for="relazCoordAss" path="relazCoordAss">Capacità di Relazione e Coordinamento</form:label></td>
    <td><form:input path="relazCoordAss" size="4" maxlength="4"/> </td>
    <td><form:errors path="relazCoordAss" cssClass="errors"/></td>

    <td>range (5-10)</td>
</tr>
    
<tr>
    <td><form:label for="gestRealAss" path="gestRealAss">Capacità di Gestione e Realizzazione</form:label></td>
    <td><form:input path="gestRealAss" size="4" maxlength="4"/> </td>
    <td><form:errors path="gestRealAss" cssClass="errors"/></td>

    <td>range (5-10)</td>
</tr>
    
</table>
</fieldset> 
    
    
<fieldset>
<table  width="80%">
  <tr>  
	<td><p class="button"><input type="submit" name="update" value="Modifica Pesi"/></p></td>
	<td><p class="button"><input type="submit" name="cancel" value="Esci"/></p></td>
    
  </tr>
</table>
</fieldset>
        
</form:form>
</div>

<table class="footer" >
    <tr>
      <td><a href="<spring:url value="/ROLE_SEPICOS" htmlEscape="true" />">Menu Sepicos</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
        <td><p>[view: formPesiComportOrganEditSepicos.jsp 2016/04/22]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>
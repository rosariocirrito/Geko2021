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
	Modifica Pesi Comportamenti Organizzativi per l'incarico di <br /> 
	${valutazione.incarico.responsabile} /
	${valutazione.incarico.denominazioneStruttura} /
	Anno: ${valutazione.anno}
</h3>
            

<form:form modelAttribute="valutazione" method="PUT">
    
<fieldset>
<h2 class="info"> Pesi Attribuiti </h2>
<table >
  	<tr>
    <td><form:label for="gestRealAss" path="gestRealAss">Capacità di intercettare, gestire risorse e programmare</form:label></td>
    <td><form:input path="gestRealAss" size="2" maxlength="2"/> </td>
    <td><form:errors path="gestRealAss" cssClass="errors"/></td>

    <td>range (0 o compreo tra 5 e 20)</td>
</tr>
    
<tr>
	<td><form:label for="analProgrAss" path="analProgrAss">Promozione strumenti analisi...</form:label></td>
	<td><form:input path="analProgrAss" size="2" maxlength="2"/> </td>
	<td><form:errors path="analProgrAss" cssClass="errors"/></td>
	
	<td>range (0 o compreo tra 5 e 20)</td>
</tr>
    
<tr>
    <td><form:label for="relazCoordAss" path="relazCoordAss">Capacita' di valorizzare competenze...</form:label></td>
    <td><form:input path="relazCoordAss" size="2" maxlength="2"/> </td>
    <td><form:errors path="relazCoordAss" cssClass="errors"/></td>

    <td>range (0 o compreo tra 5 e 20)</td>
</tr>
    <tr>
        <td><form:label for="pdlAss" path="pdlAss">Capacita' individuazione livello priorità</form:label></td>
       <td><form:input path="pdlAss" size="2" maxlength="2"/> </td>
       <td><form:errors path="pdlAss" cssClass="errors"/></td>
        
       <td>range (0 o compreo tra 5 e 20)</td>  
        
    </tr>

    
</table>
</fieldset> 
    
    
<fieldset>
<table >
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
      <td><a href="<spring:url value="/ROLE_GABINETTO" htmlEscape="true" />">Menu GABINETTO</a>
      Ge.Ko. by Regione Siciliana - Segreteria Generale - ing. R. Cirrito </td>
    </tr>
    <tr>
        <td><p>[view: form2019PesiComportOrganEditGabinetto.jsp 2019/07/09]</p></td>
    </tr>
  </table>

  </div>
</body>

</html>
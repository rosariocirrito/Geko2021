<!-- CKEditor -->
<spring:url value="/resources/ckeditor/ckeditor.js" var="ckeditor_url" />
<spring:url value="/resources/ckeditor/adapters/jquery.js" var="ckeditor_jquery_url" />
<script type="text/javascript" src="${ckeditor_url}"><jsp:text/></script>
<script type="text/javascript" src="${ckeditor_jquery_url}"><jsp:text/></script>
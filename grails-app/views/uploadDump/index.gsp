
<%@ page import="np.com.pantbinod.UploadDump" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'uploadDump.label', default: 'UploadDump')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>

	<div class="main-container">


		<div class="row">
			<g:if test="${message}">
				<div class="col-lg-12">
					<g:if test="${flash}">
						<div class="alert alert-success">
							<strong>Success!</strong> ${message}
						</div>
					</g:if>
					<g:else>
						<div class="alert alert-danger">
							<strong>Error!</strong> ${message}
						</div>
					</g:else>

				</div>
			</g:if>
		</div>


		<g:if test="${isDBError}">
			<g:each in="${databaseList}" var="c">

				<div class="col-md-12">
					<div class="card bg-primary text-white mb-4">
						<div class="card-body text-center">${c}</div>
					</div>
				</div>
			</g:each>
		</g:if>
		<g:else>
		<form action="${createLink(controller: 'uploadDump', action: 'uploadOnFly')}" method="post"  enctype='multipart/form-data'
		name="uploadDump"
		>

			<div class="form-group" style="color: green">
				<p class="h3">Upload Sql file</p>
			</div>
%{--			<div class="form-group">--}%
%{--				<label for="dbname">SQL Dump</label>--}%
%{--				<g:select name="dbname" class="form-control"  noSelection="${['':'Select Database']}"--}%
%{--						 id="dbname" from="${databaseList}"/>--}%
%{--			</div>--}%

			<div class="form-group">
				<label for="sqldump">SQL Dump</label>
				<input type="file" class="form-control" id="sqldump" name="dumpsql">
			</div>

			<div class="form-group">
				<input type="submit" value="upload" class="btn btn-success btn-upload"/>

			</div>

		</form>
			<g:form  controller="file" action="getItemsFromMongo">
				<select id="MyDomains" name="checkedDomains" class="js-select2" multiple="multiple">
					<g:each in="${allTableNames}" status="i" var="tbl">
						<option>${tbl}</option>
					</g:each>
				</select>

				<br><br>
				<button class="custom-button submit">Insert selected items</button>
			</g:form>
		</g:else>
	</div>
	<script>

		$(document).ready(function () {
			$("form[name='uploadDump']").validate({
				rules:{
					dbname: {
						required: true
					},
					dumpsql: {
						required: true
					}
				},
				messages:{
					dbname: "Please select database",
					dumpsql: "Please upload dump sql files"
				}
			})
		});

	</script>

	</body>
</html>

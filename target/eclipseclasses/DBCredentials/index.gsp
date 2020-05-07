
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
				<g:if test="${flash && isDB}">
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
	<form action="${createLink(controller: 'DBCredentials', action: 'update')}" method="post" name="dbCredentials">

		<div class="form-group" style="color: green">
			<p class="h3">Update Database Username and Password</p>
		</div>
		<div class="form-group">
			<label for="dbusername">DB username</label>
			<input type="text" class="form-control" id="dbusername" name="username">
		</div>


		<div class="form-group">
			<label for="password">DB Password</label>
			<input type="password" class="form-control" id="password" name="password">
		</div>

		<div class="form-group">
			<label for="confirmPassword">Confirm DB Password</label>
			<input type="password" class="form-control" id="confirmPassword" name="confirmPassword">
		</div>


		<div class="form-group">
			<input type="submit" value="Update Password" class="btn btn-success btn-upload"/>
		</div>

	</form>

</div>

<script>
	$(document).ready(function () {

		$(document).ready(function () {
			$("form[name='dbCredentials']").validate({
				rules:{
					username: {
						required: true
					},
					password: {
						required: true
					},
					confirmPassword:{
						equalTo:"#password"
					}
				},
				messages:{
					username: "Please enter username",
					password: "Please enter password",
					confirmPassword: "Please confirm password"
				}
			})
		});
	})
</script>

</body>
</html>

<%@ page import="np.com.pantbinod.UploadDump" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'uploadDump.label', default: 'UploadDump')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>

<div class="container-fluid">
    <div class="row">
        <g:if test="${message}">

            <div class="col-lg-12 nav-breadcrum">
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

    <h1 class="mt-4">Database List</h1>

    <div class="row">
        <g:each in="${databaseList}" var="c">
            <g:if test="${isDBError}">
            <div class="col-md-12">
                    <div class="card bg-primary text-white mb-4">
                        <div class="card-body text-center">${c}</div>
                    </div>
            </div>
            </g:if>
            <g:else>
                <div class="col-xl-3 col-md-6">
                    <a href="${createLink(controller: 'uploadDump', action: 'viewTables', params: [name: c])}">
                        <div class="card bg-primary text-white mb-4">
                            <div class="card-body">${c}</div>
                        </div>
                    </a>
                </div>
            </g:else>
        </g:each>
    </div>
</div>
</body>
</html>

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

        <div class="col-lg-12 nav-breadcrum">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a
                            href="${createLink(controller: 'uploadDump', action: 'viewDatabase')}">Home</a></li>
                    <li class="breadcrumb-item active" aria-current="page"><b>${databasename}</b></li>
                </ol>
            </nav>
        </div>
    </div>

    <div class="row">
        <div class="col-md-2 pull-right">

        </div>
    </div>

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
        <g:each in="${tableList}" var="c">

            <div class="col-md-12">
                <div class="card bg-primary text-white mb-4">
                    <div class="card-body text-center">${c}</div>
                </div>
            </div>
        </g:each>
    </g:if>
    <g:else>

        <form action="${createLink(controller: 'uploadDump', action: 'exportTables')}" method="post" id="formTable">
            <input type="hidden" name="currentDatabase" value="${databasename}"/>

            <div class="row">
                <div class="col-lg-10 main-table">
                    <g:if test="${tableList.size() > 0}">
                        <div class="row btn-export">
                            <input type="submit" value="Export" class="btn btn-primary"/>
                        </div>

                        <table width="100%" class="table table-striped" id="tableList">
                            <thead>
                            <tr><th><label class="checkbox-inline">
                                <input type="checkbox" id="select-all-table"> Select All

                            </label></th>
                                <th>Table Name</th></tr>
                            </thead>
                            <tbody>

                            <tr>
                        <g:each in="${tableList}" var="c">

                            <td><input type="checkbox" name="tableList" value="${c}"/>
                            </td>
                            <td>${c}</td>
                            </tr>
                        </g:each>

                        </tbody>
                    </g:if>

                    <g:else>
                        <div class="col-md-12">
                            <div class="card bg-primary text-white mb-4">
                                <div class="card-body text-center">There are no tables in that database</div>
                            </div>
                        </div>
                    </g:else>
                </table>
                </div>

            </div>
        </form>

    </g:else>

</div>
<script>

    $(document).ready(function () {

        $('#tableList').DataTable();
        $('#select-all-table').click(function () {
            if ($("#select-all-table").prop("checked") == true) {
                $('input[type="checkbox"]').prop("checked", true)
            } else if ($("#select-all-table").prop("checked") == false) {
                $('input[type="checkbox"]').prop("checked", false)
            }
        })
    })
</script>
</body>
</html>

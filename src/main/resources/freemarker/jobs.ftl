<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">
    <div class="row">
        <h2>Stress Test <small>${title}</small></h2>
        <#include "navigation.ftl">
    </div>


    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading">Test Overview</div>
        <div class="panel-body">
        <#list metrics.getTestOverview() as job>
            <p><a href="/jobs/stop/${job?keep_before(" ")}"><span class="glyphicon glyphicon-remove"></span> ${job}</p></a>
        </#list>
        </div>
    </div>


</div>

<#include "footer.ftl">

</body>
</html>
<!DOCTYPE html>
<html lang="en">
<#include "includes/header.ftl">
<body>

<div class="container">

    <h3>${applicationTitle} <small>${title}</small></h3>
    <#include "includes/navigation.ftl">


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

<#include "includes/footer.ftl">

</body>
</html>
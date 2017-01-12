<!DOCTYPE html>
<html lang="en">
<#include "includes/header.ftl">
<body>
    <div class="container">
        <#include "includes/navigation.ftl">

        <div class="card">
            <div class="card-header">Test Overview</div>
            <div class="card-block">
            <#list metrics.getTestOverview() as job>
                <p><i class="icon ion-close-circled"></i> <a href="/jobs/stop/${job?keep_before(" ")}">${job}</a></p>
            </#list>
            </div>
        </div>

    </div>

    <#include "includes/footer.ftl">
</body>
</html>
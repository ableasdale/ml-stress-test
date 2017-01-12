<!DOCTYPE html>
<html lang="en">
<#include "includes/header.ftl">
<body>

<div class="container">
    <#include "includes/navigation.ftl">


    <p class="lead">This test was started on <strong>${metrics.getTestDateTime()?datetime}</strong> with <strong>${metrics.getTotalHosts()}</strong> hosts</p>


    <div class="card">
        <div class="card-header">Test Overview</div>
        <div class="card-block">
            <dl class="row">
                <dt class="col-sm-3">Test label</dt>
                <dd class="col-sm-9">${metrics.getTestLabel()}</dd>
                <dt class="col-sm-3">Test Composition</dt>
                <#list metrics.getTestOverview() as job>
                    <#if job?is_first><dd class="col-sm-9"><#else><dd class="col-sm-9  offset-sm-3"></#if>${job}</dd>
                </#list>
            </dl>
        </div>
    </div>

    <#assign keys = chartMap?keys>
    <#list keys as key>
        <div id="${key}"></div>
    </#list>

    <!-- add  disabled -->

    <!-- Small modal data-toggle="modal" data-target=".bs-example-modal-sm" -->

    <!-- div class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                ...
            </div>
        </div>
    </div -->
</div>

<#include "includes/footer.ftl">

</body>
</html>

<script>
    $(function() {
        <#list keys as key>
            var chart${key} = c3.generate({
                bindto: '#${key}',
                size: {height: 500},
                axis: {
                    x: { label: '${key} interval' },
                    y: { label: 'Response times (seconds)' }
                },
                data: {
                    type: 'spline',
                    columns: [
                        ${chartMap[key]}
                    ]
                }
            });
        </#list>
    });
</script>
<!DOCTYPE html>
<html lang="en">
<#include "includes/header.ftl">
<body>

<div class="container">

    <h2>${applicationTitle} <small>${title}</small></h2>
    <#include "includes/navigation.ftl">


    <h3><small>Test label: </small>${metrics.getTestLabel()}</h3>
    <h3><small>Started at: </small>${metrics.getTestDateTime()?datetime}</h3>
    <h3><small>Hosts in test: </small>${metrics.getTotalHosts()}</h3>

    <div class="card">
        <div class="card-header">Test Overview</div>
        <div class="card-block">
        <#list metrics.getTestOverview() as job>
            <p>${job}</p>
        </#list>
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
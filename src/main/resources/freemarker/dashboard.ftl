<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">
    <div class="row">
        <h2>Stress Test <small>${title}</small></h2>
        <#include "navigation.ftl">
    </div>

    <div class="row">
        <h4>Test label: </small>${metrics.getTestLabel()}</small></h4>
        <h4>Started at: </small>${metrics.getTestDateTime()?datetime}</small></h4>
        <h4>Hosts in test: </small>${metrics.getTotalHosts()}</small></h4>

    <#assign keys = chartMap?keys>

    <#list keys as key>
        <div id="${key}"></div>
    </#list>
    </div>

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

<#include "footer.ftl">

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
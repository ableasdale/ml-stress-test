<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">
    <div class="row">
        <h2>MarkLogic Stress Test Tool <small>${title}</small></h2>
        <#include "navigation.ftl">
    </div>

    <div class="row">
        <h4>Test label: </small>${metrics.getTestLabel()}</small></h4>
        <h4>Date / Time: </small>${metrics.getTestDateTime()?datetime}</small></h4>
        <h4>Total Hosts: </small>${metrics.getTotalHosts()}</small></h4>
        <div id="chart"></div>
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
        var chart = c3.generate({
            bindto: '#chart',
            size: {
                height: 500
            },
            axis: {
                x: { label: 'Ping interval' },
                y: { label: 'Response times (seconds)' }
            },
            data: {
                type: 'spline',
                columns: [
                    ${chart}
                ]
            }
        });
    });
</script>
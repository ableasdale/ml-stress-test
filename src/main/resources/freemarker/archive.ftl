<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">
    <div class="row">
        <h2>Stress Test <small>${title}</small></h2>
        <#include "navigation.ftl">
    </div>

    <h3>Saved Stress Tests</h3>
    <div class="row">

        <table class="table table-bordered table-striped tablesorter-bootstrap">
            <thead>
            <tr>
                <th>Filename</th>
                <th>Length</th>
                <th>Last Modified</th>
            </tr>
            </thead>
            <tbody>
            <#list files as file>

            <tr>
                <td><a href="/archive/load/${file.getName()}">${file.getName()}</a></td>
                <td>${file.length()}</td>
                <td>${file.lastModified()?number_to_datetime}</td>

            </tr>

            </#list>
            </tbody>
        </table>

        <script>
            var $table = $('table'),
                    process = false;

            $('.error').click(function () {
                $.tablesorter.showError($table, 'This is the error row');
            });

            $('.process').click(function () {
                process = !process;
                $.tablesorter.isProcessing($table, process);
            });

            $('.sortable').click(function () {
                $table
                        .find('.tablesorter-header:last').toggleClass('sorter-false')
                        .trigger('update');
            });

            $table.tablesorter({
                theme: 'bootstrap',
                headerTemplate: '{content} {icon}',
                sortList: [[0, 0], [1, 0], [2, 0]],
                widgets: ['zebra', 'columns', 'filter', 'uitheme']
            });
        </script>


        <!-- div class="list-group">


            <#list filenames as fname>
                <a href="/archive/load/${fname}"><button type="button" class="list-group-item">${fname}</button></a>
            </#list>
        </div -->
    </div>

</div>

<#include "footer.ftl">

</body>
</html>
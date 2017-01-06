<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">
    <div class="row">
        <h2>MarkLogic Stress Test Tool <small>${title}</small></h2>
        <#include "navigation.ftl">
    </div>

    <h3>Saved Stress Tests
        <small>Click on headers to sort</small>
    </h3>

    <div class="row">

        <table class="table table-bordered table-striped tablesorter-bootstrap">
            <thead>
            <tr>
                <th>Filename</th>
                <th>Test Label</th>
                <th>Length (KB)</th>
                <th>Last Modified</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody>
            <#list files as file>

            <tr>
                <td><a href="/archive/load/${file.getName()}">${file.getName()}</a></td>
                <td><strong>${file.getName()?keep_before("-")}</strong></td>
                <td><#setting number_format="0.#">${file.length() / 1024}</td>
                <td>${file.lastModified()?number_to_datetime}</td>
                <td><a href="/archive/delete/${file.getName()}"><span class="glyphicon glyphicon-trash"> </span></a></td>
            </tr>

            </#list>
            </tbody>
        </table>

        <script>
            $(function () {
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
            });
        </script>
        <script src='http://mottie.github.com/tablesorter/js/jquery.tablesorter.js'></script>
        <!-- script src='http://mottie.github.com/tablesorter/js/jquery.tablesorter.widgets.js'></script -->
        <script src='http://mottie.github.com/tablesorter/addons/pager/jquery.tablesorter.pager.js'></script>
    </div>

</div>

<#include "footer.ftl">

</body>
</html>
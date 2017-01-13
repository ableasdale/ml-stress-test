<!DOCTYPE html>
<html lang="en">
<#include "includes/header.ftl">
<body>
    <div class="container">
        <#include "includes/navigation.ftl">

        <h4>Saved Stress Tests <small class="text-muted">Click on headers to sort</small></h4>
        <table class="table table-bordered table-striped display-none">
            <thead>
                <tr>
                    <th>Filename</th>
                    <th>Test Label</th>
                    <th>Filesize (KB)</th>
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
                    <td><a href="/archive/delete/${file.getName()}"><i class="icon ion-ios-trash-outline"></i></a></td>
                </tr>
                </#list>
            </tbody>

            <tfoot>
                <tr>
                    <th>Filename</th>
                    <th>Test Label</th>
                    <th>Filesize (KB)</th>
                    <th>Last Modified</th>
                    <th>Delete</th>
                </tr>
                <tr>
                    <th colspan="5" class="ts-pager">
                        <div class="form-inline">
                            <div class="btn-group btn-group-sm mx-1" role="group">
                                <button type="button" class="btn btn-secondary first" title="first">⇤</button>
                                <button type="button" class="btn btn-secondary prev" title="previous">←</button>
                            </div>
                            <span class="pagedisplay"></span>
                            <div class="btn-group btn-group-sm mx-1" role="group">
                                <button type="button" class="btn btn-secondary next" title="next">→</button>
                                <button type="button" class="btn btn-secondary last" title="last">⇥</button>
                            </div>
                            <select class="form-control-sm custom-select px-1 pagesize" title="Select page size">
                                <option selected="selected" value="10">10</option>
                                <option value="20">20</option>
                                <option value="30">30</option>
                                <option value="all">All Rows</option>
                            </select>
                            <select class="form-control-sm custom-select px-4 mx-1 pagenum" title="Select page number"></select>
                        </div>
                    </th>
                </tr>
            </tfoot>
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

                            widgets: ["filter", "columns", "zebra"],

                            widgetOptions: {
                                // using the default zebra striping class name, so it actually isn't included in the theme variable above
                                // this is ONLY needed for bootstrap theming if you are using the filter widget, because rows are hidden
                                zebra: ["even", "odd"]
                            }

                        })
                        .tablesorterPager({

                            // target the pager markup - see the HTML block below
                            container: $(".ts-pager"),

                            // target the pager page select dropdown - choose a page
                            cssGoto: ".pagenum",

                            // remove rows from the table to speed up the sort of large tables.
                            // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
                            removeRows: false,

                            // output string - default is '{page}/{totalPages}';
                            // possible variables: {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
                            output: '{startRow} - {endRow} / {filteredRows} ({totalRows})'

                        });
                // unhide when ready
                $('.display-none').show();
            });
        </script>
    </div>
    <#include "includes/footer.ftl">
</body>
</html>
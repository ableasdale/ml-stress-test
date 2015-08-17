<head>
    <style type="text/css">@charset "UTF-8";[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide:not(.ng-animate){display:none !important;}ng\:form{display:block;}</style>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>MarkLogic Fragment Counts - ${title}</title>

    <link rel="stylesheet" type="text/css" href="/vendor/c3.min.css" />
    <link rel="stylesheet" type="text/css" href="/vendor/styles.css" />
    <script type="text/javascript" src="http://gabelerner.github.io/canvg/rgbcolor.js"></script>
    <script type="text/javascript" src="http://gabelerner.github.io/canvg/StackBlur.js"></script>
    <script type="text/javascript" src="http://gabelerner.github.io/canvg/canvg.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" />

    <script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.10/c3.min.js"></script>


    <!-- TODO - old code from other project -->
<#if title = "Summary View" || title = "Detail View">
    <script>
        $(document).keyup(function (e) {
            var currentLocation = parseInt(window.location.href.substring(window.location.href.lastIndexOf('/') + 1, window.location.href.length));
            var uriBase = window.location.href.substring(window.location.href.lastIndexOf('/') + 1, -1);
            if (e.keyCode === 37) {
                if (currentLocation != 0) {
                    window.location.replace(uriBase + (currentLocation - 1));
                }
            }
            if (e.keyCode === 39) {
                if (currentLocation < ${total} -1) {
                    window.location.replace(uriBase + (currentLocation + 1));
                }
            }
        });
    </script>
</#if>

</head>
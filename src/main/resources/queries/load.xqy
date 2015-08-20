xquery version "1.0-ml";

declare function local:xml($pos) as element(xml){
    <xml>
        <id>{xdmp:random()}</id>
        <dateTime>{fn:current-dateTime()}</dateTime>
        <!-- timestamp>{xdmp:request-timestamp()}</timestamp -->
        <random>{(xdmp:random() div 2)}</random>
        <pos>{$pos}</pos>
    </xml>
};

for $i in 1 to 100
return
xdmp:spawn-function(
  function(){xdmp:document-insert(fn:concat("/",xdmp:random(),".xml"), local:xml($i)), xdmp:commit()},
  <options xmlns="xdmp:eval">
    <transaction-mode>update</transaction-mode>
  </options>
)
xquery version "1.0-ml";

(: Simple "ping" script - identifies the number of fragments inserted within a space of a minute and also returns the time taken to execute the query :)

let $start := xs:dateTime(fn:current-dateTime()) - xs:dayTimeDuration('PT2M')
let $end := xs:dateTime(fn:current-dateTime()) - xs:dayTimeDuration('PT1M')

return

fn:count(
cts:search(doc(),
  cts:and-query((
   cts:element-range-query(xs:QName("dateTime"), ">",
      $start),
   cts:element-range-query(xs:QName("dateTime"), "<",
      $end))))
),xdmp:elapsed-time()

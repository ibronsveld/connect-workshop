'use strict';
var data = require('./data');

exports.searchGET = function (args, res, next) {
  /**
   * Searches for a term
   * Searches for a term. Optional query param of **size** determines size of returned array
   *
   * query String Term to search for
   * size Double Size of array (optional)
   * returns List
   **/
  // TODO IMPLEMENT
  var size = args["size"].value || 10;
  var query = args["query"].value;

  if (Object.keys(examples).length > 0) {
    res.setHeader('Content-Type', 'application/json');
    res.end(JSON.stringify(examples[Object.keys(examples)[0]] || {}, null, 2));
  } else {
    res.end();
  }
}


function contains(value1, value2) {
  if (isNaN(value1)) {
    return (value1.toLowerCase().includes(value2.toLowerCase()));
  } else {
    return (value1 == Number.parseInt(value2));
  }
}
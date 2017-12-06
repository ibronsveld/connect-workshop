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

  var docs = data.getDocuments(1000);

  var size = args["size"].value || 10;
  var query = args["query"].value;

  if (query !== undefined) {
    // Generate an array of items
    var list = [];

    docs.forEach(function (item) {
      if (contains(item.id, query) || contains(item.name, query) || contains(item.extendedData.description, query)) {
        list.push(item);
      }
    });

    if (list.length > size) {
      list.splice(size, list.length - size);
    }

    console.log(list);
    res.setHeader('Content-Type', 'application/json');
    res.end(JSON.stringify(list, null, 2));

  } else {
    res.status(404).end();
  }


}


function contains(value1, value2) {
  if (isNaN(value1)) {
    return (value1.toLowerCase().includes(value2.toLowerCase()));
  } else {
    return (value1 == Number.parseInt(value2));
  }
}
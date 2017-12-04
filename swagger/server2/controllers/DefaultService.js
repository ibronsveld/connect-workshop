'use strict';

exports.searchGET = function(args, res, next) {
  /**
   * Searches for a term
   * Searches for a term. Optional query param of **size** determines size of returned array 
   *
   * query String Term to search for
   * size Double Size of array (optional)
   * returns List
   **/

  var Client = require('node-rest-client').Client;

  var client = new Client();

  var query = args["query"].value;

  // direct way
  client.get("http://omdbapi.com?s=" + query + "&apikey=351410a9" , function (data, response) {
    // parsed response body as js object
    console.log(data);
    res.setHeader('Content-Type', 'application/json');
    res.end(JSON.stringify(data, null, 2));

  });
}


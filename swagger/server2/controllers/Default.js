'use strict';

var url = require('url');

var Default = require('./DefaultService');

module.exports.searchGET = function searchGET (req, res, next) {
  Default.searchGET(req.swagger.params, res, next);
};

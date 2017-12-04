'use strict';
var faker = require('faker');
faker.fake();

module.exports = {
  getDocument: function (id) {
    var object = {};
    object.id = id;
    object.name =  faker.commerce.productName();
    object.description = faker.random.words(20);

    object.extendedData  =  {
      name: object.name,
      description: object.description,
      id: id,
      image: 'https://placeimg.com/640/480/any'
    };
    return object;
  },
  getDocuments: function (maxItems) {
    var tmpList = [];

    for (var i = 0; i < maxItems; i++) {
      var id = faker.random.number({min:2000, max:10000});
      var ProdData = this.getDocument(id);
      tmpList.push(ProdData);
    }
    return tmpList;
  }

};




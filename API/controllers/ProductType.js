const ProductTypeModel = require("../models/ProductType");

class ProductType {
  getAllProductTypes(req, res, next) {
    ProductTypeModel.find({})
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
}
module.exports = new ProductType();

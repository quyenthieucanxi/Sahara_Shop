const StoreModel = require("../models/Store");
const ProductModel = require("../models/Product");

class Store {
  getAllStores(req, res, next) {
    StoreModel.find({})
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }

  getStoreById(req, res, next) {
    StoreModel.findById(req.params.id)
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }

  getStoreByName(req, res, next) {
    StoreModel.find({ name: req.body.name })
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }

  getStoreByProductId(req, res, next) {
    const productId = req.params.productId;
    ProductModel.findOne({ _id: productId })
      .then((result) => {
        StoreModel.findOne({ _id: result.storeId })
          .then((result) => {
            res.json(result);
          })
          .catch((err) => {
            res.json(err);
          });
      })
      .catch((err) => {
        res.json(err);
      });
  }
}
module.exports = new Store();

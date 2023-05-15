const DiscountModel = require("../models/Discount");

class Discount {
  getAllDiscounts(req, res, next) {
    DiscountModel.find({})
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
}
module.exports = new Discount();

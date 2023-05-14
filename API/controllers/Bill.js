const BillModel = require("../models/Bill");
const ProductModel = require("../models/Product");
const CartModel = require("../models/Cart");
class Bill {
  getAllBillByUserId(req, res, next) {
    BillModel.find({ userId: req.params.userId })
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }

  add(req, res, next) {
    const bill = new BillModel(req.body);
    bill.save();
    res.json(bill);
  }
}
module.exports = new Bill();

const BillModel = require("../models/Bill");
const ProductModel = require("../models/Product");
const CartModel = require("../models/Cart");
class Bill {
  getAllBillByUserId(req, res, next) {
    BillModel.find({ userId: req.params.userId })
      .sort({ date: -1 })
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

  getAllBill(req, res, next) {
    BillModel.find({ status: req.query.status })
      .sort({ date: -1 })
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }

  update(req, res, next) {
    BillModel.findByIdAndUpdate({ _id: req.params.id }, req.body)
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
}
module.exports = new Bill();

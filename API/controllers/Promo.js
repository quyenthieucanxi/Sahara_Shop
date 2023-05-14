const PromoModel = require("../models/Promo");

class Promo {
  getAllPromos(req, res, next) {
    PromoModel.find({}, { updatedAt: 0, createdAt: 0, __v: 0 })
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
  getTopPromos(req, res, next) {
    PromoModel.find({}, { updatedAt: 0, createdAt: 0, __v: 0 })
      .limit(parseInt(req.query.limit))
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }

  add(req, res, next) {
    const newPromo = new PromoModel(req.body);
    console.log(newPromo);
    newPromo
      .save()
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
}
module.exports = new Promo();

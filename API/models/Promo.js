const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const Promo = new Schema(
  {
    productId: { type: String },
    type: { type: String },
    expirationDate: { type: String },
    state: { type: Boolean, default: true },
  },
  {
    timestamps: true,
  }
);

module.exports = mongoose.model("Promo", Promo);

const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const Bill = new Schema(
  {
    userId: { type: String },
    cartId: { type: String },
    productName: { type: String },
    phone: { type: String },
    address: { type: String },
    price: { type: Number },
    quantity: { type: Number },
    date: { type: Date, default: Date.now },
  },
  {
    timestamps: true,
  }
);

module.exports = mongoose.model("bill", Bill);

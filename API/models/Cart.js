const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const Cart = new Schema(
  {
    userId: { type: String },
    productId: { type: String },
    quantity: { type: Number },
    state: { type: String, default: "Unpaid" },
  },
  {
    timestamps: true,
  }
);

module.exports = mongoose.model("Cart", Cart);

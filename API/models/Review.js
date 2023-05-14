const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const Review = new Schema(
  {
    userId: { type: String },
    productId: { type: String },
    content: { type: String },
    time: { type: String },
    state: { type: Boolean },
  },
  {
    timestamps: true,
  }
);

module.exports = mongoose.model("Review", Review);

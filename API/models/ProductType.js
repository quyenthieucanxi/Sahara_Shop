const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const ProductType = new Schema(
  {
    name: { type: String },
    image: { type: String },
    state: { type: Boolean },
  },
  {
    timestamps: true,
  }
);

module.exports = mongoose.model("ProductType", ProductType);

const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const Product = new Schema(
  {
    storeId: { type: String },
    typeId: { type: String },
    name: { type: String },
    price: { type: Number },
    image: { type: String },
    defaultImage: { type: Number },
    detail: { type: String },
    star: { type: Number },
    state: { type: Boolean },
  },
  {
    timestamps: true,
  }
);

module.exports = mongoose.model("Product", Product);

const mongoose = require("mongoose");

const Schema = mongoose.Schema;

const MenuItem = new Schema(
  {
    discountImageID: { type: Number },
    title: { type: String },
    bgImageID: { type: String },
  },
  {
    timestamps: true,
  }
);

module.exports = mongoose.model("MenuItem", MenuItem);

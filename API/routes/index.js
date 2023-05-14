const account = require("./account");
const bill = require("./bill");
const cart = require("./cart");
const discount = require("./discount");
const notification = require("./notification");
const product = require("./product");
const productType = require("./productType");
const promo = require("./promos");
const review = require("./review");
const store = require("./store");
const user = require("./user");

function route(app) {
  app.use("/cart", cart);
  app.use("/account", account);
  app.use("/bill", bill);
  app.use("/discount", discount);
  app.use("/notification", notification);
  app.use("/product", product);
  app.use("/productType", productType);
  app.use("/promo", promo);
  app.use("/review", review);
  app.use("/store", store);
  app.use("/user", user);
}
module.exports = route;

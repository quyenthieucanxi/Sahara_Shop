const CartModel = require("../models/Cart");
const ProductModel = require("../models/Product");
class Cart {
  update(req, res, next) {
    CartModel.findByIdAndUpdate(req.params.id, req.body)
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
  delete(req, res, next) {
    CartModel.findByIdAndDelete(req.params.id)
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }

  // Get cart and price, name of productId
  getCartByProductId(req, res, next) {
    const productId = req.params.productId;
    Promise.all([
      ProductModel.findOne({ _id: productId }, { price: 1, name: 1 }),
      CartModel.findOne({ productId: productId }),
    ])
      .then(([product, cart]) => {
        res.json({
          product,
          _id: cart._id,
          userId: cart.userId,
          productId: cart.productId,
          quantity: cart.quantity,
          state: cart.state,
        });
      })
      .catch((err) => {
        res.json(err);
      });
  }

  addCart(req, res, next) {
    const cart = new CartModel(req.body);
    cart.save();
    res.json(cart);
  }
  getCartByUserId(req, res, next) {
    const userId = req.params.userId;
    CartModel.find(
      { userId: userId, state: "Unpaid" },
      { updatedAt: 0, createdAt: 0, __v: 0 }
    )
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
}
module.exports = new Cart();

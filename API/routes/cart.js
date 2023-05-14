const express = require("express");
const router = express.Router();

const CartController = require("../controllers/Cart");

router.get("/getCartByProductId/:productId", CartController.getCartByProductId);
router.get("/getCartByUserId/:userId", CartController.getCartByUserId);
router.post("/addCart", CartController.addCart);
router.put("/update/:id", CartController.update);
router.delete("/delete/:id", CartController.delete);

module.exports = router;

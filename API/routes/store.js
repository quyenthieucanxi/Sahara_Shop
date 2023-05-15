const express = require("express");
const router = express.Router();

const StoreController = require("../controllers/Store");

router.get("/getAllStores", StoreController.getAllStores);
router.get("/getStoreById/:id", StoreController.getStoreById);
router.get("/getStoreByName", StoreController.getStoreByName);
router.get(
  "/getStoreByProductId/:productId",
  StoreController.getStoreByProductId
);

module.exports = router;

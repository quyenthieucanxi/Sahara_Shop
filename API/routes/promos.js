const express = require("express");
const router = express.Router();

const PromoController = require("../controllers/Promo");

router.get("/getAllPromos", PromoController.getAllPromos);
router.get("/getTopPromos", PromoController.getTopPromos);
router.post("/add", PromoController.add);

module.exports = router;

const express = require("express");
const router = express.Router();

const DiscountController = require("../controllers/Discount");

router.get("/getAllDiscounts", DiscountController.getAllDiscounts);
module.exports = router;

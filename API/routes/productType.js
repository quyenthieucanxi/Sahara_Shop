const express = require("express");
const router = express.Router();

const ProductTypeController = require("../controllers/ProductType");

router.get("/getAllProductTypes", ProductTypeController.getAllProductTypes);

module.exports = router;

const express = require("express");
const router = express.Router();

const ReviewController = require("../controllers/Review");

router.get("/getReviews/:id", ReviewController.getReviews);

module.exports = router;

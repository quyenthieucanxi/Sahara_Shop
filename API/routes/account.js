const express = require("express");
const router = express.Router();

const AccountController = require("../controllers/Account");

router.get("/getAccount/:id", AccountController.getAccount);
router.post("/login", AccountController.login);
router.post("/sign-up", AccountController.signup);
router.put("/update/:id", AccountController.update);
router.get("/getAccountByEmail/:email", AccountController.getAccountByEmail);

module.exports = router;

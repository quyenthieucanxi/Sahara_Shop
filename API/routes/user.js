const express = require("express");
const router = express.Router();

const UserController = require("../controllers/User");

router.get("/getUserByAccountId/:accountId", UserController.getUserByAccountId);
router.get("/getUserById/:id", UserController.getUserById);
router.post("/addUser", UserController.addUser);
router.put("/update/:id", UserController.update);
router.delete("/delete/:id", UserController.delete);
router.post("/addProductLove/:id", UserController.addProductLove);
router.post("/removeProductLove/:id", UserController.removeProductLove);
module.exports = router;

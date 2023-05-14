const express = require("express");
const router = express.Router();

const NotificationController = require("../controllers/Notification");

router.get(
  "/getAllNotificationsByUserId/:userId",
  NotificationController.getAllNotificationsByUserId
);
router.put("/update/:id", NotificationController.update);
router.post("/add", NotificationController.add);

module.exports = router;

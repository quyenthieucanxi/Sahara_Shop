const NotificationModel = require("../models/Notification");

class Notification {
  getAllNotificationsByUserId(req, res, next) {
    NotificationModel.find({ userId: req.params.userId, state: true })
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
  //Change state to false
  update(req, res, next) {
    NotificationModel.findByIdAndUpdate(req.params.id, { state: false })
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }

  add(req, res, next) {
    const notification = new NotificationModel(req.body);
    notification.save();
    res.json(notification);
  }
}
module.exports = new Notification();

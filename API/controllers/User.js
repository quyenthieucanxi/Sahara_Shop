const UserModel = require("../models/User");

class User {
  getUserByAccountId(req, res, next) {
    UserModel.findOne(
      { accountId: req.params.accountId },
      { __v: 0, createdAt: 0, updatedAt: 0 }
    )
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }

  // [POST] /user/addUser
  addUser(req, res, next) {
    const { accountId, fullname, sex, phone, address, avatar, state } =
      req.body;
    UserModel.create({
      accountId,
      fullname,
      sex,
      phone,
      address,
      avatar,
      state: true,
    })
      .then((user) => {
        res.json(user);
      })
      .catch((err) => {
        res.json(err);
      });
  }

  getUserById(req, res, next) {
    UserModel.find({ _id: req.params.id })
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
  update(req, res, next) {
    UserModel.findByIdAndUpdate(req.params.id, req.body)
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
  delete(req, res, next) {
    UserModel.findByIdAndDelete(req.params.id)
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }

  addProductLove(req, res, next) {
    UserModel.findByIdAndUpdate(req.params.id, {
      $push: { productLove: req.body.productLove },
    })
      .then((result) => {
        res.json(
          {
            ...result,
            productLove: result.productLove.push(req.body.productLove),
          }._doc
        );
      })
      .catch((err) => {
        res.json(err);
      });
  }

  removeProductLove(req, res, next) {
    UserModel.findByIdAndUpdate(req.params.id, {
      $pull: { productLove: req.body.productLove },
    })
      .then((result) => {
        var productLove = result.productLove.filter(
          (item) => item != req.body.productLove
        );
        res.json({
          _id: result._id,
          accountId: result.accountId,
          fullname: result.fullname,
          sex: result.sex,
          phone: result.phone,
          address: result.address,
          avatar: result.avatar,
          productLove: productLove,
          state: result.state,
        });
      })
      .catch((err) => {
        res.json(err);
      });
  }
}
module.exports = new User();

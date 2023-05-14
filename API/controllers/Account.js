const AccountModel = require("../models/Account");

class Account {
  // [GET] /account/getAccount/:id
  getAccount(req, res, next) {
    AccountModel.find({ _id: req.params.id })
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
  // [POST] /account/login
  login(req, res, next) {
    AccountModel.findOne(
      { email: req.body.email, password: req.body.password },
      { __v: 0, createdAt: 0, updatedAt: 0 }
    )
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }

  // [POST] /account/sign-up
  signup(req, res, next) {
    AccountModel.findOne({ email: req.body.email })
      .then((account) => {
        if (account) res.status(404).send("Email đã tồn tại");
        else {
          const account = req.body;
          const newAccount = new AccountModel(account);
          newAccount.save();
          res.json(newAccount);
        }
      })
      .catch((err) => {
        res.json(err);
      });
  }

  update(req, res, next) {
    AccountModel.findByIdAndUpdate(req.params.id, req.body)
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }

  getAccountByEmail(req, res, next) {
    AccountModel.findOne({ email: req.params.email }, { email: 1, password: 1 })
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
}
module.exports = new Account();

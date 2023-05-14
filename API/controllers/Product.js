const ProductModel = require("../models/Product");
const ProductTypeModel = require("../models/ProductType");
const DiscountModel = require("../models/Discount");

class Product {
  getAllProducts(req, res, next) {
    ProductModel.find({})
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
  getProductById(req, res, next) {
    ProductModel.findById(req.params.id)
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
  getProductByListTypeId(req, res, next) {
    const { listTypeId } = req.body;
    ProductModel.find({ typeId: { $in: listTypeId } })
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }

  getProductByTypeId(req, res, next) {
    ProductModel.find({ typeId: req.params.id })
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
  getTopProducts(req, res, next) {
    ProductModel.find({ type: req.body.type })
      .limit(+req.body.limit)
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
  update(req, res, next) {
    ProductModel.findByIdAndUpdate(req.params.id, req.body)
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
  delete(req, res, next) {
    ProductModel.findByIdAndDelete(req.params.id)
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }
  // Search product by req.body.text or req.body.type
  search(req, res, next) {
    ProductModel.find({
      $or: [
        { name: { $regex: req.body.text, $options: "i" } },
        { type: { $regex: req.body.text, $options: "i" } },
      ],
    })
      .then((result) => {
        res.json(result);
      })
      .catch((err) => {
        res.json(err);
      });
  }

  getProductByTypeName(req, res, next) {
    const { typeName } = req.body;
    ProductTypeModel.findOne({ name: { $regex: typeName, $options: "i" } })
      .then((result) => {
        ProductModel.find({ typeId: result._id })
          .then((result) => {
            res.json(result);
          })
          .catch((err) => {
            res.json(err);
          });
      })
      .catch((err) => {
        res.json(err);
      });
  }

  getDiscountProductByName(req, res, next) {
    const name = req.query.name;

    ProductModel.find({})
      .then(async (result) => {
        var checkId = [];
        var result_clean = result.map((item) => {
          return {
            _id: item._id,
            name: item.name
              .toLowerCase()
              .normalize("NFD")
              .replace(/[\u0300-\u036f]/g, ""),
            price: item.price,
            image: item.image,
            typeId: item.typeId,
          };
        });
        result_clean = result_clean.filter((item) =>
          item.name.includes(
            name
              .toLowerCase()
              .normalize("NFD")
              .replace(/[\u0300-\u036f]/g, "")
          )
        );
        await Promise.all(
          result_clean.map(async (item) => {
            const result = await DiscountModel.findOne({
              productId: item._id.toString(),
            });
            if (result) {
              checkId.push(result.productId);
            }
          })
        );

        res.json(
          result.filter((item) => checkId.includes(item._id.toString()))
        );
      })
      .catch((err) => {
        res.json(err);
      });
  }
}
module.exports = new Product();

const express = require('express');
const router = express.Router();
const testController = require('../controllers/testControllor');
const chartController = require('../controllers/chartController');
const { catchErrors } = require('../handlers/errorHandlers');


// Do work here
router.get('/', catchErrors(testController.homePage));

router.get('/showdata', catchErrors(testController.apidata));
router.get('/showdata2', catchErrors(testController.apidata2));

router.get('/show', catchErrors(testController.mysqldata));

router.get('/textcloud', catchErrors(chartController.d3chart));

// router.get('/price', (req, res) => {
//   console.log("success");
//   res.send("This is price page!");
// });

router.get('/ranking', (req, res) => {
  console.log("success");
  res.render('./Multi/index');;
});

// router.get('/ranking', (req, res) => {
//   console.log("success");
//   res.render('./Multi/index');;
// });

// router.get('/textcloud', (req, res) => {
//   console.log("success");
//   res.send("This is textcloud page!");
// });

// router.get('/testjson', (req, res) => {
//   console.log("success");
//   res.send("This is textcloud page!");
// });


// router.get('/api', (req, res) => {
//   console.log("Go into api");
//   res.json({"test":"data"})
// });

module.exports = router;
const express = require('express');
const router = express.Router();
// const testController = require('../controllers/testControllor');
// const chartController = require('../controllers/chartController');
const pageController = require('../controllers/pageController');
// const apiController = require('../controllers/apiController');
const { catchErrors } = require('../handlers/errorHandlers');


// Do work here

// home page
router.get('/', catchErrors(pageController.homePage));

// 熱門排行榜
router.get('/ranking', catchErrors(pageController.rankingPage));

// 
router.get('/brand', catchErrors(pageController.brandPage));

//
router.get('/carGroup', catchErrors(pageController.carGroupPage));

//
router.get('/price', catchErrors(pageController.pricePage));

// test data



// router.get('/showdata', catchErrors(testController.apidata));
// router.get('/showdata2', catchErrors(testController.apidata2));

// router.get('/show', catchErrors(testController.mysqldata));
// router.get('/textcloud', catchErrors(chartController.d3chart));


module.exports = router;
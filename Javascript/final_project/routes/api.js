const express = require('express');
const router = express.Router();
const testController = require('../controllers/testControllor');
const chartController = require('../controllers/chartController');
const pageController = require('../controllers/pageController');
const apiController = require('../controllers/apiController');
const { catchErrors } = require('../handlers/errorHandlers');


router.get('/queryPrice', catchErrors(apiController.dropdownQuery));

router.get('/getEstPrice', catchErrors(apiController.priceQueryButton));

// router.get('/ranking/:order', catchErrors(apiController.priceRanking));

// 文字雲
router.get('/chartData', catchErrors(apiController.chartData));

// 雷達圖
// router.get('/radarData', catchErrors(apiController.radarData));

module.exports = router;
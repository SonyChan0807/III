const express = require('express');
const router = express.Router();
const testController = require('../controllers/testControllor');
const chartController = require('../controllers/chartController');
const pageController = require('../controllers/pageController');
const apiController = require('../controllers/apiController');
const { catchErrors } = require('../handlers/errorHandlers');


router.get('/queryPirce', catchErrors(apiController.dropdownQuery));

router.get('/ranking/:order', catchErrors(apiController.priceRanking));

// 文字雲
router.get('/textCloud', catchErrors(apiController.textCloud));

// 雷達圖
router.get('/radarData', catchErrors(apiController.radarData));

module.exports = router;
const mongoose = require('mongoose');
mongoose.Promise = global.Promise;
const slug = require('slugs');

const radarSchema = new mongoose.Schema({
  _id: String,
});

module.exports = mongoose.model('radar', radarSchema, 'Radar');
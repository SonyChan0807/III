const mongoose = require('mongoose');
mongoose.Promise = global.Promise;
const slug = require('slugs');

const textcloudSchema = new mongoose.Schema({
  _id: String,
  data: [{
            text : String,
            size : Number
        }] 
});

module.exports = mongoose.model('webtest', textcloudSchema, 'webtest');
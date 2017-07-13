const mongoose = require('mongoose');
mongoose.Promise = global.Promise;
const slug = require('slugs');

const articleSchema = new mongoose.Schema({
//   _id: String,
//   url: String,
//   author: String,
//   tm: Date,
//   Board: String,
//   content: String,
//   reply_no : Number,
//   replies: [ 
//         {
//             reply_id : String,
//             tm : Date,
//             content : String,
//             author_id: String
//         }] 
});

module.exports = mongoose.model('mobile01', articleSchema,'mobile01');
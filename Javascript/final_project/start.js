
const mongoose = require('mongoose');
const mysql = require('mysql');


require('dotenv').config({ path: 'variables.env' });
// Connect to our Database and handle an bad connections
console.log(process.env.DATABASE)
mongoose.connect(process.env.DATABASE);
mongoose.Promise = global.Promise; // Tell Mongoose to use ES6 promises
mongoose.connection.on('error', (err) => {
  console.error(`${err.message}`);
});


require('./models/mobile01');
require('./models/textcloud');


const app = require('./app');
app.set('port', process.env.PORT || 4200);
const server = app.listen(app.get('port'), () => {
  console.log(`Express running → PORT ${server.address().port}`);
});

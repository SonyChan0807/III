const mongoose = require('mongoose');
const Article = mongoose.model('mobile01');
const mysql = require('mysql2/promise');
const Textcloud = mongoose.model('webtest')



exports.homePage = (req, res) => {
  res.render('./main/main');
};

exports.apidata = async (req, res) => {
    const article = await Article.findOne();
    res.json(article);

};

exports.apidata2 = async (req, res) => {
    const textcloud = await Textcloud.findOne();
    res.json(textcloud);

};


exports.mysqldata = async (req, res) =>{
     console.log(process.env.MYSQL_HOST)
     const connection = await mysql.createConnection({
        host: process.env.MYSQL_HOST,
        port: process.env.MYSQL_PORT,
        user: process.env.MYSQL_USER,
        password: process.env.MYSQL_PW,
        database: process.env.MYSQL_DATABASE
    });

    connection.connect((err) => {
        if (err) {
          console.error('error connecting: ' + err.stack);
          return;
        }
        console.log('connection success');
    });
    
    
    const db = req.con;
    const [rows, fields] = await connection.query('select * from final_carPrice limit 1');
    console.log(rows);
    connection.end();
    
    res.header('Content-type','application/json');
    res.send(req.query.callback + '('+ JSON.stringify(obj) + ');');
    // res.json(rows);
    
};

// exports.addStore = (req, res) => {
//   res.render('editStore', { title: 'Add Store' });
// };

// exports.createStore = async (req, res) => {
//   const store = await (new Store(req.body)).save();
//   req.flash('success', `Successfully Created ${store.name}. Care to leave a review?`);
//   res.redirect(`/store/${store.slug}`);
// };

// exports.getStores = async (req, res) => {
//   // 1. Query the database for a list of all stores
//   const stores = await Store.find();
//   res.render('stores', { title: 'Stores', stores });
// };

// exports.editStore = async (req, res) => {
//   // 1. Find the store given the ID
//   const store = await Store.findOne({ _id: req.params.id });
//   // 2. confirm they are the owner of the store
//   // TODO
//   // 3. Render out the edit form so the user can update their store
//   res.render('editStore', { title: `Edit ${store.name}`, store });
// };

// exports.updateStore = async (req, res) => {
//   // set the location data to be a point
//   req.body.location.type = 'Point';
//   // find and update the store
//   const store = await Store.findOneAndUpdate({ _id: req.params.id }, req.body, {
//     new: true, // return the new store instead of the old one
//     runValidators: true
//   }).exec();
//   req.flash('success', `Successfully updated <strong>${store.name}</strong>. <a href="/stores/${store.slug}">View Store →</a>`);
//   res.redirect(`/stores/${store._id}/edit`);
//   // Redriect them the store and tell them it worked
// };
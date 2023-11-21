const express = require("express");
const app = express();
const fetch = require("node-fetch");
const Quote = require('inspirational-quotes');


app.set("view engine", "ejs");
app.use(express.static("public"));

app.get('/', (req,res) => {
let quote = (Quote.getQuote({author:false}));
//console.log (quote.text);
  res.render('index',{"iquote":quote.text})

  
});

app.get('/game', (req,res) => {
  res.render('game')
  
});


app.get('/about', (req,res) => {
  res.render('about')
});

app.get('/major', (req,res) => {
  res.render('major')
});

app.get('/minor', (req,res) => {
  res.render('minor')
});

app.get('/whatis', (req,res) => {
  res.render('whatis')
});

app.get('/about', (req,res) => {
  res.render('about')
});


app.listen(3000, () => {
  console.log('server started');
});
const mongoose = require('mongoose')

// on utilise une fonction flechee pour qu'elle soit plus concise et eviter l'usage du mot cle function
/*function connectDB(url) {
    return mongoose.connect(url, {
      useNewUrlParser: true,
      useCreateIndex: true,
      useFindAndModify: false,
      useUnifiedTopology: true,
    });
  }*/

const connectDB = (url) => {
    return mongoose.connect(url, {
        useNewUrlParser : true,
        useCreateIndex : true,
        useFindAndModify : false,
        useUnifiedTopology : true,
    })
}

module.exports = connectDB
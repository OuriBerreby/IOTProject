const mongoose = require('mongoose');

const RegisterScheme = new mongoose.Schema({
    userName : {
        type: String, 
        required: [true, 'must provide name'],
        trim: true,
        maxLength: [20, 'name cannot be more than 30 characters'],
    }, 
    userPassword : { 
        type: String, 
        required: [true, 'must provide password'],
        trim: true,
        maxLength: [20, 'name cannot be more than 30 characters'],
    }
})

module.exports = mongoose.model('Register', RegisterScheme)
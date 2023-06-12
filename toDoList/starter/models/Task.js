const mongoose = require('mongoose');

const TaskScheme = new mongoose.Schema({
    taskName : {
        type: String, 
        required: [true, 'must provide name'],
        trim: true,
        maxLength: [50, 'name cannot be more than 30 characters'],
    }, 
    taskCompleted : { 
        type: Boolean,
        default: false,
    }
})

module.exports = mongoose.model('Task', TaskScheme)
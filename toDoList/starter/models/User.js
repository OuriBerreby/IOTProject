const mongoose = require('mongoose');

const UserSchema = new mongoose.Schema({
  userName: {
    type: String,
    required: [true, 'must provide name'],
    trim: true,
    maxLength: [20, 'name cannot be more than 30 characters'],
  },
  userPassword: {
    type: String,
    required: [true, 'must provide password'],
    trim: true,
    maxLength: [20, 'name cannot be more than 30 characters'],
  },
  tasks: [
    {
      taskName: {
        type: String,
        required: [true, 'must provide task name'],
        trim: true,
        maxLength: [50, 'name cannot be more than 30 characters'],
      },
      taskCompleted: {
        type: Boolean,
        default: false,
      },
    },
  ],
});

module.exports = mongoose.model('User', UserSchema);

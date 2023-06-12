const express = require('express')
const router = express.Router();

const authenticationMiddleware = require('../middleware/auth')
const {getAllTasks} = require('../controllers/tasks')
const {login} = require('../controllers/login')

router.route('/dashboard').get(authenticationMiddleware, getAllTasks)
router.route('/').post(login)

module.exports = router
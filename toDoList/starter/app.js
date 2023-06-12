
const express = require('express');
const app = express();
const tasks = require('./routes/tasks')
const register = require('./routes/register')
const login = require('./routes/login')
const connectDB = require('./DB/connect')
require('dotenv').config()
const jwt = require ('jsonwebtoken')
const notFound = require('./middleware/not-found')
const errorHandlerMiddleware = require('./middleware/error-handler')

// middleware
app.use(express.static('./public'))
app.use(express.json())
app.use('/api/v1/register', register)
app.use('/api/v1/login', login)
app.use('/api/v1/tasks', tasks)
app.use(notFound)
app.use(errorHandlerMiddleware)
const port = 4000

// async keyword : make a promess that when all await method are completed -> the other are processed
const start = async () => {
    try {
        await connectDB(process.env.MONGO_URI)
        app.listen(port, () =>
            console.log(`Server is listening on port ${port}...`))
    } catch (error) {
        console.log(error)
    }
}

start()
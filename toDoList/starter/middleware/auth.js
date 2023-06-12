const jwt = require('jsonwebtoken')
const { CustomAPIError, createCustomError } = require('../errors/custom-errors')


const authenticationMiddleware = async (req, res, next) => {
    const authHeader = req.headers.authorization
    
    if (!authHeader || !authHeader.startsWith('Bearer ')) {
      throw createCustomError('No token provided')
    }
    
    const token = authHeader.split(' ')[1]
    
    try {
      const decoded = jwt.verify(token, process.env.JWT_SECRETPASS)
      const { userName } = decoded
      req.user = { userName }
      console.log(`Hello ${userName}`)
      next()
    } catch (error) {
      throw createCustomError('Not authorized to access this route')
    }
  }
  
  module.exports = authenticationMiddleware
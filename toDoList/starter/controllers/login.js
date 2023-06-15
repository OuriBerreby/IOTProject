const asyncWrapper = require('../middleware/async')
const auth = require('../middleware/auth')
const Log = require('../models/User')
const jwt = require ('jsonwebtoken')

let refreshTokens = []

const login = asyncWrapper (async(req, res) => {
    const {userName: currentUser} = req.body
    const {userPassword: givenPassword} = req.body

    const isRegistered = await Log.findOne({userName: currentUser})
    if (!isRegistered){
        res.status(404).json({msg: `user ${currentUser} is not found`})
        return
    }
    else {
        if (givenPassword != isRegistered.userPassword){
            res.status(404).json({msg: `Password is incorrect`})
            return
        }
    }
    const user = { currentUser }
    console.log(user)
    const accessToken = generateAccessToken(user)
    console.log(accessToken)
    res.status(200).send(accessToken)
})

function generateAccessToken(user) {
    return jwt.sign(user, process.env.JWT_SECRETPASS)
}

module.exports = {login}

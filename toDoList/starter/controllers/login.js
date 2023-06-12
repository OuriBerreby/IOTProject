const asyncWrapper = require('../middleware/async')
const auth = require('../middleware/auth')
const Log = require('../models/Register')
const jwt = require ('jsonwebtoken')

let refreshTokens = []

const login = asyncWrapper (async(req, res) => {
    console.log("GABIO")
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
    const user = { userName: currentUser }
    const accessToken = generateAccessToken(user)
    res.status(200).json({ accessToken: accessToken })
})

function generateAccessToken(user) {
    return jwt.sign(user, process.env.JWT_SECRETPASS)
}

module.exports = {login}

const asyncWrapper = require('../middleware/async')
const Reg = require('../models/User')

const createUser = asyncWrapper (async(req, res) => {
    const {userName: currentUser} = req.body // destructuration d'objet pour extraire la valeur de l'attribut 
    const {userPassword: givenPassword} = req.body
    console.log(`Name = ${currentUser}`)
    const isRegistered = await Reg.findOne({userName: currentUser})
    if (isRegistered){
        console.log("User exist")
        res.status(404).json({msg: 'An user with this username already exist'})
        return 
    }
    const user = await Reg.create(req.body)
    res.status(200).json({user})
})

module.exports = {
    createUser,
}
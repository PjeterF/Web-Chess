const server='http://localhost:8080'

export async function loadGame(gameID){
    try {
        const token=sessionStorage.getItem('token')
        if(token==null){
            throw new Error('Missing token')
        }
        const response=await fetch(server+'/api/games/'+gameID, {
                method:'GET',
                headers:{
                    'Authorization':'Bearer '+token
                },
            }
        )

        if(response.ok){
            return await response.json()
        }else{
            return null
        }
    } catch (error) {
        console.log(error)
        return null
    }
}

export async function loadGames(username){
    try {
        const token=sessionStorage.getItem('token')
        if(token==null){
            throw new Error('Missing token')
        }
        const response=await fetch(server+'/api/games/username/'+username, {
                method:'GET',
                headers:{
                    'Authorization':'Bearer '+token
                },
            }
        )

        if(response.ok){
            return await response.json()
        }else{
            return null
        }
    } catch (error) {
        console.log(error)
        return null
    }
}

export async function movePiece(gameID, startX, startY, targetX, targetY){
    try {
        const token=sessionStorage.getItem('token')
        if(token==null){
            throw new Error('Missing token')
        }
        const response=await fetch(server+'/api/games/move', {
            method:'POST',
            headers:{
                'Content-Type':'application/json',
                'Authorization':'Bearer '+token
            },
            body: JSON.stringify({gameID:gameID, start:[startX, startY], target:[targetX, targetY]})
        })

        if(response.ok){
            return await response.json()
        }else{
            return null
        }
    } catch (error) {
        console.log(error)
        return null
    }
}

export async function makeComputerMove(gameID, white, depth){
    try {
        const token=sessionStorage.getItem('token')
        if(token==null){
            throw new Error('Missing token')
        }
        const response=await fetch(server+'/api/games/computerMove', {
            method:'POST',
            headers:{
                'Content-Type':'application/json',
                'Authorization':'Bearer '+token
            },
            body: JSON.stringify({gameID:gameID, white:white, depth:depth})
        })

        if(response.ok){
            return await response.json()
        }else{
            return null
        }
    } catch (error) {
        console.log(error)
        return null
    }
}

export async function undoMove(gameID){
    try {
        const token=sessionStorage.getItem('token')
        if(token==null){
            throw new Error('Missing token')
        }
        const response=await fetch(server+'/api/games/undo', {
            method:'POST',
            headers:{
                'Content-Type':'application/json',
                'Authorization':'Bearer '+token
            },
            body: JSON.stringify({
                gameID:gameID
                })
            })

        if(response.ok){
            return await response.json()
        }else{
            return null
        }
    } catch (error) {
        console.log(error)
        return null
    }
}

export async function createGame(usesrnameWhite, usernameBlack, whiteIsAutomated, blackIsAutomated, difficulty=2){
    try{
        const token=sessionStorage.getItem('token')
        if(token==null){
            throw new Error('Missing token')
        }
        const response=await fetch(server+'/api/games', {
        method:'POST',
        headers:{
            'Content-Type':'application/json',
            'Authorization':'Bearer '+token
        },
        body: JSON.stringify({
            username1:usesrnameWhite,
            username2:usernameBlack,
            whiteIsAutomated:whiteIsAutomated,
            blackIsAutomated:blackIsAutomated,
            difficulty:difficulty
            })
        })

        if(response.ok){
            return await response.json()
        }
        else{
            throw new Error(await response.text())
        }
    }catch(error){
        throw error
    }
}

export async function login(username, password){
    try{
        const response=await fetch(server+'/api/accounts/login', {
        method:'POST',
        headers:{'Content-Type':'application/json'},
        body: JSON.stringify({
            username:username,
            password:password
            })
        })

        if(response.ok){
            return await response.text()
        }
        else{
            throw new Error(await response.text())
        }
    }catch(error){
        throw error
    }
}

export async function register(username, password){
    try{
        const response=await fetch(server+'/api/accounts', {
        method:'POST',
        headers:{'Content-Type':'application/json'},
        body: JSON.stringify({
            username:username,
            password:password
            })
        })

        if(response.ok){
            return await response.json()
        }
        else{
            throw new Error(await response.text())
        }
    }catch(error){
        throw error
    }
}

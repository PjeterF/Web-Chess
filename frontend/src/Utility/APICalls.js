const server='http://localhost:8080'

export async function loadGame(gameID){
    try {
        const response=await fetch('http://localhost:8080/api/games/'+gameID)

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
        const response=await fetch('http://localhost:8080/api/games/move', {
            method:'POST',
            headers:{'Content-Type':'application/json'},
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
        const response=await fetch('http://localhost:8080/api/games/computerMove', {
            method:'POST',
            headers:{'Content-Type':'application/json'},
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
        const response=await fetch(server+'/api/games/undo', {
            method:'POST',
            headers:{'Content-Type':'application/json'},
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

export async function createGame(usesrnameWhite, usernameBlack, whiteIsAutomated, blackIsAutomated){
    try{
        const response=await fetch(server+'/api/games', {
        method:'POST',
        headers:{'Content-Type':'application/json'},
        body: JSON.stringify({
            username1:usesrnameWhite,
            username2:usernameBlack,
            whiteIsAutomated:whiteIsAutomated,
            blackIsAutomated:blackIsAutomated,
            difficulty:4
            })
        })

        if(response.ok){
            const data=await response.json()
            return data
        }else{
            return null
        }
    }catch(error){
        console.log(error)
        return null
    }
}
import React, { useEffect, useState } from "react";
import { useContext } from "react";
import { boardContext } from "./BoardContextProvider";
import GameInfoBox from "./GameInfoBox";

function GameList(){
    const username='User1'
    const [list, setList]=useState([])
    const {boardContextValue, dispatch}=useContext(boardContext)

    useEffect(()=>{
        async function fetchGameListOfUser(username){
            try{
                const response=await fetch('http://localhost:8080/api/games/username/'+username)
                if(response.ok){
                    const data=await response.json()
                    setList(data)
                }
            }catch(error){
                console.log(error)
            }
        }

        fetchGameListOfUser(username)
    }, [boardContextValue.game.id])

    return(
        <div style={{height:'100%', width:'auto', overflow:'scroll', overflowX:'hidden'}}>
            {list.map((game, index)=>(
            <GameInfoBox key={index} gameID={game.id} created={game.created} lastUpdate={game.lastUpdate}/>
            ))}
        </div> 
    )
}

export default GameList
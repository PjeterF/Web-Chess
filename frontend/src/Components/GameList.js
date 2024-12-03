import React, { useEffect, useState } from "react";
import { useContext } from "react";
import { boardContext } from "./BoardContextProvider";
import GameInfoBox from "./GameInfoBox";
import { loadGames } from "../Utility/APICalls";

function GameList(){
    const [username, setUsername]=useState(sessionStorage.getItem('username'))
    const [list, setList]=useState([])
    const {boardContextValue, dispatch}=useContext(boardContext)

    useEffect(()=>{
        async function fetchGameListOfUser(username){
            const games=await loadGames(username)
            console.log(games)
            if(games!=null){
                setList(games)
            }else{
                alert('Error loading games')
            }
        }

        fetchGameListOfUser(username)
    }, [boardContextValue.game.id, username])

    return(
        <div style={{height:'100%', width:'auto', overflow:'scroll', overflowX:'hidden'}}>
            {list.map((game, index)=>(
                <GameInfoBox key={index} gameID={game.id} created={game.created} lastUpdate={game.lastUpdate}/>
            ))}
        </div> 
    )
}

export default GameList
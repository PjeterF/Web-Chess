import React, { useEffect, useState } from "react";
import { useContext } from "react";
import { boardContext } from "./BoardContextProvider";
import GameInfoBox from "./GameInfoBox";
import { loadGames } from "../Utility/APICalls";
import "../Styles/GameList.css"

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
        <div>
            <div className="GameList-Heading">
                <div className="GameList-Heading-Item">Id</div>
                <div className="GameList-Heading-Item">Date created</div>
                <div className="GameList-Heading-Item">Players</div>
                <div className="GameList-Heading-Item">Status</div>
            </div>
            {list.map((game, index)=>(
                <GameInfoBox key={index} gameID={game.id} created={game.created} users={game.accountIDs} result={game.result}/>
            ))}
        </div>
        
    )
}

export default GameList
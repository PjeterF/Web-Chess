import React, { useContext, useState } from "react";
import { boardContext } from "./BoardContextProvider";
import { loadGame } from "../Utility/APICalls";
import { useNavigate } from "react-router-dom";
import "../Styles/GameInfoBox.css"

function GameInfoBox({gameID, created, users, result}){
    const [isHovered, setIsHovered]=useState(false)
    const {boardContextValue, dispatch}=useContext(boardContext)
    const navigate=useNavigate()

    async function handleClick(id){
        const game=await loadGame(id)
        if(game!=null){
            dispatch({
                type:'set game',
                payload:game
            })
            navigate('/game')
        }else{
            alert('Error loading game')
        }
    }

    return(
        <div
            className="GameInfoBox"
            onMouseEnter={()=>{setIsHovered(true)}}
            onMouseLeave={()=>{setIsHovered(false)}}
            onClick={()=>{handleClick(gameID)}}
        >
            <div className="GameInfoBox-Item">{gameID}</div>
            <div className="GameInfoBox-Item">{created}</div>
            <div className="GameInfoBox-Item">{users[0]} vs {users[1]}</div>
            <div className="GameInfoBox-Item End">{result}</div>
        </div>
    )
}

export default GameInfoBox
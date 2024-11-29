import React, { useContext, useState } from "react";
import { boardContext } from "./BoardContextProvider";
import { loadGame } from "../Utility/APICalls";
import { useNavigate } from "react-router-dom";

function GameInfoBox({gameID, created, lastUpdate}){
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
            style={{
                border:'2px solid black',
                borderRadius:'5px',
                padding:'5px',
                transform: isHovered?'scale(1.01)':'scale(1.0)',
                transition:'transform 0.2s ease',
                cursor:'pointer',
                marginBottom:'3px'
            }}
            onMouseEnter={()=>{setIsHovered(true)}}
            onMouseLeave={()=>{setIsHovered(false)}}
            onClick={()=>{handleClick(gameID)}}
        >
            <div>ID: {gameID}</div>
            <div>Created: {created}</div>
            <div>Last update: {lastUpdate}</div>
        </div>
    )
}

export default GameInfoBox
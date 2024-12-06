import React, { useState } from "react";
import "../Styles/Gamemode.css"
import "../Styles/UI.css"
import { useNavigate } from "react-router-dom";
import { useContext } from "react";
import { boardContext } from "../Components/BoardContextProvider";
import { createGame } from "../Utility/APICalls";

function Gamemode(){
    const {boardContextValue, dispatch}=useContext(boardContext)
    const navigate=useNavigate()
    const [opponentUsername, setOpponentUsername]=useState("")
    const [error, setError]=useState("")

    async function playAgainstComputer(){
        try {
            const game=await createGame(sessionStorage.getItem('username'), 'Computer', false, true)
            dispatch({
                type:'set game',
                payload:game
            })
            navigate('/game')
        } catch (error) {
           setError(error.message) 
        }
    }
    
    async function playAgainstPlayer(username1, username2){
        try {
            const game=await createGame(username1, username2, false, false)
            dispatch({
                type:'set game',
                payload:game
            })
            navigate('/game')
        } catch (error) {
            console.log(error)
           setError(error.message) 
        }
    }
    
    const buttonStyle={
        margin:'4px',
        width:'200px'
    }

    return(
        <div className="Gamemode-Container">
            <div className="button button-khaki" onClick={playAgainstComputer} style={buttonStyle}>Play agaist computer</div>
            <div className="button button-ash" onClick={()=>{playAgainstPlayer(sessionStorage.getItem('username'), opponentUsername)}} style={buttonStyle}>Host game</div>
            <input type="text" placeholder="Oponent's username" onChange={(e)=>{setOpponentUsername(e.target.value)}} style={buttonStyle}></input>
            <div>{error}</div>
        </div>
    )
}

export default Gamemode
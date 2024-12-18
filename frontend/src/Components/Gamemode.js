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
    const [difficulty, setDifficulty]=useState(5)

    async function playAgainstComputer(){
        try {
            const game=await createGame(sessionStorage.getItem('username'), 'Computer', false, true, difficulty)
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
            const game=await createGame(username1, username2, false, false, difficulty)
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

    async function AIvsAI(){
        try {
            const game=await createGame('Computer', 'Computer', true, true, difficulty)
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
            <div className="Gamemode-SubContainer">
                <h3 style={{color:'var(--text-color)'}}>Play agaist computer</h3>
                <div className="button button-primary" onClick={playAgainstComputer} style={buttonStyle}>Play vs AI</div>
                <div className="button button-primary" onClick={AIvsAI} style={buttonStyle}>Spectate AI vs AI</div>
                <div style={{color:'var(--text-color)'}}>AI diffuculty</div>
                <div style={{display:'flex', flexDirection:'row', color:'var(--text-color)'}}>
                    <div>1</div>
                    <input className="Gamemode-Slider" onChange={(e)=>{setDifficulty(e.target.value)}} type="range" min={3} max={6} defaultValue={5}></input>
                    <div>4</div>
                </div>
            </div>
            <div className="Gamemode-SubContainer">
                <h3 style={{color:'var(--text-color)'}}>Play agaist other players</h3>
                <div className="button button-primary" onClick={()=>{playAgainstPlayer(sessionStorage.getItem('username'), opponentUsername)}} style={buttonStyle}>Host game</div>
                <input type="text" placeholder="Oponent's username" onChange={(e)=>{setOpponentUsername(e.target.value)}} style={buttonStyle}></input>
            </div>
            <div>{error}</div>
        </div>
    )
}

export default Gamemode
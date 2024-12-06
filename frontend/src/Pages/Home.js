import React from "react";
import "../Styles/Home.css"
import "../Styles/UI.css"
import { useNavigate } from "react-router-dom";
import { useContext } from "react";
import { boardContext } from "../Components/BoardContextProvider";
import ChessBoard from "../Components/ChessBoard";
import GameList from "../Components/GameList";
import GameSettings from "../Components/GameSettings";

import { createGame } from "../Utility/APICalls";
import NavBar from "../Components/NavBar";
import Gamemode from "../Components/Gamemode";

function Home(){
    const {boardContextValue, dispatch}=useContext(boardContext)
    const navigate=useNavigate()

    async function playAgainstComputer(){
        const game=await createGame(sessionStorage.getItem('username'), 'Computer', false, true)
        if(game!=null){
            dispatch({
                type:'set game',
                payload:game
            })
            navigate('/game')
        }
    }

    async function playAgainstPlayer(username1, username2){
        const game=await createGame(username1, username2, false, false)
        if(game!=null){
            dispatch({
                type:'set game',
                payload:game
            })
            navigate('/game')
        }
    }

    const buttonStyle={
        margin:'4px',
        width:'140px'
    }

    return(
        <div className="home-container">
            <Gamemode/>
            <GameList/>
        </div>
    )
}

export default Home
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

function Home(){
    const {boardContextValue, dispatch}=useContext(boardContext)
    const navigate=useNavigate()

    async function onHost(){

    }

    async function onJoin(){

    }

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
            <NavBar/>
            <div className="home-sub-container">
            <div className="button button-ash" style={buttonStyle}>Login</div>
            <div className="button button-khaki" style={buttonStyle}>Create account</div>
            </div>
            <div className="home-sub-container">
                <div className="button button-gray" onClick={playAgainstComputer} style={buttonStyle}>Play agaist computer</div>
                <div className="button button-ash" onClick={()=>{playAgainstPlayer(sessionStorage.getItem('username'), 'User1')}} style={buttonStyle}>Host game</div>
                <div className="button button-khaki" style={buttonStyle}>Join game</div>
            </div>
            <GameSettings/>
            <GameList/>
        </div>
    )
}

export default Home
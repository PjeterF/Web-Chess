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

function Home(){
    const {boardContextValue, dispatch}=useContext(boardContext)
    const navigate=useNavigate()

    async function onHost(){

    }

    async function onJoin(){

    }

    async function playOffline(){
        const game=await createGame('User1', 'Computer', false, true)
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
            <div className="home-sub-container">
            <div className="button button-ash" style={buttonStyle}>Login</div>
            <div className="button button-khaki" style={buttonStyle}>Create account</div>
            </div>
            <div className="home-sub-container">
                <div className="button button-gray" onClick={playOffline} style={buttonStyle}>Play agaist computer</div>
                <div className="button button-ash" style={buttonStyle}>Host game</div>
                <div className="button button-khaki" style={buttonStyle}>Join game</div>
            </div>
            <GameSettings/>
        </div>
    )
}

export default Home
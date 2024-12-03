import React from "react";
import ChessBoard from "../Components/ChessBoard";
import "../Styles/Game.css"
import { BoardContextProvider } from "../Components/BoardContextProvider";
import GameList from "../Components/GameList";
import Controls from "../Components/Controls";
import NavBar from "../Components/NavBar";


function Game(){
    return(
        <div className="Game-grid">
            <div className="Game-grid-top">
                <Controls/>
            </div>
            <div className="Game-grid-mid">
                <ChessBoard/>
            </div>
            <div className="Game-grid-right">
                <GameList/>
            </div>
        </div>
    )
}

export default Game
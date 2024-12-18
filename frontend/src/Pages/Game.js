import React from "react";
import ChessBoard from "../Components/ChessBoard";
import "../Styles/Game.css"
import { BoardContextProvider } from "../Components/BoardContextProvider";
import GameList from "../Components/GameList";
import Controls from "../Components/Controls";
import NavBar from "../Components/NavBar";


function Game(){
    return(
        <div style={{display:'grid', gridTemplateRows:'auto 91.5vh'}}>
            <div>
                <NavBar/>
            </div>
            <div className="Game-grid">
                <div className="Game-grid-right-top">
                    <Controls/>
                </div>
                <div className="Game-grid-left">
                    <ChessBoard/>
                </div>
            </div>
        </div>
            
        
    )
}

export default Game
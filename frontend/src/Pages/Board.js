import React from "react";
import ChessBoard from "../Components/ChessBoard";
import "../Styles/Board.css"
import { BoardContextProvider } from "../Components/BoardContextProvider";
import GameList from "../Components/GameList";


function Board(){
    return(
        <div className="Board">
            <ChessBoard/>
            <GameList></GameList>
            
        </div>
    )
}

export default Board
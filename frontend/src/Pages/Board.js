import React from "react";
import Canvas from "../Components/Canvas";
import "../Styles/Board.css"
import { BoardContextProvider } from "../Components/BoardContextProvider";

function Board(){
    return(
        <BoardContextProvider>
            <div className="Board">
                <Canvas/>
            </div>
        </BoardContextProvider>
        
    )
}

export default Board
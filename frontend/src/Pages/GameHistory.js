import React from "react";
import GameList from "../Components/GameList";
import NavBar from "../Components/NavBar";
import "../Styles/GameHistory.css"
import "../Styles/UI.css"

function GameHistory(){
    return(
        <>
            <NavBar/>
            <div className="GameHistory-Container">
                <div className="Container">
                    <div className="GameHistory-List SubContainer">
                        <GameList/>
                    </div>
                </div>
            </div>
        </>
    )
}

export default GameHistory
import React from "react";
import { boardContext } from "./BoardContextProvider";
import { useContext } from "react";

import "../Styles/UI.css"
import { undoMove } from "../Utility/APICalls";

function Controls(){
    const {boardContextValue, dispatch}=useContext(boardContext)

    async function undoOnce(){
        const game=await undoMove(boardContextValue.game.id)
        if(game!=null){
            dispatch({
                type:'set game',
                payload:game
            })
        }
    }

    async function undoTwice() {
        await undoOnce()
        await undoOnce()
    }

    return(
        <div className="Container">
            <div className="SubContainer">
                <div style={{color:'var(--text-color)'}}>Controls</div>
                <div className="button button-primary">Restart</div>
                <div className="button button-secondary" onClick={undoOnce}>Undo Turn</div>
                <div className="button button-danger">Resign</div>
            </div>
        </div>
    )
}

export default Controls
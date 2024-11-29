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
        <div style={{display:'flex', flexDirection:'row'}}>
            <div className="button button-red">Restart</div>
            <div className="button button-green" onClick={undoOnce}>Undo move</div>
            <div className="button button-green" onClick={undoTwice}>Go back a turn</div>
            <div className="button button-blue">Resign</div>
        </div>
    )
}

export default Controls
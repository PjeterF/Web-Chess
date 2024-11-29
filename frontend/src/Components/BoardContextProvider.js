import React, { createContext, useReducer, useState } from "react";
import { useContext } from "react";

const gameInitialValue={
    game:{
        id: 1,
        boardState: "rnbqkbnrpppppppp................................PPPPPPPPRNBQKBNR",
        accountIDs: [
            1,
            2
        ],
        whitesTurn: true,
        whiteIsAutomated:false,
        blackIsAutomated:false
    },
    processingMove:false
}

function boardReducer(state, action){
    switch(action.type){
        case 'set board state':
            return{
                ...state,
                game:{
                    ...state.game,
                    boardState:action.payload
                }
            }
        case 'set game':
            return{
                ...state,
                game:action.payload
            }
        case 'toggle turn':
            return{
                ...state,
                game:{
                    ...state.game,
                    whitesTurn:!state.game.whitesTurn
                }
        }
        case 'toggle process move':
            return{
                ...state,
                processingMove:!state.processingMove
            }
    }
}

const boardContext=createContext(null)
function BoardContextProvider({children}){
    const [boardContextValue, dispatch]=useReducer(boardReducer, gameInitialValue)

    return(
        <boardContext.Provider value={{boardContextValue, dispatch}}>
            {children}
        </boardContext.Provider>
    )
}

export {boardContext, BoardContextProvider}
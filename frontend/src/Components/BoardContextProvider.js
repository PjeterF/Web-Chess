import React, { createContext, useState } from "react";
import { useContext } from "react";

const gameInitialValue={
    id: 1,
    boardState: "rnbqkbnrpppppppp................................PPPPPPPPRNBQKBNR",
    accountIDs: [
        1,
        2
    ],
    whitesTurn: true
}

const boardContext=createContext(null)

function BoardContextProvider({children}){
    const [contextGame, setContextGame]=useState(gameInitialValue)

    return(
        <boardContext.Provider value={{contextGame, setContextGame}}>
            {children}
        </boardContext.Provider>
    )
}

export {boardContext, BoardContextProvider}
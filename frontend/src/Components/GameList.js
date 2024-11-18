import React, { useEffect, useState } from "react";
import { useContext } from "react";
import { boardContext } from "./BoardContextProvider";

function GameList(){
    const username='User1'
    const [list, setList]=useState([])
    const {boardContextValue, dispatch}=useContext(boardContext)

    useEffect(()=>{
        async function fetchGameListOfUser(username){
            try{
                const response=await fetch('http://localhost:8080/api/account/'+username)
                if(response.ok){
                    const data=await response.json()
                    setList(data.gameIDs)
                }
            }catch(error){
                console.log(error)
            }
        }

        fetchGameListOfUser(username)
    }, [])

    function selectGame(gameID){
        
    }

    return(
        <div>
            <ul>
                {list.map((gameID, index)=>(
                <li key={index}>{gameID}</li>
                ))}
            </ul>
        </div> 
    )
}

export default GameList
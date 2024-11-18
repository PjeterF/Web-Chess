import React from "react";
import "../Styles/Home.css"
import "../Styles/UI.css"
import { useNavigate } from "react-router-dom";
import { useContext } from "react";
import { boardContext } from "../Components/BoardContextProvider";
import ChessBoard from "../Components/ChessBoard";
import GameList from "../Components/GameList";
import '../Styles/Grid.css'

function Home(){
     const {boardContextValue, dispatch}=useContext(boardContext)
    const navigate=useNavigate()

    async function onHost(){

    }

    async function onJoin(){

    }

    async function playOffline(){
        const response=await fetch('http://localhost:8080/api/game/create', {
            method:'POST',
            headers:{'Content-Type':'application/json'},
            body: JSON.stringify({
                username1:"User1",
                username2:"User2"
            })
        })

        const data=await response.json()

        if(response.ok){
            dispatch({
                type:'set game',
                payload:data
            })
            navigate('/board')
            console.log(data)
        }
    }

    return(
        <div className="grid-container">
            <div className="grid-header">

            </div>
            <div className="grid-left">
                <div className="button button-red" onClick={onHost}>Host game</div>
                <div className="button button-green" onClick={onJoin}>Join game</div>
                <div className="button button-blue" onClick={playOffline}>Play offline</div>
            </div>
            <div className="grid-center">
                <ChessBoard/>
            </div>
            <div className="grid-right">
                <GameList/>
            </div>
        </div>
    )
}

export default Home
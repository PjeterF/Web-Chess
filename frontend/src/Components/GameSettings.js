import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { boardContext } from "./BoardContextProvider";
import "../Styles/UI.css"

function GameSettings(){
    const [white, setWhite]=useState(true)
    const {boardContextValue, dispatch}=useContext(boardContext)
    const navigate=useNavigate()

    const username='User1'
    const computerUsername='Computer'

    async function playOffline(){
        const response=await fetch('http://localhost:8080/api/games', {
            method:'POST',
            headers:{'Content-Type':'application/json'},
            body: JSON.stringify({
                username1:white?username:computerUsername,
                username2:white?computerUsername:username,
                whiteIsAutomated:white,
                blackIsAutomated:!white
            })
        })
        
        const data=await response.json()
        if(response.ok){
            dispatch({
                type:'set game',
                payload:data
            })
            navigate('/game')
        }
    }

    return(
        <div>
            <div>Choose color</div>
            <div style={{display:'flex'}}>
                <div>
                    <img src="/whiteCircle.png" style={{width:'30px', height:'auto', margin:'10px', borderWidth:'2px', borderRadius:'5px',
                        borderStyle:white?'solid':'hidden'
                        }}
                        onClick={()=>{setWhite(true)}}/>
                </div>
                <div>
                <img src="/blackCircle.png" style={{width:'30px', height:'auto', margin:'10px', borderWidth:'2px', borderRadius:'5px',
                        borderStyle:white?'hidden':'solid'
                        }}
                        onClick={()=>{setWhite(false)}}/>
                </div>
            </div>
            
            <div>Choose difficulty</div>
            <div>

            </div>
            <div className="button button-ash" onClick={playOffline}>Play</div>
        </div>
    )
}

export default GameSettings
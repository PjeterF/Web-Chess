import React, { useState } from "react";

import "../Styles/Authentication.css"
import { useNavigate } from "react-router-dom";
import { register, login } from "../Utility/APICalls";

function Register(){
    const navigate=useNavigate()
    const [error, setError]=useState(null)
    const [username, setUsername]=useState("")
    const [password, setPassword]=useState("")
    const [password2, setPassword2]=useState("")

    async function handleSubmit(){
        if(username==""){
            setError('username cannot be empty')
            setTimeout(()=>{setError("")}, 5000)  
            return
        }
        if(password==""){
            setError('Password cannot be empty')
            setTimeout(()=>{setError("")}, 5000)  
            return
        }
        if(password!=password2){
            setError('Passwords do not match')
            setTimeout(()=>{setError("")}, 5000)  
            return
        }

        try {
            await register(password, username)

            try {
                const token=await login(username, password)
                sessionStorage.setItem("token", token)
                sessionStorage.setItem('username', username)
                navigate('/home') 
            } catch (err) {
                setError(err.message)
                setTimeout(()=>{setError("")}, 5000)            
            }
        } catch (err) {
            setError(err.message)
            setTimeout(()=>{setError("")}, 5000)  
        }

        
    }

    return(
        <div className="Auth-Container">
            <div className="Auth-Card">
                <h2 className="Auth-Title">Register</h2>
                <input onChange={(e)=>{setUsername(e.target.value)}} className="Auth-Input" placeholder="Username"></input>
                <input onChange={(e)=>{setPassword(e.target.value)}} className="Auth-Input" placeholder="Password" type="password"></input>
                <input onChange={(e)=>{setPassword2(e.target.value)}} className="Auth-Input" placeholder="Repeat Password" type="password"></input>
                <div onClick={handleSubmit} className="Auth-Submit">Register</div>
                <div style={{display:'flex', justifyContent:'space-between'}}>
                    {error!=null?<div style={{color:'orange'}}>{error}</div>:<div></div>}
                    <div style={{textDecoration:'underline', cursor:'pointer'}} className="Auth-Label" onClick={()=>{navigate('/signin')}}>Sign In</div>
                </div>
            </div>
        </div>
    )
}

export default Register
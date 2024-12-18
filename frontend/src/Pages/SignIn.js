import React, { useState } from "react";
import "../Styles/Authentication.css"
import "../Styles/UI.css"
import { useNavigate } from "react-router-dom";
import { login } from "../Utility/APICalls";

function SignIn(){
    const navigate=useNavigate()
    const [error, setError]=useState(null)
    const [username, setUsername]=useState("")
    const [password, setPassword]=useState("")

    async function handleSubmit(){
        try {
            const token=await login(username, password)
            sessionStorage.setItem("token", token)
            sessionStorage.setItem('username', username)
            navigate('/home') 
        } catch (err) {
            setError(err.message)
            setTimeout(()=>{setError("")}, 5000)            
        }
    }

    async function loginWithDemoAccount(){
        try {
            const token=await login('User1', '123')
            sessionStorage.setItem("token", token)
            sessionStorage.setItem('username', 'User1')
            navigate('/home') 
        } catch (err) {
            setError(err.message)
            setTimeout(()=>{setError("")}, 5000)            
        }
    }

    return(
        <div className="Auth-Container">
            <div className="Auth-Card">
                <h2 className="Auth-Title">Sign In</h2>
                <input onChange={(e)=>{setUsername(e.target.value)}} className="Auth-Input" placeholder="Username"></input>
                <input onChange={(e)=>{setPassword(e.target.value)}} className="Auth-Input" placeholder="Password" type="password"></input>
                <div onClick={handleSubmit} className="Auth-Submit">Sign In</div>
                <div style={{display:'flex', justifyContent:'space-between'}}>
                    {error!=null?<div style={{color:'orange'}}>{error}</div>:<div></div>}
                    <div style={{textDecoration:'underline', cursor:'pointer'}} className="Auth-Label" onClick={()=>{navigate('/register')}}>Register</div>
                </div>
                <div onClick={loginWithDemoAccount} style={{marginTop:'8px', color:'white', border:'1px solid white'}} className="button button-secondary">Sign in with demo account</div>
            </div>
        </div>
    )
}

export default SignIn
import React, { useState } from "react";

import "../Styles/Authentication.css"
import { useNavigate } from "react-router-dom";

function Register(){
    const navigate=useNavigate()
    const [error, setError]=useState(null)

    return(
        <div className="Auth-Container">
            <div className="Auth-Card">
                <h2 className="Auth-Title">Register</h2>
                <input className="Auth-Input" placeholder="Username"></input>
                <input className="Auth-Input" placeholder="Password" type="password"></input>
                <input className="Auth-Input" placeholder="Repeat Password" type="password"></input>
                <div className="Auth-Submit">Register</div>
                <div style={{display:'flex', justifyContent:'space-between'}}>
                    {error!=null?<div style={{color:'orange'}}>{error}</div>:<div></div>}
                    <div style={{textDecoration:'underline', cursor:'pointer'}} className="Auth-Label" onClick={()=>{navigate('/signin')}}>Sign In</div>
                </div>
            </div>
        </div>
    )
}

export default Register
import React from "react";
import "../Styles/NavBar.css"
import "../Styles/UI.css"
import { useNavigate } from "react-router-dom";

function NavBar(){
    const navigate=useNavigate()

    function handleLogOut(){
        sessionStorage.removeItem('token')
        sessionStorage.removeItem('username')
        navigate('/')
    }

    return(
        <div className="NavBar-Container">
            <h1 className="NavBar-Container Title">
                WebChess
            </h1>
            <div className="NavBar-Container Buttons">
                <div className="NavBar-Button" onClick={()=>{navigate('/home')}}>Game selection</div>
                <div className="NavBar-Button" onClick={()=>{navigate('/history')}}>History</div>
                <div className="NavBar-LogOut-Section">
                    <div>{sessionStorage.getItem('username')}</div>
                    <div onClick={handleLogOut} className="button button-danger">LogOut</div>
                </div>
            </div>
        </div>
    )
}

export default NavBar
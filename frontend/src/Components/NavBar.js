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
            <div className="NavBar-Container Title">
                WebChess
            </div>
            <div className="NavBar-Container Buttons">
                <div>{sessionStorage.getItem('username')}</div>
                <div onClick={handleLogOut} className="button button-red">LogOut</div>
            </div>
        </div>
    )
}

export default NavBar
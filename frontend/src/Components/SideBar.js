import React, { useState } from "react"
import "../Styles/SideBar.css"

function SideBar(){
    const [isOpen, setIsOpen]=useState(true)

    return(
        <div className={`SideBar ${isOpen?"SideBar-Open":"SideBar-Closed"}`}>
            <button onClick={()=>[setIsOpen(!isOpen)]}>Btn</button>
        </div>
    )
}

export default SideBar
import React from "react";
import {BrowserRouter, Routes, Route} from "react-router-dom"
import { BoardContextProvider } from "./Components/BoardContextProvider";

import Home from "./Pages/Home";
import Game from "./Pages/Game";
import SignIn from "./Pages/SignIn";
import Register from "./Pages/Register";
import GameHistory from "./Pages/GameHistory";

function App() {
  return (
    <div>
      <BoardContextProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/game" element={<Game/>}/>
            <Route path="/home" element={<Home/>}/>
            <Route path="/" element={<SignIn/>}/>
            <Route path="/register" element={<Register/>}/>
            <Route path="/history" element={<GameHistory/>}/>
          </Routes>
        </BrowserRouter>
      </BoardContextProvider>
    </div>
  );
}

export default App;

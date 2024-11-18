import React from "react";
import {BrowserRouter, Routes, Route} from "react-router-dom"
import { BoardContextProvider } from "./Components/BoardContextProvider";

import Board from "./Pages/Board";
import Home from "./Pages/Home";

function App() {
  return (
    <div>
      <BoardContextProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/board" element={<Board/>}/>
            <Route path="/" element={<Home/>}/>
          </Routes>
        </BrowserRouter>
      </BoardContextProvider>
    </div>
  );
}

export default App;

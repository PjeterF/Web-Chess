import React, { useContext, useEffect, useState } from "react";
import { useRef } from "react";
import "../Styles/ChessBoard.css"
import "../Styles/UI.css"
import { boardContext } from "./BoardContextProvider";

import { makeComputerMove, movePiece } from "../Utility/APICalls";

function ChessBoard(){
    const canvasRef=useRef(null)
    const cellSize=80;
    const pieceSize=64
    const [selectedCell, setSelectedCell]=useState(null)
    const [processingMove, setProcessingMove]=useState(false)
    const [socket, setSocket]=useState(null)

    const image=new Image()
    image.src='/pieces.png'

    const piecesMap=new Map()
    piecesMap.set('P', [0, 0, 16, 16])
    piecesMap.set('N', [16, 0, 16, 16])
    piecesMap.set('R', [32, 0, 16, 16])
    piecesMap.set('B', [48, 0, 16, 16])
    piecesMap.set('Q', [64, 0, 16, 16])
    piecesMap.set('K', [80, 0, 16, 16])
    piecesMap.set('p', [0, 16, 16, 16])
    piecesMap.set('n', [16, 16, 16, 16])
    piecesMap.set('r', [32, 16, 16, 16])
    piecesMap.set('b', [48, 16, 16, 16])
    piecesMap.set('q', [64, 16, 16, 16])
    piecesMap.set('k', [80, 16, 16, 16])

    const {boardContextValue, dispatch}=useContext(boardContext)

    function coordToIndex(x, y){
        return x+y*8
    }

    async function handleComputerMove(white){
        dispatch({
            type:'toggle process move',
            payload:null
        })

        console.log(boardContextValue)
        const game=await makeComputerMove(boardContextValue.game.id, white, boardContextValue.game.difficulty)
        if(game!=null){
            dispatch({
                type:'set game',
                payload:game
            })
            if(socket!=null){
                socket.send(JSON.stringify(game))
            }
        }else{
            alert("Error making computer move")
        }
        dispatch({
            type:'toggle process move',
            payload:null
        })
    }

    function charIsUppercase(c){
        return c>='A' && c<='Z'
    }
    function charIsLowercase(c){
        return c>='a' && c<='z'
    }

    async function handleClick(e){
        const board=boardContextValue.game.boardState

        const rect=e.currentTarget.getBoundingClientRect()
        const x=e.clientX-rect.left
        const y=e.clientY-rect.top

        if(selectedCell==null){
            const newSelectedCell=[Math.floor(x/cellSize), Math.floor(y/cellSize)]
            const indexNew=coordToIndex(newSelectedCell[0], newSelectedCell[1])
            if(board[indexNew]==='.'){
                setSelectedCell(null)
            }else{
                if(sessionStorage.getItem('username')==boardContextValue.game.accountIDs[0]){
                    if(charIsLowercase(board[indexNew])){
                        setSelectedCell(null)
                        return
                    }
                }else if(sessionStorage.getItem('username')==boardContextValue.game.accountIDs[1]){
                    if(charIsUppercase(board[indexNew])){
                        setSelectedCell(null)
                        return
                    }
                }
                setSelectedCell(newSelectedCell)
            }
        }else{
            const newSelectedCell=[Math.floor(x/cellSize), Math.floor(y/cellSize)]
    
            const game=await movePiece(boardContextValue.game.id, selectedCell[0], selectedCell[1], newSelectedCell[0], newSelectedCell[1])
            if(game!=null){
                dispatch({
                    type:'set game',
                    payload:game
                })
                if(socket!=null){
                    socket.send(JSON.stringify(game))
                }
            }else{
                alert("Invalid move")
            }
            setSelectedCell(null)
        }
    }

    function drawBoard(){
        const board=boardContextValue.game.boardState

        const canvas=canvasRef.current
        const context=canvas.getContext('2d')

        context.imageSmoothingEnabled = false;
        context.imageSmoothingQuality = 'low';

        image.onload=()=>{
            for(let i=0;i<8;i++){
                for(let j=0;j<8;j++){
                    if((i+j)%2===0){
                        context.fillStyle = '#555555';
                    }else{
                        context.fillStyle = '#F8F8F2';
                    }

                    if(selectedCell!==null){
                        if(i===selectedCell[0] && j===selectedCell[1]){
                            context.fillStyle='#007777'
                        }
                    }
                    
                    context.fillRect(cellSize*i, cellSize*j, cellSize, cellSize)
                }
            }

            const halfDistance=(cellSize-pieceSize)/2

            for(let i=0;i<8;i++){
                for(let j=0;j<8;j++){
                    const piece=board[coordToIndex(i, j)]
                    if(piece==='.'){
                        continue
                    }

                    const division=piecesMap.get(piece)
                    context.drawImage(image, division[0], division[1], division[2], division[3], cellSize*i+halfDistance, cellSize*j+halfDistance, pieceSize, pieceSize)
                }
            }
        }
    }

    useEffect(()=>{
        drawBoard();
    },[boardContextValue.game.boardState, selectedCell])

    useEffect(()=>{
        async function timeout(ms){
            return new Promise(resolve=>setTimeout(resolve, ms))
        }

        async function moveAutonomously(){
            if(boardContextValue.game.whitesTurn && boardContextValue.game.whiteIsAutomated){
                setProcessingMove(true)
                await timeout(1000)
                await handleComputerMove(true)
            }
            if(!boardContextValue.game.whitesTurn && boardContextValue.game.blackIsAutomated){
                setProcessingMove(true)
                await timeout(1000)
                await handleComputerMove(false)
            }
            setProcessingMove(false)
        }

        moveAutonomously()
    },[boardContextValue.game.whitesTurn])

    useEffect(()=>{
        const roomID=boardContextValue.game.id
        const url='ws://localhost:8080/game?roomID='+roomID
        const soc=new WebSocket(url)

        soc.onopen=()=>{
            console.log('Connection established')
        }
        soc.onclose=()=>{
            console.log('Connection closed')
        }
        soc.onerror=(event)=>{
            console.log('Socket error: '+event.type)
        }
        soc.onmessage=(event)=>{
            console.log('Message recieved')
            const gameStr=event.data
            dispatch({
                type:'set game',
                payload:JSON.parse(gameStr)
            })
        }

        setSocket(soc)
    }, [])

    const turnBoxColor={
        backgroundColor:boardContextValue.game.whitesTurn?'#F8F8F2':'#222222',
        color:boardContextValue.game.whitesTurn?'#222222':'#F8F8F2',
        borderColor:boardContextValue.game.whitesTurn?'#222222':'#F8F8F2',
    }

    return(
        <div style={{display:'flex', justifyContent:'center', alignItems:'center', flexDirection:'column'}}>
            {/*
                {boardContextValue.game.whiteIsAutomated?<div>White is automated</div>:<div>White is not automated</div>}
                {boardContextValue.game.blackIsAutomated?<div>Black is automated</div>:<div>Black is not automated</div>}
            */}
            {
                processingMove?(
                    <div style={turnBoxColor} className="TurnBox ComputerThinkingBox">Thinking</div>
                ):(
                    <div style={turnBoxColor} className="TurnBox">{boardContextValue.game.whitesTurn?'White\'s turn':'Black\'s turn'}</div>
                )        
            }
            <div className="ChessBoard">
                <canvas onClick={handleClick}  width={cellSize*8} height={cellSize*8} ref={canvasRef}/>
            </div>
        </div>
    )
}

export default ChessBoard
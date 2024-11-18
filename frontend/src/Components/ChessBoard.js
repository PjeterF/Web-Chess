import React, { useContext, useEffect, useState } from "react";
import { useRef } from "react";
import "../Styles/Canvas.css"
import "../Styles/UI.css"
import { boardContext } from "./BoardContextProvider";

function ChessBoard(){
    const canvasRef=useRef(null)
    const cellSize=80;
    const pieceSize=64
    const [selectedCell, setSelectedCell]=useState(null)
    const [turnText, setTurnText]=useState('White\'s turn')

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

    function movePiece(startX, startY, targetX, targetY){
        const board=boardContextValue.game.boardState

        const startIndex=coordToIndex(startX, startY)
        const targetIndex=coordToIndex(targetX, targetY)

        let newBoard=board.slice(0, targetIndex)+board[startIndex]+board.slice(targetIndex+1)
        newBoard=newBoard.slice(0, startIndex)+'.'+newBoard.slice(startIndex+1)

        dispatch({
            type:'set board state',
            payload:newBoard
        })
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
                setSelectedCell(newSelectedCell)
            }
        }else{
            const newSelectedCell=[Math.floor(x/cellSize), Math.floor(y/cellSize)]

            const response=await fetch('http://localhost:8080/api/game/move', {
                method:'POST',
                headers:{'Content-Type':'application/json'},
                body: JSON.stringify({gameID:boardContextValue.game.id, start:selectedCell, target:newSelectedCell})
            })

            if(response.ok){
                movePiece(selectedCell[0], selectedCell[1], newSelectedCell[0], newSelectedCell[1])
            }else{
                alert("Invalid move")
            }

            setSelectedCell(null)
        }
    }

    useEffect(()=>{
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
    },[boardContextValue.game.boardState, selectedCell])

    async function makeComputerMove(){
        const response=await fetch('http://localhost:8080/api/game/computerMove',{
            method:'POST',
            headers:{'Content-Type':'application/json'},
            body: JSON.stringify({gameID:boardContextValue.game.id, depth:6, white:false})
        })

        if(response.ok){
            const data=await response.json()
            dispatch({
                type:'set board state',
                payload:data.newBoardState
            })
        }else{
            alert("Error making computer move")
        }
    }

    return(
        <div>
            <div className="button button-red" onClick={makeComputerMove}>Move black</div>
            <canvas onClick={handleClick} className="canvas" width={cellSize*8} height={cellSize*8} ref={canvasRef}/>
        </div>
    )
}

export default ChessBoard
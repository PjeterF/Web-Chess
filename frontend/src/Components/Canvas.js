import React, { useEffect, useState } from "react";
import { useRef } from "react";
import "../Styles/Canvas.css"

function Canvas(){
    const canvasRef=useRef(null)
    const cellSize=80;
    const pieceSize=64
    const [board, setBoard]=useState('rnbqkbnrpppppppp................................PPPPPPPPRNBQKBNR')
    const [selectedCell, setSelectedCell]=useState(null)
    const piecesMap=new Map()

    function coordToIndex(x, y){
        return x+y*8
    }

    function handleClick(e){
        const rect=e.currentTarget.getBoundingClientRect()
        const x=e.clientX-rect.left
        const y=e.clientY-rect.top

        if(selectedCell==null){
            const newSelectedCell=[Math.floor(x/cellSize), Math.floor(y/cellSize)]
            const indexNew=coordToIndex(newSelectedCell[0], newSelectedCell[1])
            if(board[indexNew]=='.'){
                setSelectedCell(null)
            }else{
                setSelectedCell(newSelectedCell)
            }

        }else{
            const newSelectedCell=[Math.floor(x/cellSize), Math.floor(y/cellSize)]
            const indexOld=coordToIndex(selectedCell[0], selectedCell[1])
            const indexNew=coordToIndex(newSelectedCell[0], newSelectedCell[1])
            let newBoard=board.slice(0, indexNew)+board[indexOld]+board.slice(indexNew+1)
            newBoard=newBoard.slice(0, indexOld)+'.'+board.slice(indexOld+1)
            setBoard(newBoard)
            setSelectedCell(null)
        }
    }

    useEffect(()=>{
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

        const canvas=canvasRef.current
        const context=canvas.getContext('2d')

        context.imageSmoothingEnabled = false;
        context.imageSmoothingQuality = 'low';

        const image=new Image()
        image.src='/pieces.png'

        image.onload=()=>{
            for(let i=0;i<8;i++){
                for(let j=0;j<8;j++){
                    if((i+j)%2==0){
                        context.fillStyle = '#070706';
                    }else{
                        context.fillStyle = '#F8F8F2';
                    }
                    
                    context.fillRect(cellSize*i, cellSize*j, cellSize, cellSize)
                }
            }

            const halfDistance=(cellSize-pieceSize)/2

            for(let i=0;i<8;i++){
                for(let j=0;j<8;j++){
                    const piece=board[coordToIndex(i, j)]
                    if(piece=='.'){
                        continue
                    }

                    const division=piecesMap.get(piece)
                    context.drawImage(image, division[0], division[1], division[2], division[3], cellSize*i+halfDistance, cellSize*j+halfDistance, pieceSize, pieceSize)
                }
            }
        }
    },[board])

    return(
        <div>
            <canvas onClick={handleClick} className="canvas" width={cellSize*8} height={cellSize*8} ref={canvasRef}/>
        </div>
    )
}

export default Canvas
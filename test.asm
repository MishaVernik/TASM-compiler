﻿CDATA SEGMENT 
    M1  DD  111b
    M2  DW  0Fh
    M3  DD  20
    SMS DB 'COMPILER 1.0.0'
CDATA ENDS

INCAX MACRO
    INC AX
    ENDM

INCNUM MACRO NUMBER
    INC NUMBER
    ENDM

CSEG SEGMENT
 START:
    MOV AX, CDATA
    MOV DS, AX

    MOV AX, 10h
    BT AX, BX
    
    AAS
    INC CX
    DEC WORD PTR [DI]
    AND AX, WORD PTR [DI]
    OR WORD PTR [DI + BX], 0001b
    CMP WORD PTR[DI], DX
    JL lblJL	
lblJL:	 
    MOV AX, [BX][SI]
    MOV AL, CS:[BX][DI]
    MOV CL, ES:[BX + SI]
    INCAX
    INCNUM M1
    

CSEG ENDS
END
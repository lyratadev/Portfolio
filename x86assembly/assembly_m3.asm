; write a program that reverses a string using variables target and source

.386
.model flat,stdcall
.stack 4096
ExitProcess proto,dwExitCode:dword

INCLUDE Irvine32.inc

.data
source BYTE "This is the source string",0
target BYTE SIZEOF source DUP(0)

.code
main PROC
    XOR ESI,ESI
    XOR ECX,ECX
    XOR EDI,EDI
    XOR EDX,EDX
    XOR EAX,EAX

    mov ESI, OFFSET source     ; source pointer
    mov ECX, SIZEOF source -1  ; loop counter, accounting for null char
    mov EDI,0

L1:
    ;call dumpregs
    mov al, [ESI+ECX-1]        ; account for starting at 0
    mov [target+edi], al
    inc edi
        loop L1

    mov EDX, OFFSET target
    call WriteString

    invoke ExitProcess, 0
main ENDP
END main

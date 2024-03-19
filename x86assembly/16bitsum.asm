; write a program that subtracts three integers only 16-bit registers
; insert a call dumpRegs statement to display these register values

.386
.model flat,stdcall
.stack 4096
ExitProcess proto,dwExitCode:dword

INCLUDE Irvine32.inc

.data
firstval  word 0ah
secondval word 4h
thirdval  word 5h
diff word 0

.code
main proc
    xor eax,eax      ;zero out eax register to be able to understand result

    mov ax,firstval  ; move first value of 10 in hex to the 16bit register ax
    mov bx,secondval ; move second value of 4 to the 16bit register bx
    mov cx,thirdval  ; move third value of 5 to the 16bit cx register
    sub ax,bx        ; subtract the values of bx register from the ax register so 10-4=6
    sub ax,cx        ; subtract the values of cx register from ax register so 6-5=1
    call DumpRegs    ; call dumpregs to view register values, EAX should show 0x1 

    invoke ExitProcess,0
main endp
end main
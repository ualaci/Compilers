$BEGIN_PROGRAM
BOOL Valor1
BOOL Valor2
INT NUM1
INT NUM2
INT NUM3
BYTE I
BYTE J
DOUBLE CalcA
DOUBLE CalcB
Valor1:=true
Valor2:=false
CalcB:=51.21
NUM1:=44
J:=100
NUM2:=17
NUM3:=44
I:=1
CalcA:=22.14
if ( CalcB > CalcA && NUM1 < NUM3 && Valor1 && NUM1 > NUM2 &&
NUM2 < NUM3 || Valor2 ) then
{
NUM2:=25
NUM3:=NUM1-1
Valor2:=true
}
else
{
NUM1:=NUM2+1
NUM2:=87
Valor1:=false
}
$END_PROGRAM
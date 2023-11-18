Consider the encodings implemented and made available on 02/03/2022 to provide validations in the Grammar related to variable management, as requested:
a) Checking the types FLOAT, BOOL, BYTE, LONG and STRING;
b) Assignment between variables, denoting the appropriate semantics (only between equivalent types);
c) Test the input below with the appropriate alternations between types.


$BEGIN_PROGRAM

FLOAT X
INT Y
BOOL Flag
FLOAT Number
LONG Z
STRING Transport


Y=21
X=34.
Transport=Airplane
Number=43.22
Flag=false
X=78.0
Z=Transport
Y=X

$END_PROGRAM
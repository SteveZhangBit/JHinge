The definition of the tokens are as follows: (also shows the order to match)

Comment			#.*
String			r?(?:'[^']*'|"[^"]*")		'\n' can appear in strings
												'r' represents raw string, it won't deal with the escape character
Identifier		[a-zA-Z_]\w*

Float			Exponentfloat|Pointfloat	(?:\d*\.\d+|\d+\.|\d+)[eE][+-]?\d+|\d*\.\d+|\d+\.
Pointfloat		\d*\.\d+|\d+\.
Exponentfloat	(?:Pointfloat|\d+)[eE][+\-]?\d+

LongIntger		Integer[lL]					(?:Integer)[lL]
Integer			Decimal|Octal|Hex|Binary	[1-9]\d*|0[0-7]+|0[xX][0-9a-fA-F]+|0[bB][01]+|0
Decimal			[1-9]\d*|0
Octal			0[0-7]+
Hex				0[xX][0-9a-fA-F]+
Binary			0[bB][01]+

AssignOp		\+=|-=|\*=|\*\*=|/=|%=|<<=|>>=|&=|\|=|\^=
Bitwise			<<|>>|&|\||\^|~
Comparison		<=|>=|==|!=|<|>
Operator		\+|-|\*\*|\*|/|%
Delimiter		[()\[\]{},.?:]
Assign			=

Blank			[ \t\x0B\f\r]+
Newline			\n

<end of tokens>

The escape sequence:
\\	Backslash (\)
\'	Single quote (')
\"	Double quote (")
\a	ASCII Bell (BEL)
\b	ASCII Backspace (BS)
\f	ASCII Formfeed (FF)
\n	ASCII Linefeed (LF)
\r	ASCII Carriage Return (CR)
\t	ASCII Horizontal Tab (TAB)
\uxxxx	Character with 16-bit hex value xxxx (Unicode only)	(1)
\v	ASCII Vertical Tab (VT)
\ooo	Character with octal value ooo	(3,5)
\xhh	Character with hex value hh


The key words of Hinge:
and			or		not		is		in		true		false
continue	break	for		while 	do		switch		case
if			else	raise	import	try		catch		finally
return		func	class	super	self	none


The operators of Hinge:
+		-		*		**		/		%
<<		>>		&		|		^		~
<		>		<=		>=		==		!=


The delimiters:
(		)		[		]		{		}
,		?		:		.		=
+=		-=		*=		**=		/=		%=
<<=		>>=     &=		|=		^=


Operator precedence: (from low to high)

if – else		Conditional expression
or				Boolean OR
and				Boolean AND
not x			Boolean NOT
in, not in, is, is not, <, <=, >, >=, !=, ==
				Comparisons, including membership tests and identity tests
|				Bitwise OR
^				Bitwise XOR
&				Bitwise AND
<<, >>			Shifts
+, -			Addition and subtraction
*, /, %			Multiplication, division, remainder
+x, -x, ~x		Positive, negative, bitwise NOT
**				Exponentiation
x[index], x[index:index], x(arguments...), x.attribute
				Subscription, slicing, call, attribute reference
(expressions...), [expressions...], {key: value...}
				Binding or tuple display, list display, dictionary display, string conversion

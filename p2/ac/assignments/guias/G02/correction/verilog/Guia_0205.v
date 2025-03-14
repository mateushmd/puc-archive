/*
	Guia_0205.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0205;
	reg [7 : 0] a, b;
	reg [11 : 0] c;
	
	initial
	begin: main
		a = 8'b101_110;
		b = 8'b10_011;
		c = a + b;
		$display("a) %b.%b (2)", c[11:3], c[2:0]);
		
		a = 8'b1000_101;
		b = 8'b10_010;
		c = a - b;
		$display("b) %b.%b (2)", c[11:3], c[2:0]);
		
		a = 8'b101_101;
		b = 8'b10_101;
		c = a * b;
		$display("c) %b.%b (2)", c[11:6], c[5:0]);
		
		a = 8'b10111_010;
		b = 8'b11_011;
		c = a / b;
		$display("d) %b (2)", c);
		
		a = 8'b1101011;
		b = 8'b1101;
		c = a % b;
		$display("e) %b (2)", c);
	end
endmodule

/*
	Saída:
	
	a) 000001000.001 (2)
	b) 000000110.011 (2)
	c) 001110.110001 (2)
	d) 000000000110 (2)
	e) 000000000011 (2)
*/
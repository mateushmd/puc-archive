/*
	Guia_0803.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0803;
	reg [4 : 0] x;
	reg [4 : 0] y;
	wire[5 : 0] e;
	
	c c0(e[0], x[0], y[0]);
	c c1(e[1], x[1], y[1]);
	c c2(e[2], x[2], y[2]);
	c c3(e[3], x[3], y[3]);
	c c4(e[4], x[4], y[4]);
	
	and AND(e[5], e[4], e[3], e[2], e[1], e[0]);
	
	initial 
	begin: main
		$monitor("%b == %b ? %b", x, y, e[5]);
		
		x = 5'b00000; 
		y = 5'b00000;
		
		#1 
		
		x = 5'b11111; 
		y = 5'b11111;
		
		#1
		
		x = 5'b10111; 
		y = 5'b11111;
	end
endmodule

module c (output e, input x, y);
	xnor XNOR1(e, x, y);
endmodule
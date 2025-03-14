/*
	Guia_0802.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_0802;
	reg [3 : 0] x;
	reg [3 : 0] y;
	wire [3 : 0] v;
	wire [3 : 0] d;

	fullDifference fd0 (v[0], d[0], x[0], y[0], 1'b0);
	fullDifference fd1 (v[1], d[1], x[1], y[1], v[0]);
	fullDifference fd2 (v[2], d[2], x[2], y[2], v[1]);
	fullDifference fd3 (v[3], d[3], x[3], y[3], v[2]);
	
	initial 
	begin: main
		$monitor("%b - %b = %b", x, y, d);
		
		x = 4'b1001; 
		y = 4'b0100;
		
		#1 
		
		x = 4'b1111; 
		y = 4'b1110;
		
		#1
		
		x = 4'b0111; 
		y = 4'b1111;
	end
endmodule

module halfDifference (output v, d, input a, b);
	wire na;
	
	not NOT1 (na, a);
	xor XOR1 (d, a, b);
	and AND1 (v, na, b);
endmodule

module fullDifference (output v, d, input a, b, vIn);
	wire diff, v1, v2;
	
	halfDifference hd0(v1, diff, a, b);
	halfDifference hd1(v2, d, diff, vIn);
	
	or OR1(v, v1, v2);
endmodule
/*
	Guia_1303.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_1303;
	reg clk, enable, clear;
	wire [3:0] q;
	
	counter c (q, clk, enable, clear);
	
	initial 
	begin: main
		integer i;
		clk = 0;
		clear = 1;
		#1 clear = 0;
		enable = 1;
		
		$display("%d | %b", i, q);
		
		for(i = 0; i < 16; i++) begin
			#1
			clk = ~clk;
			#1
			clk = ~clk;
			
			$display("%d | %b", i, q);
		end
	end
endmodule

module counter (output [3:0] q_out,
			input clk, enable, clear);
			
	wire [0:3] q;
	wire [0:3] qnot;
	
	jkff jk3 (q[3], qnot[3], enable, enable, clk, 1'b0, clear);
	jkff jk2 (q[2], qnot[2], enable, enable, qnot[3], 1'b0, clear);
	jkff jk1 (q[1], qnot[1], enable, enable, qnot[2], 1'b0, clear);
	jkff jk0 (q[0], qnot[0], enable, enable, qnot[1], 1'b0, clear);
	
	assign q_out = q;
endmodule

module jkff ( output q, output qnot,
			input j, input k, input clk,
			input preset, input clear );
	reg q, qnot;
	always @( posedge clk or posedge preset or posedge clear )
	begin
		if ( clear ) begin q <= 0; qnot <= 1; end
		else
		if ( preset ) begin q <= 1; qnot <= 0; end
		else
		if (j & ~k) begin q <= 1; qnot <= 0; end
		else
		if (~j & k) begin q <= 0; qnot <= 1; end
		else 
		if(j & k) begin q <= ~q; qnot <= ~qnot; end
	end
endmodule
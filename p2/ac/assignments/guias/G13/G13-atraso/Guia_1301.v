/*
	Guia_1301.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_1301;
	reg clk, enable, clear;
	wire [4:0] q;
	
	counter c (q, clk, enable, clear);
	
	initial 
	begin: main
		integer i;
		clk = 0;
		clear = 1;
		#1 clear = 0;
		enable = 1;
		
		$display("%d | %b", i, q);
		
		for(i = 0; i < 32; i++) begin
			#1
			clk = ~clk;
			#1
			clk = ~clk;
			
			$display("%d | %b", i, q);
		end
	end
endmodule

module counter (output [4:0] q_out,
			input clk, enable, clear);
			
	wire q;
	wire [4:0] qnot;
	
	jkff jk0 (q, qnot[0], enable, enable, clk, 1'b0, clear);
	jkff jk1 (q, qnot[1], enable, enable, qnot[0], 1'b0, clear);
	jkff jk2 (q, qnot[2], enable, enable, qnot[1], 1'b0, clear);
	jkff jk3 (q, qnot[3], enable, enable, qnot[2], 1'b0, clear);
	jkff jk4 (q, qnot[4], enable, enable, qnot[3], 1'b0, clear);
	
	assign q_out = qnot;
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
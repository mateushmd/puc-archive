/*
	Guia_1305.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_1305;
	reg clk, enable, clear;
	wire [2:0] q;
	
	counter c (q, clk, enable, clear);
	
	initial 
	begin: main
		integer i;
		clk = 0;
		clear = 1;
		#1 clear = 0;
		enable = 1;
		
		$display("%d | %b", i, q);
		
		for(i = 0; i < 7; i++) begin
			#1
			clk = ~clk;
			#1
			clk = ~clk;
			
			$display("%d | %b", i, q);
		end
	end
endmodule

module counter (output [2:0] q_out,
			input clk, enable, clear);
			
	wire [2:0] q;
	wire t_2, c, qnot;
	
	assign c = clear | (q == 3'b111);
		
	tff t0 (q[0], qnot, enable, clk, 1'b0, c);
	tff t1 (q[1], qnot, q[0], clk, 1'b0, c);
	
	assign t_2 = q[0] & q[1];
	
	tff t2 (q[2], qnot, t_2, clk, 1'b0, c);
	
	assign q_out = q;
endmodule

module tff ( output q, output qnot,
			input t, input clk,
			input preset, input clear );
	reg q, qnot;
	always @( posedge clk or posedge preset or posedge clear )
	begin
		if ( clear ) begin q <= 0; qnot <= 1; end
		else
		if ( preset ) begin q <= 1; qnot <= 0; end
		else
		if (t) begin q <= ~q; qnot <= ~qnot; end
	end
endmodule
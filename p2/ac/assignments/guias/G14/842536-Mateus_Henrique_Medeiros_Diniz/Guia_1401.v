/*
	Guia_1401.v
	842536 - Mateus Henrique Medeiros Diniz
*/

module Guia_1401;
	reg ld, clk, clear;
	wire [5:0] q;
	
	lbs lbs0(q, clk, ld, clear);
	
	initial 
	begin: main
		integer i;
		clk = 0;
		clear = 0;
		ld = 0;
		
		for(i = 0; i < 9; i++) begin
			#1
			clk = ~clk;
			ld = ~ld;
			#1
			clk = ~clk;
			
			$display("%d | %b", i, q);
		end
	end
endmodule

module lbs (output [5:0] q_out,
			input clk, ld, clear);
			
	reg preset = 0;
	wire [5:0] q;
	wire qnot;
	
	dff d0 (q[0], qnot, ld, clk, preset, clear);
	dff d1 (q[1], qnot, q[0], clk, preset, clear);
	dff d2 (q[2], qnot, q[1], clk, preset, clear);
	dff d3 (q[3], qnot, q[2], clk, preset, clear);
	dff d4 (q[4], qnot, q[3], clk, preset, clear);
	dff d5 (q[5], qnot, q[4], clk, preset, clear);
	
	assign q_out = q;
endmodule

module dff ( output q, output qnot,
			input d, input clk,
			input preset, input clear );
	reg q, qnot;
	always @( posedge clk )
	begin
		if ( clear ) begin q <= 0; qnot <= 1; end
		else
		if ( preset ) begin q <= 1; qnot <= 0; end
		else
		begin q <= d; qnot <= ~d; end
	end
endmodule